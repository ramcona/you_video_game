package com.rafli.yourvideogames.app

import android.app.Application
import android.os.StrictMode
import androidx.room.Room
import com.rafli.yourvideogames.database.AppDatabase
import com.rafli.yourvideogames.helper.Helper
import com.rafli.yourvideogames.helper.HelperToast

class App : Application() {

    companion object {
        var helper = Helper
        var toast = HelperToast()
        var db : AppDatabase? = null

    }

    override fun onCreate() {
        super.onCreate()
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        db = Room.databaseBuilder(applicationContext,
            AppDatabase::class.java,
            "my_games")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

    }

}