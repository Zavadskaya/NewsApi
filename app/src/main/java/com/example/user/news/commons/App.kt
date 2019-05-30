package com.example.user.news.commons

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

open class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val configuration = RealmConfiguration.Builder().build()
        Realm.setDefaultConfiguration(configuration)
    }
}