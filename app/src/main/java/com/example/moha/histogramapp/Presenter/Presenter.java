package com.example.moha.histogramapp.Presenter;

import android.graphics.Bitmap;

import com.example.moha.histogramapp.Data.ImageModel;
import com.example.moha.histogramapp.ImageDataRepository;


public interface Presenter {
//    void  onActivityResult(int requestCode, int resultCode,  Intent data);
    void onHistogramClicked();
    void onHistoComuleClicked();
    void onHistPonderClicked();
    void onLoadImageClicked();
    ImageDataRepository getImageDataRepo();
    void onCreate();
    void onImageResult();

    public interface View{
    void showGraph(int[] bins);
    void showImage(Bitmap image,String name);
    void openLoadImageIntent();
    void activateHistograme();
    void activateHistogramComule();
    void activateHistogramePndr();
    void initGraph();
}
}
