package com.example.moha.histogramapp;

import android.graphics.Bitmap;

import com.example.moha.histogramapp.Data.ImageModel;
import com.example.moha.histogramapp.Utils.Schedulers.BaseSchedulerProvider;
import com.example.moha.histogramapp.Utils.Schedulers.SchedulerProvider;

import javax.inject.Inject;

public class ImageDataRepository {
    public ImageModel getImageModel() {
        return imageModel;
    }

    @Inject
    ImageModel imageModel;
    @Inject
    public ImageDataRepository(){}
    public  ImageModel provideImageModel(Bitmap image, String name){
        imageModel.setImage(image);
        imageModel.setName(name);
        return imageModel;
    }
}
