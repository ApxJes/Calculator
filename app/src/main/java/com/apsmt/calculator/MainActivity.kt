package com.apsmt.calculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.apsmt.calculator.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private var isReused: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnClear.setOnClickListener { clearAllAction() }
        binding.btnBack.setOnClickListener { backSpaceAction() }
        binding.btnPlus.setOnClickListener { addOperation(it) }
        binding.btnMinus.setOnClickListener { minusOperation(it) }
        binding.btnMultiply.setOnClickListener { multiplyOperation(it) }
        binding.btnDivide.setOnClickListener { dividedOperation(it) }
        binding.btnPercentage.setOnClickListener { findPercentageOperation(it) }
        binding.btnDecimal.setOnClickListener { addDecimalAction(it) }
        binding.btnEqual.setOnClickListener { equalAction() }

        binding.btnOne.setOnClickListener { numberAction(it) }
        binding.btnTwo.setOnClickListener { numberAction(it) }
        binding.btnThree.setOnClickListener { numberAction(it) }
        binding.btnFour.setOnClickListener { numberAction(it) }
        binding.btnFive.setOnClickListener { numberAction(it) }
        binding.btnSix.setOnClickListener { numberAction(it) }
        binding.btnSeven.setOnClickListener { numberAction(it) }
        binding.btnEight.setOnClickListener { numberAction(it) }
        binding.btnNine.setOnClickListener { numberAction(it) }
        binding.btnZero.setOnClickListener { numberAction(it) }

    }

    private fun numberAction(view: View) {
        if (view is Button) {
            if (isReused) {
                binding.txvWorking.text = binding.txvResult.text
                binding.txvResult.text = ""
                isReused = false
            }
            binding.txvWorking.append(view.text)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun backSpaceAction() {
        if (isReused) {
            binding.txvWorking.text = "${binding.txvResult.text} %"
            binding.txvResult.text = ""
            isReused = false
        } else {
            val length = binding.txvWorking.text.toString().length
            if (length > 0) {
                binding.txvWorking.text = binding.txvWorking.text.subSequence(0, length - 1)
            }
        }
    }

    private fun clearAllAction() {
        binding.txvWorking.text = ""
        binding.txvResult.text = ""
        isReused = false
    }

    private fun operatorHandel(view: View) {
        if (view is Button) {
            if (isReused) {
                binding.txvWorking.text = binding.txvResult.text
                binding.txvResult.text = ""
                isReused = false
            }
            val workingText = binding.txvWorking.text.toString()
            if (workingText.isNotEmpty() && workingText.last() !in "+-*/") {
                binding.txvWorking.append(view.text)
            }
        }
    }

    private fun addOperation(view: View) {
        operatorHandel(view)
    }

    private fun minusOperation(view: View) {
        operatorHandel(view)
    }

    private fun multiplyOperation(view: View) {
        operatorHandel(view)
    }

    private fun dividedOperation(view: View) {
        operatorHandel(view)
    }

    @SuppressLint("SetTextI18n")
    private fun findPercentageOperation(view: View) {
        if (view is Button) {
            if (isReused) {
                binding.txvWorking.text = binding.txvResult.text
                binding.txvResult.text = ""
                binding.txvResult.text =
                    (binding.txvWorking.text.toString().toDouble() / 100).toString()
            } else {
                try {
                    if (binding.txvWorking.text.isNotEmpty()) {
                        binding.txvResult.text =
                            (binding.txvWorking.text.toString().toDouble() / 100).toString()
                    }
                } catch (e: Exception) {
                    binding.txvResult.text = "Error"
                }
            }
        }
    }

    private fun addDecimalAction(view: View) {
        if (view is Button) {
            val workingText = binding.txvWorking.text.toString()
            if (workingText.isNotEmpty() && workingText.last() !in ".") {
                binding.txvWorking.append(view.text)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun equalAction() {
        val workingText = binding.txvWorking.text
        try {
            if (workingText.isNotEmpty()) {
                val result = evaluateExpression(workingText.toString())
                binding.txvResult.text = result.toString()
                isReused = true
            }
        } catch (e: Exception) {
            binding.txvResult.text = "Error"
        }

    }

    private fun evaluateExpression(expression: String): Double {
        val rhino = org.mozilla.javascript.Context.enter()
        rhino.optimizationLevel = -1
        val result =
            rhino.evaluateString(rhino.initStandardObjects(), expression, "Javascript", 1, null)
        return result.toString().toDouble()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}