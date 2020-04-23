package com.example.moha.histogramapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moha.histogramapp.Presenter.Presenter;
import com.example.moha.histogramapp.Presenter.PresenterImpl;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import java.io.File;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements Presenter.View{
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
            private Presenter mPresenter;
        @Override
        protected void onCreate(Bundle savedInstanceState)  {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            source_img=(ImageView) findViewById(R.id.source_img);
            img_path=(TextView)findViewById(R.id.img_path);
            load_img=(Button)findViewById(R.id.img_load);
            load_grph=(Button)findViewById(R.id.load_hist);
            load_pondr=(Button)findViewById(R.id.load_pond);
            load_comul=(Button)findViewById(R.id.load_comul);
            graph= (GraphView) findViewById(R.id.graph);
            mPresenter=new PresenterImpl(this,Injection.provideScheduleProvider());
            mPresenter.onCreate();
            load_grph.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.onHistogramClicked();
                }
            });
            load_pondr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.onHistPonderClicked();
                }
            });
            load_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 mPresenter.onLoadImageClicked();
                }
            });
            load_comul.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.onHistoComuleClicked();
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
                        mPresenter.setImageModel(Injection.provideImageModel(bitmap,path));

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

        @Override
        public void showImage(Bitmap image, String name) {
            if(bitmap != null){
                source_img.setImageBitmap(bitmap);
                img_path.setText(name);
            }
            load_grph.setVisibility(View.VISIBLE);
            graph.setVisibility(View.INVISIBLE);
        }

        @Override
        public void openLoadImageIntent() {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"),REQUEST_GET_SINGLE_FILE);
        }

        @Override
        public void activateHistograme() {
            load_grph.setVisibility(View.INVISIBLE);
            load_pondr.setVisibility(View.VISIBLE);
            load_comul.setVisibility(View.VISIBLE);
            graph.setVisibility(View.VISIBLE);
        }

        @Override
        public void activateHistogramComule() {
            load_comul.setVisibility(View.INVISIBLE);
            load_pondr.setVisibility(View.VISIBLE);
            graph.setVisibility(View.VISIBLE);
            load_grph.setVisibility(View.VISIBLE);
        }

        @Override
        public void activateHistogramePndr() {
            load_grph.setVisibility(View.VISIBLE);
            load_comul.setVisibility(View.VISIBLE);
            load_pondr.setVisibility(View.INVISIBLE);
            graph.setVisibility(View.VISIBLE);
        }

    @Override
    public void initGraph() {
        graph.setVisibility(View.INVISIBLE);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(255);
        graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        source_img.setImageDrawable(getDrawable(R.drawable.ic_launcher_background));
    }

}
