package com.mapp.ut03

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mapp.ut03.SettingsDataStore
import com.mapp.ut03.databinding.ActivitySettingsBinding
import kotlinx.coroutines.launch

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var settingsDataStore: SettingsDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settingsDataStore = SettingsDataStore(this)

        lifecycleScope.launch {
            settingsDataStore.getEuroToDollarRate().collect { rate ->
                binding.etnEuroDolar.setText(rate.toString())
            }
        }

        lifecycleScope.launch {
            settingsDataStore.getDollarToEuroRate().collect { rate ->
                binding.etnDolarEuro.setText(rate.toString())
            }
        }

        binding.btnGuardar.setOnClickListener {
            guardarPreferencias()
        }
    }

    private fun guardarPreferencias() {
        val euroToDollarRate = binding.etnEuroDolar.text.toString().toDoubleOrNull()
        val dollarToEuroRate = binding.etnDolarEuro.text.toString().toDoubleOrNull()

        if (euroToDollarRate == null || dollarToEuroRate == null || euroToDollarRate <= 0.0 || dollarToEuroRate <= 0.0) {
            Toast.makeText(this, "Valores inválidos", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            settingsDataStore.saveEuroToDollarRate(euroToDollarRate)
            settingsDataStore.saveDollarToEuroRate(dollarToEuroRate)
            Toast.makeText(this@SettingsActivity, "Preferencias guardadas", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}