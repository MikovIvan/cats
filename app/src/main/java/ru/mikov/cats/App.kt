package ru.mikov.cats

import android.app.Application
import android.content.Context
import ru.mikov.cats.data.remote.NetworkMonitor

class App : Application() {

    companion object {
        private var instance: App? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()

        // start network monitoring
        NetworkMonitor.registerNetworkMonitor(applicationContext())
    }
}
