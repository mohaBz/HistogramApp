package com.example.moha.histogramapp.Presenter;

import com.example.moha.histogramapp.ImageDataRepository;
import com.example.moha.histogramapp.Utils.Schedulers.BaseSchedulerProvider;
import javax.inject.Inject;
public class PresenterImpl implements Presenter {
    public View view;
    public BaseSchedulerProvider schedulerProvider;
    public ImageDataRepository imageDataRepository;
    @Inject
    public PresenterImpl(View view,BaseSchedulerProvider baseSchedulerProvider,ImageDataRepository imageDataRepository){
        this.view=view;
        this.schedulerProvider=baseSchedulerProvider;
        this.imageDataRepository=imageDataRepository;
    }
    @Override
    public void onHistogramClicked() {
         imageDataRepository.getImageModel().getGrayBins().observeOn(schedulerProvider.ui()).subscribe(nextEvent->{
             view.showGraph(nextEvent);
             view.activateHistograme();
         });
    }
    @Override
    public void onHistoComuleClicked() {
        imageDataRepository.getImageModel().getHistComuleBins().observeOn(schedulerProvider.ui()).subscribe(nextEvent->{
            view.showGraph(nextEvent);
            view.activateHistogramComule();
        });
    }

    @Override
    public void onHistPonderClicked() {
        imageDataRepository.getImageModel().getHistPndrBins().observeOn(schedulerProvider.ui()).subscribe(nextEvent->{
            view.showGraph(nextEvent);
            view.activateHistogramePndr();
        });
    }

    @Override
    public void onLoadImageClicked() {
        view.openLoadImageIntent();
    }

    @Override
    public ImageDataRepository getImageDataRepo() {
        return imageDataRepository;
    }


    @Override
    public void onCreate() {
        view.initGraph();
    }

    @Override
    public void onImageResult() {
        view.showImage(imageDataRepository.getImageModel().getImage(),"");
    }
}
