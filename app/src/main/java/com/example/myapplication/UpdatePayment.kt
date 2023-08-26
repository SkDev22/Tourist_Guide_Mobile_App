package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Toast
import com.example.myapplication.databinding.ActivityUpdateHotelBinding
import com.example.myapplication.databinding.ActivityUpdatePaymentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdatePayment : AppCompatActivity() {
    private lateinit var binding: ActivityUpdatePaymentBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdatePaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var cardType = intent.getStringExtra("cardType").toString()
        var cardNumber = intent.getStringExtra("cardNumber").toString()
        var cardName = intent.getStringExtra("cardName").toString()
        var cardExp = intent.getStringExtra("cardExp").toString()
        var cardCVV = intent.getStringExtra("cardCVV").toString()
        var paymentId = intent.getStringExtra("paymentId").toString()
        var totFee = intent.getStringExtra("totFee").toString()


        binding.etCardType.setText(cardType)
        binding.etCardNo.setText(cardNumber)
        binding.etNameOnCard.setText(cardName)
        binding.etExpiration.setText(cardExp)
        binding.etCVV.setText(cardCVV)
        binding.tvTotAmt.text = totFee

        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("payments").child(paymentId)

        binding.btnUpdate.setOnClickListener {

             cardType = binding.etCardType.text.toString()
             cardNumber = binding.etCardNo.text.toString()
             cardName = binding.etNameOnCard.text.toString()
             cardExp = binding.etExpiration.text.toString()
             cardCVV = binding.etCVV.text.toString()

            if(cardType.isEmpty() || cardNumber.isEmpty() || cardName.isEmpty() || cardExp.isEmpty() || cardCVV.isEmpty()){

                if(cardType.isEmpty()){
                    binding.etCardType.error = "Enter card type"
                }
                if(cardNumber.isEmpty()){
                    binding.etCardNo.error = "Enter card number"
                }
                if(cardName.isEmpty()){
                    binding.etNameOnCard.error = "Enter name"
                }
                if(cardExp.isEmpty()){
                    binding.etExpiration.error = "Enter expiration date"
                }
                if(cardCVV.isEmpty()){
                    binding.etCVV.error = "Enter cvv"
                }
            } else if (cardNumber.length != 16) {
                binding.etCardNo.error = "Enter a valid card number"

            }else if (cardCVV.length != 3) {
                binding.etCVV.error = "Enter a valid CVV"

            }else if (!binding.checkBox.isChecked) {
                Toast.makeText(this, "Please accept terms and conditions", Toast.LENGTH_SHORT).show()

            }else {

                val map = HashMap<String,Any>()

                //add data to hashMap
                map["cardType"] = cardType
                map["cardNumber"] = cardNumber
                map["cardName"] = cardName
                map["cardExp"] = cardExp
                map["cardCVV"] = cardCVV


                //update database from hashMap
                databaseRef.updateChildren(map).addOnCompleteListener {
                    if( it.isSuccessful){
                        intent = Intent(applicationContext, Home::class.java)
                        startActivity(intent)
                        Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


        binding.btnDlt.setOnClickListener {
            databaseRef.removeValue().addOnCompleteListener {
                if( it.isSuccessful) {
                    Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show()
                    intent = Intent(applicationContext, Home::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}