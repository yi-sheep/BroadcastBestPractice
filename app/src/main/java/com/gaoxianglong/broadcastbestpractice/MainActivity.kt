package com.gaoxianglong.broadcastbestpractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
            val user = editTextUser.text.toString()
            val password = editTextPassword.text.toString()
            if (user == "admin" && password == "123456") {
                val intent = Intent(this, Main2Activity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this,"密码不正确",Toast.LENGTH_SHORT).show()
            }
        }
    }
}
