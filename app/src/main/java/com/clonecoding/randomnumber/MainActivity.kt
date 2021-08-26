package com.clonecoding.randomnumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast

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

            val textView = this.numberTextViewList[this.pickNumberSet.size]
            textView.visibility = View.VISIBLE
            textView.text = this.numberPicker.value.toString()

            this.pickNumberSet.add(this.numberPicker.value)
        }
    }

    /**
     * Init run button click listener
     */
    private fun initRunButton() {

        this.runButton.setOnClickListener {

            val list = this.getRandomNumber()
        }
    }

    /**
     * Get random number list (6)
     */
    private fun getRandomNumber(): List<Int> {

        val numberList = mutableListOf<Int>()
            .apply {
                for (i in MIN_NUM..MAX_NUM) {
                    this.add(i)
                }
            }

        numberList.shuffle()

        return numberList.subList(0, 6).sorted()
    }


}