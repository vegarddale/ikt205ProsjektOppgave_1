package com.example.ikt205prosjektoppgave_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ikt205prosjektoppgave_1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}