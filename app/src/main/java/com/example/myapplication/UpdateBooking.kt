package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.DataClasses.Tour
import com.example.myapplication.databinding.ActivityUpdateBookingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateBooking : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBookingBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("Booking").child(uid)

        var dst = intent.getStringExtra("destination").toString()
        val id = intent.getStringExtra("id").toString()
        val nAdults = intent.getStringExtra("nAdults").toString()
        val nChildren = intent.getStringExtra("nChildren").toString()
        val vType = intent.getStringExtra("vType").toString()
        val nDays = intent.getStringExtra("nDays").toString()
        val tot = intent.getStringExtra("tot").toString()

        binding.etDestination.setText(dst)
        binding.etAdults.setText(nAdults)
        binding.etChildren.setText(nChildren)
        binding.etVehicleType.setText(vType)
        binding.etNoDays.setText(nDays)
        binding.tvTotal.text = tot

        binding.btnCalcTot.setOnClickListener {
            var destination = binding.etDestination.text.toString()
            var adultsCount = binding.etAdults.text.toString()
            var childrenCount = binding.etChildren.text.toString()
            var vehicleType = binding.etVehicleType.text.toString()
            var daysCount = binding.etNoDays.text.toString()

            if(destination.isEmpty() || adultsCount.isEmpty() || vehicleType.isEmpty() || daysCount.isEmpty()){
                if(destination.isEmpty()){
                    binding.etDestination.error = "Enter destination"
                }
                if(adultsCount.isEmpty()){
                    binding.etAdults.error = "Enter adults count"
                }
                if(vehicleType.isEmpty()){
                    binding.etVehicleType.error = "Enter vehicle type"
                }
                if(daysCount.isEmpty()){
                    binding.etNoDays.error = "Enter number of days"
                }
            } else {

                var adCount = adultsCount.toInt()
                var chCount = childrenCount.toInt()
                var nDays = daysCount.toInt()

                var totFee = (((adCount * 10) + (chCount * 5)) * nDays)
                var tot = totFee.toString()
                binding.tvTotal.text = tot

                binding.btnSubmit.setOnClickListener { //Id for new record
                    val map = HashMap<String,Any>()

                    //add data to hashMap
                    map["destination"] = destination
                    map["nadults"] = adultsCount
                    map["nchildren"] = childrenCount
                    map["vtype"] = vehicleType
                    map["ndays"] = daysCount
                    map["tot"] = tot


                    //update database from hashMap
                    databaseRef.child(id).updateChildren(map).addOnCompleteListener {
                        if( it.isSuccessful){
                            intent = Intent(applicationContext, MyBookings::class.java)
                            startActivity(intent)
                            Toast.makeText(this, "Booking Updated successfully", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        binding.btnDlt.setOnClickListener {
            databaseRef.child(id).removeValue().addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Booking deleted", Toast.LENGTH_SHORT).show()
                    intent = Intent(applicationContext, MyBookings::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}