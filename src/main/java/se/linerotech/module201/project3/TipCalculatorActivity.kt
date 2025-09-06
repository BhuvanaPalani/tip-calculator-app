package se.linerotech.module201.project3

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import java.util.Locale

class TipCalculatorActivity : AppCompatActivity() {

    companion object {
        private const val MIN_TIP_PERCENT = 0
        private const val MAX_TIP_PERCENT = 20
        private const val PERCENT_DIVISOR = 100.0
    }

    private lateinit var etBillAmount: EditText
    private lateinit var tvTipPercent: TextView
    private lateinit var tvTotalAmount: TextView
    private lateinit var tvBill: TextView
    private lateinit var tvTip: TextView
    private var tipPercent = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tip_calculator)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etBillAmount = findViewById(R.id.etBillAmount)
        tvTipPercent = findViewById(R.id.tvTipPercent)
        tvTotalAmount = findViewById(R.id.tvTotalAmount)
        tvBill = findViewById(R.id.tvBill)
        tvTip = findViewById(R.id.tvTip)

        val buttonPlus = findViewById<Button>(R.id.buttonPlus)
        val buttonMinus = findViewById<Button>(R.id.buttonMinus)

        etBillAmount.doAfterTextChanged { updateDisplay() }

        buttonPlus.setOnClickListener {
            if (etBillAmount.text.isNullOrBlank()) {
                showToast("Enter bill amount first")
                return@setOnClickListener
            }
            if (tipPercent < MAX_TIP_PERCENT) {
                tipPercent++
                updateDisplay()
            }
        }

        buttonMinus.setOnClickListener {
            if (etBillAmount.text.isNullOrBlank()) {
                showToast("Enter bill amount first")
                return@setOnClickListener
            }
            if (tipPercent > MIN_TIP_PERCENT) {
                tipPercent--
                updateDisplay()
            }
        }
    }

    @SuppressLint("SetTextI18n") // using inline "%" for the percent label
    private fun updateDisplay() {
        val billText = etBillAmount.text.toString()
        val bill = billText.toDoubleOrNull() ?: 0.0
        val tip = bill * tipPercent / PERCENT_DIVISOR
        val total = bill + tip

        tvTipPercent.text = "$tipPercent%"
        tvBill.text = String.format(Locale.US, "%.2f kr", bill)
        tvTip.text = String.format(Locale.US, "%.2f kr", tip)
        tvTotalAmount.text = String.format(Locale.US, "%.2f kr", total)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
