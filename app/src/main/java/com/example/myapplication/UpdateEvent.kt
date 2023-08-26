package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.databinding.ActivityUpdateEventBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateEvent : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateEventBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateEventBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //initialize variables
        databaseRef = FirebaseDatabase.getInstance().reference.child("Events")

        var name = intent.getStringExtra("name").toString()
        var id = intent.getStringExtra("id").toString()
        var description = intent.getStringExtra("description").toString()
        var location = intent.getStringExtra("location").toString()

        binding.etName.setText(name)
        binding.etDescription.setText(description)
        binding.etLocation.setText(location)

        binding.btnUpdate.setOnClickListener {
            name = binding.etName.text.toString()
            description = binding.etDescription.text.toString()
            location = binding.etLocation.text.toString()


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
                val map = HashMap<String, Any>()

                //add data to hashMap
                map["name"] = name
                map["description"] = description
                map["location"] = location


                //update database
                databaseRef.child(id).updateChildren(map).addOnCompleteListener {
                    if (it.isSuccessful) {
                        intent = Intent(applicationContext, viewAllEventsAdmin::class.java)
                        startActivity(intent)
                        Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        //delete
        binding.btnDelete.setOnClickListener {
            databaseRef.child(id).removeValue().addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show()
                    intent = Intent(applicationContext, viewAllEventsAdmin::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}
