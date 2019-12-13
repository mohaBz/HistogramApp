package com.example.moha.histogramapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.File;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
private ImageView source_img;
private TextView img_path;
private Button load_img;
private Button load_grph;
    private Button load_pondr;
    private Button load_comul;
private Bitmap bitmap;
private     GraphView graph;
private static final String TAG = MainActivity.class.getSimpleName();
public static final int REQUEST_GET_SINGLE_FILE=202;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        source_img=(ImageView) findViewById(R.id.source_img);
        img_path=(TextView)findViewById(R.id.img_path);
        load_img=(Button)findViewById(R.id.img_load);
        load_grph=(Button)findViewById(R.id.load_hist);
        load_pondr=(Button)findViewById(R.id.load_pond);
        load_comul=(Button)findViewById(R.id.load_comul);
        graph= (GraphView) findViewById(R.id.graph);
        graph.setVisibility(View.INVISIBLE);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(255);
        graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        load_grph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] bins=getGrayBins(bitmap);
                showGraph(bins);
                load_grph.setVisibility(View.INVISIBLE);
                load_pondr.setVisibility(View.VISIBLE);
                load_comul.setVisibility(View.VISIBLE);
                graph.setVisibility(View.VISIBLE);
            }
        });
        load_pondr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] bins=getPondrBins(bitmap);
                showGraph(bins);
                load_grph.setVisibility(View.VISIBLE);
                load_comul.setVisibility(View.VISIBLE);
                load_pondr.setVisibility(View.INVISIBLE);
                graph.setVisibility(View.VISIBLE);
            }
        });
        DataPoint[] data=new DataPoint[256];
        for (int i=0;i<256;i++){
            data[i]=new DataPoint(i,i);

        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(
        data);
        graph.addSeries(series);
        source_img.setImageDrawable(getDrawable(R.drawable.ic_launcher_background));
        load_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),REQUEST_GET_SINGLE_FILE);

            }
        });
        load_comul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] bins=getComulBins(bitmap);
                showGraph(bins);
                load_comul.setVisibility(View.INVISIBLE);
                load_pondr.setVisibility(View.VISIBLE);
                graph.setVisibility(View.VISIBLE);
                load_grph.setVisibility(View.VISIBLE);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == REQUEST_GET_SINGLE_FILE) {
                    Uri selectedImageUri = Objects.requireNonNull(data).getData();
                    // Get the path from the Uri
                    final String path = getPathFromURI(selectedImageUri);
                    if (path != null) {
                        File f = new File(path);
                        selectedImageUri = Uri.fromFile(f);
                    }
                    // Set the image in ImageView
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    if(bitmap != null){
                        source_img.setImageBitmap(toGrayscale(bitmap));
                        img_path.setText(path);
                    }
                    Log.d(TAG, "Lod image path " + selectedImageUri + " - " + selectedImageUri.getEncodedPath());
                    load_grph.setVisibility(View.VISIBLE);
                    graph.setVisibility(View.INVISIBLE);
                }
            }
        } catch (Exception e) {
            Log.e("FileSelectorActivity", "File select error", e);
        }
    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
    public Bitmap toGrayscale(Bitmap bmpOriginal)
    {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }
    public int roundToGray(int pxl){
        double lum= 0.59*Color.red(pxl)+0.3*Color.green(pxl)+0.11*Color.blue(pxl);
        return ((int)Math.round(lum));
    }
    public int[] getGrayBins(Bitmap bitmap1){
        int grayBins[]=new int[256];
        int[] values=new int[bitmap1.getWidth()*bitmap1.getHeight()];
        bitmap1.getPixels(values,0,bitmap1.getWidth(),0,0,bitmap1.getWidth(),bitmap1.getHeight());
        for (int value:values){
            grayBins[roundToGray(value)]++;
        }
        return grayBins;
    }
    public int[] getComulBins(Bitmap bitmap1){
        int comulBins[]=new int[256];
        int[] grayBins=getGrayBins(bitmap1);
        comulBins[0] =grayBins[0];
        for (int i=1;i<256;i++){
            comulBins[i]=grayBins[i]+comulBins[i-1];
        }
        return comulBins;
    }
    public void showGraph(int[] bins){
        DataPoint[] dataPoints=new DataPoint[256];
        for (int j=0;j<256;j++){
            dataPoints[j]=new DataPoint(j,bins[j]);
        }
        BarGraphSeries<DataPoint> series =new BarGraphSeries<>(dataPoints);
        graph.removeAllSeries();
        series.setSpacing(0);
        graph.addSeries(series);

    }
    public int[] getPondrBins(Bitmap bitmap1){
        int width=bitmap1.getWidth(),height=bitmap1.getHeight();
        int[] pondrBins=new int[256];
        int[] values=new int[bitmap1.getWidth()*bitmap1.getHeight()];
        bitmap1.getPixels(values,0,bitmap1.getWidth(),0,0,bitmap1.getWidth(),bitmap1.getHeight());
        for (int i=0;i<height;i++){
            for (int j=0;j<width;j++){
                float distX=j<height/2 ?height/2 -j : (j%(height/2));
                float distY=i<width/2 ? width/2-i:(i%(width/2));
                double x=distX/(width/2);
                double y=distY/(height/2);
                double m=Math.pow(x,2)+Math.pow(y,2);
                double dist=Math.sqrt(m);
                Log.d("the distance","dist= "+String.valueOf(Math.sqrt(m))+"m= "+m+" "+ "x and y"+String.valueOf(height)+" "+String.valueOf(y) );
                int value=roundToGray(values[i*height+j]);
                if (dist <= 1/3)
                    pondrBins[value]+= 3;
                else if (dist <= 1)
                    pondrBins[value]+= 2;
                else
                    pondrBins[value]++;
            }
        }
//        for(int i = 0 ; i < 256 ; i++) {
//            for(int j = 0 ; j < 256 ; j++) {
//		 int distX = i < 128 ? 128 - i : (i % 128);
//		int distY = j < 128 ? 128 - j : (j % 128);
//		double dist = Math.sqrt((distX / 128)*(distX / 128) + (distY / 128) *(distY / 128));
//		Log.d("the distance",String.valueOf(dist));
//		int pixel = roundToGray(values[i*256 + j]);
//                if (dist <= 1/3)
//                    pondrBins[pixel] += 3;
//                else if(dist <= 2/3)
//                    pondrBins[pixel] += 2;
//                else if (dist <= 1)
//                    pondrBins[pixel] += 2;
//                else
//                    pondrBins[pixel] ++;
//            }
//        }
return pondrBins;
    }



}
