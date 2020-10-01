package com.example.pocvirginialottery

import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity :AppCompatActivity() {

   protected fun fullScreen(){
       requestWindowFeature(Window.FEATURE_NO_TITLE);
       window.setFlags(
           WindowManager.LayoutParams.FLAG_FULLSCREEN,
           WindowManager.LayoutParams.FLAG_FULLSCREEN);
   }
}
