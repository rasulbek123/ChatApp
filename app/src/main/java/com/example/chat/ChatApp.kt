package com.example.chat

import android.app.Application
import com.example.chat.di.dataModule
import com.example.chat.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class ChatApp:Application() {
    override fun onCreate() {
        super.onCreate()
        val modules = listOf(dataModule, viewModelModule)
        startKoin { // use AndroidLogger as Koin Logger - default Level.INFO
            androidLogger()

            // use the Android context given there
            androidContext(this@ChatApp)

            // load properties from assets/koin.properties file
            androidFileProperties()

            // module list
            koin.loadModules(modules)
        }
    }
}