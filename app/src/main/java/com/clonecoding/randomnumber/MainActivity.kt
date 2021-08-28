package com.clonecoding.randomnumber

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

/**
 * Main Activity
 */
class MainActivity : AppCompatActivity() {

    /**
     * Max number, Number Picker
     */
    private val MAX_NUM: Int = 45

    /**
     * Min number, Number Picker
     */
    private val MIN_NUM: Int = 1

    /**
     * Init button
     */
    private val initButton: Button by lazy {
        findViewById(R.id.button_init)
    }

    /**
     * Add button
     */
    private val addButton: Button by lazy {
        findViewById(R.id.button_add)
    }

    /**
     * Run button
     */
    private val runButton: Button by lazy {
        findViewById(R.id.button_run)
    }

    /**
     * Number picker
     */
    private val numberPicker: NumberPicker by lazy {
        findViewById(R.id.number_picker)
    }

    /**
     * Did pick number
     */
    private var didRun = false

    /**
     * Pick number set
     */
    private val pickNumberSet = mutableSetOf<Int>()

    /**
     * Text view list
     */
    private val numberTextViewList: List<TextView> by lazy {
        listOf(
            findViewById(R.id.text_number1),
            findViewById(R.id.text_number2),
            findViewById(R.id.text_number3),
            findViewById(R.id.text_number4),
            findViewById(R.id.text_number5)
        )
    }

    /**
     * onCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.numberPicker.minValue = MIN_NUM
        this.numberPicker.maxValue = MAX_NUM

        this.initRunButton()
        this.initAddButton()
        this.initClearButton()
    }

    /**
     * Clear button
     */
    private fun initClearButton() {

        this.initButton.setOnClickListener {
            this.pickNumberSet.clear()
            this.numberTextViewList.forEach{
                it.visibility = View.GONE
            }
            this.didRun = false
        }
    }

    /**
     * Init add button click listener
     */
    private fun initAddButton() {

        this.addButton.setOnClickListener {

            if (this.didRun) {
                Toast.makeText(this, "초기화 후에 시도해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (this.pickNumberSet.size >= 5) {
                Toast.makeText(this, "번호는 최대 5개까지 선택하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (this.pickNumberSet.contains(numberPicker.value)) {
                Toast.makeText(this, "이미 선택한 번호입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Update text view
            val textView = this.numberTextViewList[this.pickNumberSet.size]
            this.updateNumberTextView(textView, this.numberPicker.value)

            this.pickNumberSet.add(this.numberPicker.value)
        }
    }

    /**
     * Update TextView style
     */
    private fun updateNumberTextView(textView: TextView, number: Int) {

        var viewBackground: Drawable?

        when(number) {
            in 1..10 -> viewBackground = ContextCompat.getDrawable(this, R.drawable.circle_yellow)
            in 11..20 -> viewBackground = ContextCompat.getDrawable(this, R.drawable.circle_blue)
            in 21..30 -> viewBackground = ContextCompat.getDrawable(this, R.drawable.circle_red)
            in 31..40 -> viewBackground = ContextCompat.getDrawable(this, R.drawable.circle_gray)
            else -> viewBackground = ContextCompat.getDrawable(this, R.drawable.circle_green)
        }

        textView.apply {
            visibility = View.VISIBLE
            text = number.toString()
            background = viewBackground
        }
    }

    /**
     * Init run button click listener
     */
    private fun initRunButton() {

        this.runButton.setOnClickListener {

            val list = this.getRandomNumber()

            this.didRun = true;

            list.forEachIndexed { index, i ->
                val textView = this.numberTextViewList[index]

                this.updateNumberTextView(textView, i)
            }
        }
    }

    /**
     * Get random number list (6)
     */
    private fun getRandomNumber(): List<Int> {

        val numberList = mutableListOf<Int>()
            .apply {
                for (i in MIN_NUM..MAX_NUM) {
                    if (pickNumberSet.contains(i)) {
                        continue
                    }
                    this.add(i)
                }
            }

        numberList.shuffle()

        val newList = this.pickNumberSet.toList() + numberList.subList(0, 5 - this.pickNumberSet.size)

        return newList.sorted()
    }
}