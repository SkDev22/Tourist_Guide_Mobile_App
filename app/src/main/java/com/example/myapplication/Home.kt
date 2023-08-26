package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityHomeBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Home : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnProfile.setOnClickListener {
            intent = Intent(applicationContext, UserProfile::class.java)
            startActivity(intent)
        }

        binding.btnHotels.setOnClickListener {
            intent = Intent(applicationContext, ViewAllHotelsUser::class.java)
            startActivity(intent)
        }

        binding.btnPlaces.setOnClickListener {
            intent = Intent(applicationContext, ViewAllPlacesUser::class.java)
            startActivity(intent)
        }

        binding.btnEvents.setOnClickListener {
            intent = Intent(applicationContext, ViewAllEventsUser::class.java)
            startActivity(intent)
        }

        binding.btnBookTour.setOnClickListener {
            intent = Intent(applicationContext, BookTour::class.java)
            startActivity(intent)
        }



    }
}