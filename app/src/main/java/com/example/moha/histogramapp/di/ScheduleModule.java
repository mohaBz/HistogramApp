package com.example.moha.histogramapp.di;

import com.example.moha.histogramapp.Utils.Schedulers.BaseSchedulerProvider;
import com.example.moha.histogramapp.Utils.Schedulers.SchedulerProvider;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ScheduleModule {
    @Binds
    abstract BaseSchedulerProvider provideBaseSchedulerProvider(SchedulerProvider schedulerProvider);
}
