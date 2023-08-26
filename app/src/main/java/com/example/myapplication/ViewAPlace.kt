package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.ActivityViewAplaceBinding

class ViewAPlace : AppCompatActivity() {
    private lateinit var binding: ActivityViewAplaceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewAplaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvName.text = intent.getStringExtra("name").toString()
        binding.tvCity.text = intent.getStringExtra("location").toString()
        binding.tvDes.text = intent.getStringExtra("description").toString()
    }
}