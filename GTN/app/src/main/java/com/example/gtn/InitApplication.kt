package com.example.gtn

import android.app.Application
import io.realm.Realm

class InitApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}