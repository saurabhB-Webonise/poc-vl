package com.example.pocvirginialottery

import android.content.Intent
import android.os.Bundle
import com.example.pocvirginialottery.retroclient.Api
import com.example.pocvirginialottery.retroclient.ApiClient
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreen()
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        showButton.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }
    }

}