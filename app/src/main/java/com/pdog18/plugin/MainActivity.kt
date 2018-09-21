package com.pdog18.plugin

import android.app.ActivityThread
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val currentApplication = ActivityThread.currentApplication()
        println(currentApplication)
    }
}