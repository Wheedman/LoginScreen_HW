package com.heroworkout.login.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.heroworkout.login.ui.login.LoginActivity

/**
 * Activity for transitioning the app
 */
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(1000) // Currently for simulating heavy load of app
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}