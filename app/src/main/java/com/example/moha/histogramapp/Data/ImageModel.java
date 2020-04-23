package com.example.moha.histogramapp.Data;

import android.graphics.Bitmap;

import com.example.moha.histogramapp.Utils.HistogramCalculationUtils;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ImageModel {
    private Bitmap image;
    private String name;

    public ImageModel(Bitmap image,String name){
        this.image=image;
        this.name=name;
    }
    public Observable<int[]> getGrayBins(){

        Observable<int[]> bins= Observable.just(HistogramCalculationUtils.getGrayBins(image)).subscribeOn(Schedulers.computation());
        return bins;
    }
    public Observable<int[]> getHistComuleBins(){

        Observable<int[]> bins= Observable.just(HistogramCalculationUtils.getComulBins(image)).subscribeOn(Schedulers.computation());
        return bins;
    }
    public Observable<int[]> getHistPndrBins(){
        if (HistogramCalculationUtils.getPondrBins(image)!=null)
        {
            Observable<int[]> bins= Observable.just(HistogramCalculationUtils.getPondrBins(image)).subscribeOn(Schedulers.computation());
            return bins;
        }
        return null;

    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }
}
