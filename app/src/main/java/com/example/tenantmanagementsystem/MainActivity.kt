package com.example.tenantmanagementsystem

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tenantmanagementsystem.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveButton.setOnClickListener {
            val name = binding.tenantNameEditText.text.toString()
            if (name.isEmpty()) {
                binding.tenantNameEditText.error = "Tenant name is required"
                return@setOnClickListener
            }
            val phone = binding.phoneEditText.text.toString()
            val rent = binding.rentEditText.text.toString()

            val tenant = Tenant(name, phone, rent)
            binding.tenant = tenant
            binding.tenantNameEditText.text.clear()
            binding.phoneEditText.text.clear()
            binding.rentEditText.text.clear()
        }
    }
}