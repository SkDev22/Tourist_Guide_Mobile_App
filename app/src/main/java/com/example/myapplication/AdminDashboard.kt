package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.databinding.ActivityAdminDashboardBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AdminDashboard : AppCompatActivity() {

    private lateinit var binding: ActivityAdminDashboardBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize variables
        auth = FirebaseAuth.getInstance()
        binding.btnLogout.setOnClickListener {
            Firebase.auth.signOut()

            //redirect user to login page
            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)

            //toast message
            Toast.makeText(this, "Successfully logged out", Toast.LENGTH_SHORT).show()
        }

        binding.btnHotels.setOnClickListener {
            intent = Intent(applicationContext, ViewAllHotelsAdmin::class.java)
            startActivity(intent)
        }

        binding.btnPlaces.setOnClickListener {
            intent = Intent(applicationContext, ViewAllPlacesAdmin::class.java)
            startActivity(intent)
        }

        binding.btnEvents.setOnClickListener {
            intent = Intent(applicationContext, viewAllEventsAdmin::class.java)
            startActivity(intent)
        }
    }
}