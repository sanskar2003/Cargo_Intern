package com.sanskar.jmdwebstudio

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AirCargo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_air_cargo)
        val searchrate: Button = findViewById(R.id.search_rate)
        searchrate.setOnClickListener {
            val intent = Intent(this@AirCargo, search_rate::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }
    }
}