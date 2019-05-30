package com.example.user.news.commons
import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

open class App: Application() {

   open fun reamInit()
    {
        Realm.init(this)

        val configuration = RealmConfiguration.Builder()
            .build()
        Realm.setDefaultConfiguration(configuration)

    }

    override fun onCreate() {
        reamInit()
        super.onCreate()
    }
}