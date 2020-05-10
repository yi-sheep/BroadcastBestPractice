package com.gaoxianglong.broadcastbestpractice

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val preferences = getPreferences(Context.MODE_PRIVATE)
        val isRemember = preferences.getBoolean("remember", false)
        if (isRemember) {
            val user = preferences.getString("user", "")
            val password = preferences.getString("password", "")
            editTextUser.setText(user)
            editTextPassword.setText(password)
            checkBox.isChecked = true
        }
        button.setOnClickListener {
            val user = editTextUser.text.toString()
            val password = editTextPassword.text.toString()
            if (user == "admin" && password == "123456") {
                // 获取到SharedPreferences.Editor实例
                preferences.edit().apply {
                    // 判断选项框的状态
                    if (checkBox.isChecked) {
                        putString("user",user)
                        putString("password",password)
                        putBoolean("remember",true)
                        apply()
                    } else {
                        clear()
                    }
                }
                val intent = Intent(this, Main2Activity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this,"密码不正确",Toast.LENGTH_SHORT).show()
            }
        }
    }
}
