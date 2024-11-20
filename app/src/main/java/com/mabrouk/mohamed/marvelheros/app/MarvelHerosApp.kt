package com.mabrouk.mohamed.marvelheros.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import com.mabrouk.mohamed.marvelheros.BuildConfig
import timber.log.Timber

@HiltAndroidApp
class MarvelHerosApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}