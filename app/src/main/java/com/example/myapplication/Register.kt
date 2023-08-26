package com.example.myapplication

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.myapplication.DataClasses.User
import com.example.myapplication.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Register : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    //email pattern
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initializing auth and database variables
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        binding.btnSignUp.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val pwd = binding.etPwd.text.toString()

            if(name.isEmpty() || email.isEmpty() || pwd.isEmpty()){

                if(name.isEmpty()){
                    binding.etName.error = "Enter your name"
                }
                if(email.isEmpty()){
                    binding.etEmail.error = "Enter your email"
                }
                if(pwd.isEmpty()){
                    binding.etPwd.error = "Enter password"
                }
            }

            else if (!email.matches(emailPattern.toRegex())) {
                binding.etEmail.error = "Enter a valid email address"

            }
            else if (pwd.length < 7) {
                binding.etPwd.error = "Password must be at least 7 characters."

            } else if (!binding.checkBox.isChecked) {
                Toast.makeText(this, "Please accept terms and conditions.", Toast.LENGTH_SHORT).show()
            }else {
                auth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener {

                    if (it.isSuccessful) {
                        //store user details in the database
                        val databaseRef =
                            database.reference.child("users").child(auth.currentUser!!.uid)
                        val user: User = User(name, email,auth.currentUser!!.uid,"user")
                        databaseRef.setValue(user).addOnCompleteListener {
                            if (it.isSuccessful) {
                                //redirect user to login activity
                                val intent = Intent(this, Login::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this, "Something went wrong, try again", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(this, "Something went wrong, try again", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}