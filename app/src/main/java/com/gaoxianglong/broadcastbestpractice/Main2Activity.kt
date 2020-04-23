package com.gaoxianglong.broadcastbestpractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        button2.setOnClickListener {
            val intent = Intent("com.gaoxianglong.FORCE_OFFLINE")
            intent.setPackage(packageName)
            sendBroadcast(intent)
        }
    }
}
