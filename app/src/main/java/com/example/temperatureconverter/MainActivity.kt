package com.example.temperatureconverter

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var temperatureField: EditText
    private lateinit var fromSpinner: Spinner
    private lateinit var toSpinner: Spinner
    private lateinit var resultField: TextView

    private val temperatureTypes = arrayOf(
        "Choose the Temperature",
        "Celsius (°C)",
        "Fahrenheit (°F)",
        "Kelvin (°K)",
        "Rankine (°R)"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        temperatureField = findViewById(R.id.temperatureField)
        fromSpinner = findViewById(R.id.fromSpinner)
        toSpinner = findViewById(R.id.toSpinner)
        resultField = findViewById(R.id.resultField)

        val convertButton = findViewById<Button>(R.id.convertButton)
        convertButton.setOnClickListener {
            convertTemperature()
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, temperatureTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fromSpinner.adapter = adapter
        toSpinner.adapter = adapter
    }

    private fun convertTemperature() {
        try {
            val temperature = temperatureField.text.toString().toDouble()
            val fromUnit = fromSpinner.selectedItem.toString()
            val toUnit = toSpinner.selectedItem.toString()

            val convertedTemperature = convert(temperature, fromUnit, toUnit)
            val resultText = String.format("%.2f %s", convertedTemperature, getTemperatureSymbol(toUnit))
            resultField.text = resultText
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show()
        }
    }

    private fun convert(value: Double, fromUnit: String, toUnit: String): Double {
        return when (fromUnit) {
            "Celsius (°C)" -> {
                when (toUnit) {
                    "Fahrenheit (°F)" -> (value * 9 / 5) + 32
                    "Kelvin (°K)" -> value + 273.15
                    "Rankine (°R)" -> (value + 273.15) * 9 / 5
                    else -> value
                }
            }
            "Fahrenheit (°F)" -> {
                when (toUnit) {
                    "Celsius (°C)" -> (value - 32) * 5 / 9
                    "Kelvin (°K)" -> (value + 459.67) * 5 / 9
                    "Rankine (°R)" -> value + 459.67
                    else -> value
                }
            }
            else -> value
        }
    }

    private fun getTemperatureSymbol(unit: String): String {
        return when {
            unit.contains("Celsius") -> "°C"
            unit.contains("Fahrenheit") -> "°F"
            unit.contains("Kelvin") -> "°K"
            unit.contains("Rankine") -> "°R"
            else -> ""
        }
    }
}
