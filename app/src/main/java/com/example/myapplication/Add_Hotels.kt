package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.DataClasses.Hotel
import com.example.myapplication.databinding.ActivityAddHotelsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Add_Hotels : AppCompatActivity() {

    private lateinit var binding: ActivityAddHotelsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // calls the "onCreate" method of the  "AppCompatActivity" class, and passes the "savedInstanceState" bundle object to it
        binding = ActivityAddHotelsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //initialize variables
        auth = FirebaseAuth.getInstance() //Firebase Authentication object is created by calling FirebaseDatabase.getInstance()
        databaseRef = FirebaseDatabase.getInstance().reference.child("Hotels") //Firebase Realtime Database object is created by calling FirebaseDatabase.getInstance().reference.child("Hotels")

        binding.btnAdd.setOnClickListener {
            var name = binding.etName.text.toString()
            var location = binding.etLocation.text.toString()
            var description = binding.etDescription.text.toString()

            //validation
            if(name.isEmpty() || description.isEmpty() || location.isEmpty()){
                if(name.isEmpty()){
                    binding.etName.error = "Enter name"
                }
                if(description.isEmpty()){
                    binding.etDescription.error = "Enter Description"
                }
                if(location.isEmpty()){
                    binding.etLocation.error = "Enter location"
                }
            } else {
                //Id for new record
                var id = databaseRef.push().key!!
                val data = Hotel( name,location,description,id)
                databaseRef.child(id).setValue(data).addOnCompleteListener {
                    if (it.isSuccessful){
                        intent = Intent(applicationContext, ViewAllHotelsAdmin::class.java)
                        startActivity(intent)
                        Toast.makeText(this, "Added successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}