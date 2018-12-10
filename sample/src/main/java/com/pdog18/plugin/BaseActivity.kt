package com.pdog18.plugin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        doFirst()

        setContentView(getLayoutId())
        title = this::class.java.simpleName

        doLast()
    }


    protected open fun getLayoutId() = R.layout.activity_base

    protected open fun doFirst() = Unit

    protected open fun doLast() = Unit

    protected inline fun <reified T : Activity> startActivity() {
        startActivity(Intent(this, T::class.java))
    }
}