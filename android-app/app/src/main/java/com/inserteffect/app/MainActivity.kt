package com.inserteffect.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.inserteffect.mylibrary.Library

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Library().init()
    }
}
