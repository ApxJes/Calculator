package com.apsmt.calculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.apsmt.calculator.databinding.ActivityMainBinding
import org.mozilla.javascript.Script
import kotlin.time.times


class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private var isResultUsed: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnClear.setOnClickListener {
            clearAllAction(it)
        }

        binding.btnBack.setOnClickListener {
            backSpaceAction(it)
        }

        binding.btnPercentage.setOnClickListener {
            percentageAction(it)
        }

        binding.btnDecimal.setOnClickListener {
            decimalAction(it)
        }

        binding.btnPlus.setOnClickListener {
            operatorAction("+")
        }

        binding.btnMinus.setOnClickListener {
            operatorAction("-")
        }

        binding.btnMultiply.setOnClickListener {
            operatorAction("*")
        }

        binding.btnDivide.setOnClickListener {
            operatorAction("/")
        }

        binding.btnEqual.setOnClickListener {
            equalAction()
        }

        binding.btnOne.setOnClickListener {
            numberAction(it)
        }

        binding.btnTwo.setOnClickListener {
            numberAction(it)
        }

        binding.btnThree.setOnClickListener {
            numberAction(it)
        }

        binding.btnFour.setOnClickListener {
            numberAction(it)
        }

        binding.btnFive.setOnClickListener {
            numberAction(it)
        }

        binding.btnSix.setOnClickListener {
            numberAction(it)
        }

        binding.btnSeven.setOnClickListener {
            numberAction(it)
        }

        binding.btnEight.setOnClickListener {
            numberAction(it)
        }

        binding.btnNine.setOnClickListener {
            numberAction(it)
        }

        binding.btnZero.setOnClickListener {
            numberAction(it)
        }

    }

    private fun numberAction(view: View) {
        if(view is Button) {
            if(isResultUsed){
                binding.txvWorking.text = ""
                isResultUsed = false
            }

            binding.txvWorking.append(view.text)
        }
    }

    private fun backSpaceAction(view: View) {
        val length = binding.txvWorking.text.length
        if(length >= 1) {
            binding.txvWorking.text = binding.txvWorking.text.subSequence(0, length -1)
        }
    }

    private fun clearAllAction(view: View) {
        binding.txvWorking.text = ""
        binding.txvResult.text = ""
        isResultUsed = false
    }

    @SuppressLint("SetTextI18n")
    private fun percentageAction(view: View) {
        if(view is Button) {
            val currentText = binding.txvWorking.text
            if(currentText.isNotEmpty() && currentText.last() !in "+-*/") {

                try {
                    if (binding.txvResult.text.isNotEmpty()) {
                        binding.txvWorking.text = "%"
                        binding.txvResult.text =
                            (binding.txvResult.text.toString().toDouble() / 100).toString()
                    } else {
                        binding.txvResult.text =
                            (currentText.toString().toDouble() / 100).toString()
                    }
                }catch (e: Exception) {
                    binding.txvResult.text = "Error"
                }
            }
        }
    }

    private fun operatorAction(operator: String) {
        val currentText = binding.txvWorking.text

        if(isResultUsed) {
            val result = binding.txvResult.text.toString()
            binding.txvWorking.text = result
            isResultUsed = false
        }

        if(currentText.isNotEmpty() && currentText.last() !in ("+-*/")){
            binding.txvWorking.append(operator)
        }
    }

    private fun decimalAction(view: View) {
        if(view is Button) {
            val currentText = binding.txvWorking.text

            if(currentText.isNotEmpty() && currentText.last() !in (".")) {
                binding.txvWorking.append(".")
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun equalAction(){
        try {
            val expression = binding.txvWorking.text
            val calculatingResult = evaluateExpression(expression.toString())

            binding.txvResult.text = calculatingResult.toString()
            isResultUsed = true
        } catch (e: Exception) {
            binding.txvResult.text = "Error"
        }

    }

    private fun evaluateExpression(expression: String): Double {
        val rhino = org.mozilla.javascript.Context.enter()
        rhino.optimizationLevel = -1
        val scope: org.mozilla.javascript.Scriptable = rhino.initStandardObjects()
        return (rhino.evaluateString(scope, expression, "JavaScript", 1, null ) )as Double
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}