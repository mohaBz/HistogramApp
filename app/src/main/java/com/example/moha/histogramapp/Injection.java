package com.example.moha.histogramapp;

import android.graphics.Bitmap;

import com.example.moha.histogramapp.Data.ImageModel;
import com.example.moha.histogramapp.Utils.BaseSchedulerProvider;
import com.example.moha.histogramapp.Utils.SchedulerProvider;

public class Injection {
    public static ImageModel provideImageModel(Bitmap image, String name){
        return new ImageModel(image,name);

    }
   public static BaseSchedulerProvider provideScheduleProvider(){
        return SchedulerProvider.getInstance();
   }
}
