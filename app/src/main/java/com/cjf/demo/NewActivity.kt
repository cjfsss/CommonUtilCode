package com.cjf.demo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

/**
 * <p>Title: NewActivity </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @date : 2020/10/28 13:40
 * @version : 1.0
 */
class NewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intExtra = intent?.getIntExtra("id", 0)
        Log.i("TAG", "onCreate: $intExtra")
    }
}