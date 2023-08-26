package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Toast
import com.example.myapplication.DataClasses.Payment
import com.example.myapplication.databinding.ActivityPayNowBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PayNow : AppCompatActivity() {
    private lateinit var binding: ActivityPayNowBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayNowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("payments")

        //fetch data from the intent
        val bookingId = intent.getStringExtra("bookingId").toString()
        val totFee = intent.getStringExtra("totFee").toString()

        binding.tvTotal.text = totFee

        binding.btnCancel.setOnClickListener{
            startActivity(Intent(this,Home::class.java))
        }

        binding.btnPayNow.setOnClickListener {
            var cardType = binding.etCardType.text.toString()
            var cardNumber = binding.etCardNo.text.toString()
            var cardName = binding.etNameOnCard.text.toString()
            var cardExp = binding.etExpiration.text.toString()
            var cardCVV = binding.etCVV.text.toString()

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
                var id = databaseRef.push().key!!
                //create a object
                val data = Payment( id,bookingId,uid, cardType, cardNumber, cardName, cardExp, cardCVV,totFee)
                databaseRef.child(id).setValue(data).addOnCompleteListener {
                    if (it.isSuccessful){
                        intent = Intent(applicationContext, PaymentReceipt::class.java).also {
                            it.putExtra("cardType", cardType)
                            it.putExtra("cardNumber", cardNumber)
                            it.putExtra("cardName", cardName)
                            it.putExtra("cardExp", cardExp)
                            it.putExtra("cardCVV", cardCVV)
                            it.putExtra("paymentId", id)
                            it.putExtra("totFee", totFee)
                            startActivity(it)
                        }
                        Toast.makeText(this, "Added successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}