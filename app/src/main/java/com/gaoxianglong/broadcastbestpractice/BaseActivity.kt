package com.gaoxianglong.broadcastbestpractice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper

/**
 * 记住要使用open关键字让这个类能够被继承
 */
open class BaseActivity : AppCompatActivity() {
    lateinit var fb: FB // 延迟初始化
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        ActivityCollector.addActivity(this) // 将当前活动加入到列表
    }

    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter() // 创建一个意图过滤器
        intentFilter.addAction("com.gaoxianglong.FORCE_OFFLINE") // 添加一个不过滤的信息
        fb = FB() // 初始化接收器
        registerReceiver(fb,intentFilter) // 注册接收器
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(fb) // 注销接收器
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this) // 将当前活动移出列表
    }

    inner class FB : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            // 对话框
            AlertDialog.Builder(context).apply {
                setTitle("下线提示")
                setMessage("你的账号在其他地方登录了")
                setCancelable(false)
                setPositiveButton("ok"){_,_->
                    ActivityCollector.finishAll()
                    context.startActivity(Intent(context,MainActivity::class.java))
                }
                show()
            }
        }

    }
}