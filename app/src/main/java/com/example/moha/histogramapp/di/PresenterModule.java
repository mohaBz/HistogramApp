package com.example.moha.histogramapp.di;

import com.example.moha.histogramapp.Presenter.Presenter;
import com.example.moha.histogramapp.Presenter.PresenterImpl;

import dagger.Binds;
import dagger.Module;


@Module
public abstract class PresenterModule {
    @Binds
   abstract Presenter providePresenter(PresenterImpl p);
}
