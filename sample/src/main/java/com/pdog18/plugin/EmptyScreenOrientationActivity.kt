package com.pdog18.plugin

import com.pdog18.ModuleActivity
import kotlinx.android.synthetic.main.activity_base.*

class EmptyScreenOrientationActivity : BaseActivity() {

    override fun doLast() {
        super.doLast()

        background.setImageResource(R.mipmap.empty)
        btn_next.setOnClickListener {
            startActivity<ModuleActivity>()
        }
    }
}