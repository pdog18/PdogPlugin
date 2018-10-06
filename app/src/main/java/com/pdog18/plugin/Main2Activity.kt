package com.pdog18.plugin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main2)
        title = this::class.java.canonicalName

        tv_start_empty_screen_orientation_activity.setOnClickListener {
            startActivity(Intent(this, EmptyScreenOrientationActivity::class.java))
        }
    }
}
