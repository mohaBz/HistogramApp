package com.example.moha.histogramapp.Utils;

import android.graphics.Bitmap;
import android.graphics.Color;

public class HistogramCalculationUtils
{
        public static int[] getGrayBins(Bitmap bitmap1){
            int grayBins[]=new int[256];
            int[] values=new int[bitmap1.getWidth()*bitmap1.getHeight()];
            bitmap1.getPixels(values,0,bitmap1.getWidth(),0,0,bitmap1.getWidth(),bitmap1.getHeight());
            for (int value:values){
                grayBins[roundToGray(value)]++;
            }
            return grayBins;
        }

        public static int[] getComulBins(Bitmap bitmap1){
            int comulBins[]=new int[256];
            int[] grayBins=getGrayBins(bitmap1);
            comulBins[0] =grayBins[0];
            for (int i=1;i<256;i++){
                comulBins[i]=grayBins[i]+comulBins[i-1];
            }
            return comulBins;
        }
        public static int[] getPondrBins(Bitmap bitmap1){
            int width=bitmap1.getWidth(),height=bitmap1.getHeight();
            if(height==width){
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
            return new int[256];
            }
        private static int roundToGray(int pxl){
            double lum= 0.59* Color.red(pxl)+0.3*Color.green(pxl)+0.11*Color.blue(pxl);
            return ((int)Math.round(lum));
        }
}
