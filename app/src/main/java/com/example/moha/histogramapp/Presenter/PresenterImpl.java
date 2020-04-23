package com.example.moha.histogramapp.Presenter;

import com.example.moha.histogramapp.Utils.BaseSchedulerProvider;
import com.example.moha.histogramapp.Data.ImageModel;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class PresenterImpl implements Presenter {
    private ImageModel imageModel;
    private View view;
    private BaseSchedulerProvider schedulerProvider;

    public PresenterImpl(View view,BaseSchedulerProvider baseSchedulerProvider){
        this.view=view;
        this.schedulerProvider=baseSchedulerProvider;
    }
    @Override
    public void onHistogramClicked() {
         imageModel.getGrayBins().observeOn(schedulerProvider.ui()).subscribe(new Observer<int[]>() {
             @Override
             public void onSubscribe(Disposable d) {

             }

             @Override
             public void onNext(int[] ints) {
               view.showGraph(ints);
               view.activateHistograme();
             }

             @Override
             public void onError(Throwable e) {

             }

             @Override
             public void onComplete() {

             }
         });
    }

    @Override
    public void onHistoComuleClicked() {
        imageModel.getHistComuleBins().observeOn(schedulerProvider.ui()).subscribe(new Observer<int[]>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(int[] ints) {
                view.showGraph(ints);
                view.activateHistogramComule();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void onHistPonderClicked() {
        imageModel.getHistPndrBins().observeOn(schedulerProvider.ui()).subscribe(new Observer<int[]>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(int[] ints) {
                view.showGraph(ints);
                view.activateHistogramePndr();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void onLoadImageClicked() {
        view.openLoadImageIntent();
    }

    @Override
    public void setImageModel(ImageModel model) {
        this.imageModel=model;
        view.showImage(model.getImage(),"");
    }

    @Override
    public void onCreate() {
        view.initGraph();
    }
}
