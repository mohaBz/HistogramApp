package com.example.moha.histogramapp.di;

import com.example.moha.histogramapp.Views.MainActivity;

import dagger.BindsInstance;
import dagger.Component;
@Component(modules={PresenterModule.class,ScheduleModule.class,ViewModule.class})
public interface AppComponent {

    @Component.Factory
    public interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        AppComponent create(@BindsInstance MainActivity main);
    }
    void inject(MainActivity activity);
}
