# BroadcastBestPeactice

强制退出登录

用的了一个随时随地退出app的功能
```kotlin
/**
 * 用于关闭全部activity
 */
object ActivityCollector {
    private val activities = ArrayList<Activity>() // 当前打开的activity列表

    // 打开一个activity时添加到列表中
    fun addActivity(activity: Activity) {
        activities.add(activity)
    }
    // 当关闭一个activity时在列表中将他移除
    fun removeActivity(activity: Activity) {
        activities.remove(activity)
    }

    fun finishAll() {
        for (activity in activities) {
            if (activity.isFinishing) {
                activity.finish()
            }
        }
        activities.clear()
    }
}
```
然后创建一个类继承与AppCompatActivity，在这个类中调用两个生命周期函数方便在启动一个活动时向活动列表中添加一个活动，退出一个活动时移除这个活动。
让所以的活动继承与这个类
```kotlin
/**
 * 记住要使用open关键字让这个类能够被继承
 */
open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        ActivityCollector.addActivity(this) // 将当前活动加入到列表
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this) // 将当前活动移出列表
    }
}
```
当然在这个类里还可以写上广播接收器，方便所有的活动都能受到广播影响
```kotlin
open class BaseActivity : AppCompatActivity() {
    lateinit var fb: FB // 延迟初始化
    ...
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
```
发起广播
```kotlin
val intent = Intent("com.gaoxianglong.FORCE_OFFLINE")
intent.setPackage(packageName)
sendBroadcast(intent)
```