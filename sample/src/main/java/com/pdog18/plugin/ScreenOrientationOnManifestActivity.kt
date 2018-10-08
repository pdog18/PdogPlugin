package com.pdog18.plugin

import kotlinx.android.synthetic.main.activity_base.*

class ScreenOrientationOnManifestActivity : BaseActivity() {
    override fun doLast() {
        super.doLast()
        background.setImageResource(R.mipmap.onmanifest)

        btn_next.setOnClickListener {
            startActivity<ScreenOrientationOnKotlinCodeActivity>()
        }
    }
}