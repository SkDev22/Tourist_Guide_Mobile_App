package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Toast
import com.example.myapplication.databinding.ActivityPaymentReceiptBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PaymentReceipt : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentReceiptBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentReceiptBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var cardType = intent.getStringExtra("cardType").toString()
        val cardNumber = intent.getStringExtra("cardNumber").toString()
        val cardName = intent.getStringExtra("cardName").toString()
        val cardExp = intent.getStringExtra("cardExp").toString()
        val cardCVV = intent.getStringExtra("cardCVV").toString()
        val paymentId = intent.getStringExtra("paymentId").toString()
        val totFee = intent.getStringExtra("totFee").toString()

        //bind values to editTexts
        binding.tvName.text = cardName
        binding.tvAmt.text = totFee

        binding.btnOk.setOnClickListener {
            startActivity(Intent(this,Home::class.java))
        }

        binding.btnUpdate.setOnClickListener {
            intent = Intent(applicationContext, UpdatePayment::class.java).also {
                it.putExtra("cardType", cardType)
                it.putExtra("cardNumber", cardNumber)
                it.putExtra("cardName", cardName)
                it.putExtra("cardExp", cardExp)
                it.putExtra("cardCVV", cardCVV)
                it.putExtra("paymentId", paymentId)
                it.putExtra("totFee", totFee)
                startActivity(it)
            }
        }





    }
}