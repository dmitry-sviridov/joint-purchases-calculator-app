package ru.dkotsur.calculator.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.content.Intent
import android.os.Handler
import ru.dkotsur.calculator.view.sessions.SessionActivity


class SplashScreen : AppCompatActivity() {

    private val SPLASH_DISPLAY_LENGTH: Long = 100L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ru.dkotsur.calculator.R.layout.activity_splash_screen)

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        Handler().postDelayed(Runnable {
            /* Create an Intent that will start the Menu-Activity. */
            val mainIntent = Intent(this@SplashScreen, SessionActivity::class.java)
            this.startActivity(mainIntent)
            this.finish()
        }, SPLASH_DISPLAY_LENGTH)
    }
}
