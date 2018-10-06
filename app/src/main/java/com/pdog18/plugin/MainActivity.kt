package com.pdog18.plugin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = this::class.java.canonicalName

        tv_start_main2.setOnClickListener {
            startActivity(Intent(this, Main2Activity::class.java))
        }
    }
}