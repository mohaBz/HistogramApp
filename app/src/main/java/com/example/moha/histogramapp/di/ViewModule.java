package com.example.moha.histogramapp.di;

import com.example.moha.histogramapp.Presenter.Presenter;
import com.example.moha.histogramapp.Views.MainActivity;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModule {
    @Binds
    abstract Presenter.View provideView(MainActivity mainActivity);
}
