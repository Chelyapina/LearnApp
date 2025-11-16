package com.example.learnapp

import android.app.Application
import com.example.learnapp.di.AppComponent
import com.example.learnapp.di.AppModule
import com.example.learnapp.di.DaggerAppComponent

class LearnApp : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}