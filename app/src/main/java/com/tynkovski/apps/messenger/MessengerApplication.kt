package com.tynkovski.apps.messenger

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.tynkovski.apps.messenger.sync.initializers.Sync
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MessengerApplication : Application(), ImageLoaderFactory {
    @Inject
    lateinit var imageLoader: dagger.Lazy<ImageLoader>

    override fun onCreate() {
        super.onCreate()
        Sync.initialize(context = this)
    }

    override fun newImageLoader(): ImageLoader = imageLoader.get()
}