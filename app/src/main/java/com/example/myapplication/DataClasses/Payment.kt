package com.example.myapplication.DataClasses

data class Payment(var paymentId: String? = null,
                   var bookingId: String? = null,
                   var uid: String? = null,
                   var cardType: String? = null,
                   var cardNumber: String? = null,
                   var cardName: String? = null,
                   var cardExp: String? = null,
                   var cardCVV: String? = null,
                   var amount: String? = null,
)
