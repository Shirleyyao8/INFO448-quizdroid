package edu.uw.ischool.yuhuiyao.quizdroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.RadioButton
import android.widget.TextView
import android.view.View
import android.content.Intent

class firstMarvelQuestion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_marvel_question)

        val options = findViewById<RadioGroup>(R.id.answers)
        val submitButton = findViewById<Button>(R.id.submitButton)

        options.setOnCheckedChangeListener { _, _ ->
            submitButton.visibility = View.VISIBLE
        }

        submitButton.setOnClickListener{
            val selectedRadioButtonId = options.checkedRadioButtonId
            if (selectedRadioButtonId != -1) {
                // User has selected an answer. Get the selected answer text.
                val selectedRadioButton = findViewById<RadioButton>(selectedRadioButtonId)
                val selectedAnswer = selectedRadioButton.text.toString()

                // Create an Intent to start the AnswerActivity and pass the selected answer as an extra.
                val firstMathQuestionIntent = Intent(this, firstMarvelAnswer::class.java)
                firstMathQuestionIntent.putExtra("userAnswer", selectedAnswer)
                startActivity(firstMathQuestionIntent)
            } else {
                // Display a message to select an answer or do nothing.
            }
        }
    }
}