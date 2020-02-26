package com.full.ndkdemo

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.*


val TAG = "ThreadPoolExecutor"

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val options = BitmapFactory.Options()
        options.inMutable = true
        //最好的是ARGB8888 32位
        //最好的是ARGB8888 32位
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        val decodeResource = BitmapFactory.decodeResource(resources, R.drawable.ic_launcher,options)

        val gray = BitmapFilter.gray(decodeResource)

        sample_text1.setImageBitmap(gray)
        // Example of a call to a native method
//        sample_text.text = stringFromJNI().toString()
//        val drawable = BitmapDrawable(resources, bitmapFromJNI(this))
//        sample_text.setCompoundDrawables(drawable, null, null, null)
//SynchronousQueue 并发
        val threadPoolExecutor =
            ThreadPoolExecutor(3, 5, 60L, TimeUnit.SECONDS, PriorityBlockingQueue<Runnable>(),
                Executors.defaultThreadFactory(),
                RejectedExecutionHandler { r, executor ->
                    Log.w(TAG, "任务被拒绝：$r");
                })

        threadPoolExecutor.prestartAllCoreThreads()
        threadPoolExecutor.allowCoreThreadTimeOut(true)
        for (index in 1..15) {
            threadPoolExecutor.execute(MyRunnable("$index 号种子"))
        }

    }

}

class MyRunnable(val name: String) : Runnable, Comparable<MyRunnable> {

    override fun run() {
        Log.d(TAG, "任务被执行：$name ," + Thread.currentThread().name);
        SystemClock.sleep(2000)
    }

    override fun toString(): String {
        return "$name"
    }

    override fun compareTo(other: MyRunnable): Int {
        return if (name.hashCode() > other.name.hashCode()) 1 else 0
    }

}
