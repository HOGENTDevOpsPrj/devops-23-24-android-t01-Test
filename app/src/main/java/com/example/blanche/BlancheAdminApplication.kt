package com.example.blanche

import android.app.Application
import com.example.blanche.data.AppContainer
import com.example.blanche.data.DefaultAppContainer

class BlancheAdminApplication : Application() {

    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(context = applicationContext)

    }
}
