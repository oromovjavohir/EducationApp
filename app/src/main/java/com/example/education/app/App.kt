package com.example.education.app

import android.app.Application
import android.content.res.Resources
import com.example.education.repostitory.DataBaseImpl

/**
 * Creator: Javohir Oromov
 * Date: 24/02/25
 * Project: Education
 * Javohir's MacBook Air
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        DataBaseImpl.init(this)
    }

    override fun getResources(): Resources {
        val a = 0
        return super.getResources()
    }
}