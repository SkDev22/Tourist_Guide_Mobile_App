package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.DataClasses.User
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        if( auth.currentUser != null ) {
            checkUserType()
        }

        binding.btnRegister.setOnClickListener {
            intent = Intent(applicationContext, Register::class.java)
            startActivity(intent)
        }

        binding.btnSignIn.setOnClickListener {
            intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
        }

    }

    private fun checkUserType() {
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("users").child(uid)
        databaseRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                //retrieve values from the db and convert them to user data class
                var user = snapshot.getValue(User::class.java)!!

                if( user.type == "admin") {
                    intent = Intent(applicationContext, AdminDashboard::class.java)
                    startActivity(intent)
                } else if( user.type == "user"){
                    intent = Intent(applicationContext, Home::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@MainActivity, "Your account is not valid.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "Failed to retrieve user", Toast.LENGTH_SHORT).show()
            }
        })
    }


}