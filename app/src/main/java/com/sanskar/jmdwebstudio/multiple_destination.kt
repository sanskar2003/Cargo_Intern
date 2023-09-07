package com.sanskar.jmdwebstudio

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class multiple_destination : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multiple_destination)
        val proceed: Button = findViewById(R.id.proceed)
        proceed.setOnClickListener {
            val intent = Intent(this@multiple_destination, container_types::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }
    }
}