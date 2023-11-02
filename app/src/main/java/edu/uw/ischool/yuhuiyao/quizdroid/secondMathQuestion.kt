package edu.uw.ischool.yuhuiyao.quizdroid


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.RadioButton
import android.widget.TextView
import android.view.View
import android.content.Intent

class secondMathQuestion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_math_question)

        val options = findViewById<RadioGroup>(R.id.answerRadioGroup)
        val submitButton = findViewById<Button>(R.id.submitButton)

        options.setOnCheckedChangeListener { _, _ ->
            submitButton.visibility = View.VISIBLE
        }

        val correctAnswersCount = intent.getIntExtra("correctAnswersCount", 0)

        submitButton.setOnClickListener{
            val selectedRadioButtonId = options.checkedRadioButtonId
            if (selectedRadioButtonId != -1) {
                // User has selected an answer. Get the selected answer text.
                val selectedRadioButton = findViewById<RadioButton>(selectedRadioButtonId)
                val selectedAnswer = selectedRadioButton.text.toString()

                // Create an Intent to start the AnswerActivity and pass the selected answer as an extra.
                val secondMathQuestionIntent = Intent(this, secondMathAnswer::class.java)
                secondMathQuestionIntent.putExtra("userAnswer", selectedAnswer)
                secondMathQuestionIntent.putExtra("correctAnswersCount", correctAnswersCount)
                startActivity(secondMathQuestionIntent)
            } else {
                // Display a message to select an answer or do nothing.
            }
        }
    }
}