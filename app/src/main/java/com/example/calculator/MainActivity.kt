package com.example.calculator

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.calculator.ui.theme.CalculatorTheme

class MainActivity : ComponentActivity() {

    private lateinit var tvResult: TextView
    private var currentNumber = ""
    private var operator = ""
    private var previousNumber = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        tvResult = findViewById(R.id.tvResult)
    }

    fun onDigitClick(view: View) {
        val button = view as Button
        currentNumber += button.text.toString()
        updateDisplay()

    }

    fun onClearEntry(view: View){
        currentNumber = ""
        updateDisplay()

    }
    fun onClear(view: View){
        currentNumber = ""
        previousNumber = ""
        operator = ""
        updateDisplay()
    }
    fun onBackspace(view: View){
        if(currentNumber.isNotEmpty()){
            currentNumber = currentNumber.dropLast(1)
            updateDisplay()
        }
    }
    fun onOperatorClick(view: View){
        val button = view as Button
        if(operator.isEmpty()) {
            operator = button.text.toString()
            previousNumber = currentNumber
            currentNumber = ""
            Log.v("TAG", operator)
        }
    }

    fun onToggleSign(view: View){
        if (currentNumber.isNotEmpty()) {
            currentNumber = if (currentNumber.startsWith("-")) {
                currentNumber.substring(1)
            } else {
                "-$currentNumber"
            }
            updateDisplay()
        }
    }
    fun onEqualClick(view: View){
        if(previousNumber.isNotEmpty() && currentNumber.isNotEmpty()){
            var num1 = previousNumber.toInt()
            var num2 = currentNumber.toInt()


            val result = when(operator){
                "+" ->  num1 + num2
                "-" ->  num1 - num2
                "x" -> num1 * num2
                "/" -> if(num2 == 0) "Error" else (num1 / num2)
                    else -> 0
            }
            tvResult.text = result.toString()
            currentNumber = result.toString()
            previousNumber = ""
            operator = ""
        }
    }


    private fun updateDisplay(){
        if(tvResult.text == "Error"){
            tvResult.text = "0"
        }else {
            tvResult.text = currentNumber.ifEmpty { "0" }
        }
    }
}

