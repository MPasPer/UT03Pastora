package com.example.tema2

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tema2.databinding.ActivityMainConversorBinding
import java.text.DecimalFormat

class MainActivityConversor : AppCompatActivity() {

    private lateinit var binding: ActivityMainConversorBinding
    private val decimalFormat = DecimalFormat("0.00") // Para formatear a 2 decimales
    private val cambioFormat = DecimalFormat("0.00000") // Para formatear a 5 decimales

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainConversorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar funcionalidad inicial
        configurarListeners()
    }

    private fun configurarListeners() {
        // Listener para la cantidad ingresada
        binding.etnCantidad.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                actualizarResultado()
            }
        })

        // Listener para el cambio ingresado
        binding.etnCambio.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                // Al cambiar el valor de cambio, reiniciamos los valores
                if (!binding.etnCambio.text.isNullOrEmpty()) {
                    binding.etnCantidad.text.clear()
                    binding.txtResultado.text = "0.00"
                }
            }
        })

        // Listener para los RadioButtons
        binding.rdgconversion.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbtEuroDolar -> {
                    Toast.makeText(this, "Convertir Euros a Dólares", Toast.LENGTH_SHORT).show()
                }
                R.id.rbtDolarEuro -> {
                    Toast.makeText(this, "Convertir Dólares a Euros", Toast.LENGTH_SHORT).show()
                }
            }
            actualizarResultado()
        }
    }

    private fun actualizarResultado() {
        // Validar entradas
        val cantidadStr = binding.etnCantidad.text.toString()
        val cambioStr = binding.etnCambio.text.toString()

        if (cantidadStr.isEmpty() || cambioStr.isEmpty()) {
            binding.txtResultado.text = "0.00"
            return
        }

        val cantidad = cantidadStr.toDoubleOrNull()
        val cambio = cambioStr.toDoubleOrNull()

        if (cantidad == null || cambio == null || cantidad < 0.05 || cambio <= 0.0) {
            binding.txtResultado.text = "0.00"
            return
        }

        // Cálculo según la selección de conversión
        val resultado = if (binding.rbtEuroDolar.isChecked) {
            cantidad * cambio
        } else {
            cantidad / cambio
        }

        // Mostrar el resultado formateado a 2 decimales
        binding.txtResultado.text = decimalFormat.format(resultado)
    }
}
