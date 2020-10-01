package com.example.pocvirginialottery

import android.content.Intent
import android.os.Bundle
import com.example.pocvirginialottery.retroclient.Api
import com.example.pocvirginialottery.retroclient.ApiClient
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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

        searchButton.setOnClickListener {
            val zipcode = editZipCode.text.toString()
            if (zipcode.isNotEmpty()) {
                var intent = Intent(this, MapsActivity::class.java)
                intent.putExtra("ZIPCODE", zipcode)
                startActivity(intent)
            }
        }
    }


}