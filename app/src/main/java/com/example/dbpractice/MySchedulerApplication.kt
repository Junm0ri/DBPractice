package com.example.dbpractice

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class MySchedulerApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
//        val config = RealmConfiguration.Builder().allowWritesOnUiThread(true).build()
        val config=RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().allowWritesOnUiThread(true).build()
        Realm.setDefaultConfiguration(config)
    }
}