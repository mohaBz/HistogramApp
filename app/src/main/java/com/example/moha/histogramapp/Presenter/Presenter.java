package com.example.moha.histogramapp.Presenter;

import android.graphics.Bitmap;

import com.example.moha.histogramapp.Data.ImageModel;


public interface Presenter {
//    void  onActivityResult(int requestCode, int resultCode,  Intent data);
    void onHistogramClicked();
    void onHistoComuleClicked();
    void onHistPonderClicked();
    void onLoadImageClicked();
    void setImageModel(ImageModel model);
    void onCreate();

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
