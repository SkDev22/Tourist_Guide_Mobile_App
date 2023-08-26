package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.ActivityViewAnEventBinding

class ViewAnEvent : AppCompatActivity() {
    private lateinit var binding: ActivityViewAnEventBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewAnEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvName.text = intent.getStringExtra("name").toString()
        binding.tvDes.text = intent.getStringExtra("description").toString()

    }
}