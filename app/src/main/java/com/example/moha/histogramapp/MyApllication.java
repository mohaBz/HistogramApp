package com.example.moha.histogramapp;

import android.app.Application;

import com.example.moha.histogramapp.Views.MainActivity;
import com.example.moha.histogramapp.di.AppComponent;
import com.example.moha.histogramapp.di.DaggerAppComponent;

public class MyApllication extends Application {
      public AppComponent getApplicationComponent(MainActivity mainActivity){
         return DaggerAppComponent.factory().create(mainActivity);
        }

}
