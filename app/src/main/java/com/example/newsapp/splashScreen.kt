package com.example.newsapp


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class SplashScreen : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Delay for 3 seconds and then move to MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this@SplashScreen, MainActivity::class.java)
                intent.putExtra("navigate_to", "headline") // Optional: Extra data for navigation
                startActivity(intent)
            finish() // Remove SplashActivity from back stack
        }, 3000) // 3 seconds delay
    }
}


