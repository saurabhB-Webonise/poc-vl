package com.example.pocvirginialottery

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
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

        searchButton.setOnClickListener {

            try {
                val imm: InputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            } catch (e: Exception) {

            }

            val zipcode = editZipCode.text.toString()
            if (zipcode.isNotEmpty()) {
                var intent = Intent(this, MapsActivity::class.java)
                intent.putExtra("ZIPCODE", zipcode)
                startActivity(intent)

            }
        }
    }

    override fun onPause() {
        super.onPause()



    }

    fun toast() {
        Toast.makeText(this, " Wait! Searching for result...", Toast.LENGTH_LONG).show()
    }

}
