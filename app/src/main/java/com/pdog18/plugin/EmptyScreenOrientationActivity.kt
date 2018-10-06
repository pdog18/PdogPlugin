package com.pdog18.plugin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class EmptyScreenOrientationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_null_launch_mode)
        title = this::class.java.canonicalName

    }
}
