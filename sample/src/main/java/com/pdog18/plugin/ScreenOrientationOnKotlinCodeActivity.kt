package com.pdog18.plugin

import android.content.pm.ActivityInfo
import com.pdog.attributes.KeepAttributes
import kotlinx.android.synthetic.main.activity_base.*

@KeepAttributes(configChanges = "keyboardHidden")
class ScreenOrientationOnKotlinCodeActivity : BaseActivity() {

    override fun doFirst() {
        super.doFirst()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun doLast() {
        super.doLast()
        background.setImageResource(R.mipmap.onkotlin)


        btn_next.setOnClickListener {
            finish()
        }
    }
}
