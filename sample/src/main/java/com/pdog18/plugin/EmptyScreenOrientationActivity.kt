package com.pdog18.plugin

import kotlinx.android.synthetic.main.activity_base.*

class EmptyScreenOrientationActivity : SecondActivity() {

    override fun doLast() {
        super.doLast()

        background.setImageResource(R.mipmap.empty)
        btn_next.setOnClickListener {
            startActivity<ScreenOrientationOnManifestActivity>()
        }
    }
}