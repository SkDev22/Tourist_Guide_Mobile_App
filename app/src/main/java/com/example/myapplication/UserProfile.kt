package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.DataClasses.User
import com.example.myapplication.databinding.ActivityUserProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class UserProfile : AppCompatActivity() {
    private lateinit var binding: ActivityUserProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("users").child(uid)

        //retrieve user data
        databaseRef.addValueEventListener(object :
            ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                //retrieve values from the db and convert them to user data class
                var user = snapshot.getValue(User::class.java)!!

                binding.tvName.text = user.name
                binding.tvWelcomeName.text = user.name
                binding.tvEmail.text = user.email

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@UserProfile, "Faild to fetch user", Toast.LENGTH_SHORT).show()

            }
        })


        binding.btnLogout.setOnClickListener {
            Firebase.auth.signOut()

            //redirect user to login page
            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)

            //toast message
            Toast.makeText(this, "Successfully logged out", Toast.LENGTH_SHORT).show()
        }

        binding.btnMyBookings.setOnClickListener {
            intent = Intent(applicationContext, MyBookings::class.java)
            startActivity(intent)
        }


    }
}