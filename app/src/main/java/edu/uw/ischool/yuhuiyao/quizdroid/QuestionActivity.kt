package edu.uw.ischool.yuhuiyao.quizdroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import android.content.Intent

class QuestionActivity : AppCompatActivity() {
    private var questions: List<Question> = emptyList()
    private var questionIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        val questionTextView = findViewById<TextView>(R.id.questionTextView)
        val answerChoicesGroup = findViewById<RadioGroup>(R.id.answerChoicesGroup)
        val submitButton = findViewById<Button>(R.id.submitButton)

        // Get the selected topic from the "Topic Overview" page
        val selectedTopic = intent.getStringExtra("selectedTopic")

        // Assign list of questions based on the selected topic
        when (selectedTopic) {
            "Math" -> questions = mathQuestions
            "Physics" -> questions = physicsQuestions
            "Marvel Super Heroes" -> questions = marvelQuestions
            else -> {
                // Handle unknown topics or provide a default behavior
            }
        }

        questionIndex = 0
        // Display the first question
        displayQuestion()

        submitButton.setOnClickListener {
            if (questionIndex < questions.size) {
                // Display the next question
                questionIndex++
                if (questionIndex < questions.size) {
                    displayQuestion()
                } else {
                    // Handle the end of the questions or provide a behavior
                }
            }
        }
    }

    private fun displayQuestion() {
        if (questionIndex < questions.size) {
            val question = questions[questionIndex]
            val questionTextView = findViewById<TextView>(R.id.questionTextView)
            val radioButtons = listOf(
                findViewById<RadioButton>(R.id.radioButton1),
                findViewById<RadioButton>(R.id.radioButton2),
                findViewById<RadioButton>(R.id.radioButton3),
                findViewById<RadioButton>(R.id.radioButton4)
            )

            questionTextView.text = question.questionText

            for ((index, radioButton) in radioButtons.withIndex()) {
                radioButton.text = question.answerChoices[index]
            }
        } else {
            // Handle the end of the questions or provide a behavior
        }
    }
}




//package edu.uw.ischool.yuhuiyao.quizdroid
//
//import android.os.Bundle
//import android.widget.RadioButton
//import android.widget.Button
//import android.widget.RadioGroup
//import android.widget.TextView
//import androidx.appcompat.app.AppCompatActivity
//import android.util.Log
//
//class QuestionActivity : AppCompatActivity() {
//    private data class Question(
//        val questionText: String,
//        val answerChoices: List<String>,
//        val correctAnswerIndex: Int
//    )
//
//    private val mathQuestions = listOf(
//        Question("What is 2 + 2?", listOf("3", "4", "5", "6"), 1),
//        Question("Solve for x: 3x - 7 = 14", listOf("3", "5", "7", "8"), 3)
//    )
//
//    private val physicsQuestions = listOf(
//        Question("What is the SI unit of force?", listOf("Newton", "Watt", "Joule", "Ampere"), 0),
//        Question("What is the acceleration due to gravity on Earth?", listOf("9.8 m/s²", "6.7 m/s²", "11.3 m/s²", "5.5 m/s²"), 0)
//    )
//
//    private val marvelQuestions = listOf(
//        Question("Who is the alter ego of Spider-Man?", listOf("Bruce Wayne", "Clark Kent", "Peter Parker", "Tony Stark"), 2),
//        Question("What is the name of Thor's enchanted hammer?", listOf("Mjolnir", "Excalibur", "Gungnir", "Stormbreaker"), 0)
//    )
//
//    private lateinit var questions: List<Question>
//    private var questionIndex = 0
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_question)
//
//        val questionTextView = findViewById<TextView>(R.id.questionTextView)
//        val answerChoicesGroup = findViewById<RadioGroup>(R.id.answerChoicesGroup)
//        val submitButton = findViewById<Button>(R.id.submitButton)
//
//        // Get the selected topic from the "Topic Overview" page
//        val selectedTopic = intent.getStringExtra("selectedTopic")
//
//        // Assign list of questions based on the selected topic
//        questions = when (selectedTopic) {
//            "Math" -> mathQuestions
//            "Physics" -> physicsQuestions
//            "Marvel Super Heroes" -> marvelQuestions
//            // Log the selected topic to verify its value
//
//            else -> emptyList()  // Handle unknown topics or provide a default behavior
//        }
//
//        // Display the first question
//        displayQuestion()
//
//        submitButton.setOnClickListener {
//            if (questionIndex < questions.size) {
//                // Handle the submission logic here
//                // Check the selected answer and update the questionIndex
//                // Display the next question or navigate to the results page
//                displayQuestion()
//                questionIndex++
//
//            }
//        }
//    }
//
//    private fun displayQuestion() {
//        val question = questions[questionIndex]
//        val questionTextView = findViewById<TextView>(R.id.questionTextView)
//        val radioButtons = listOf(
//            findViewById<RadioButton>(R.id.radioButton1),
//            findViewById<RadioButton>(R.id.radioButton2),
//            findViewById<RadioButton>(R.id.radioButton3),
//            findViewById<RadioButton>(R.id.radioButton4)
//        )
//
//        questionTextView.text = question.questionText
//
//        for ((index, radioButton) in radioButtons.withIndex()) {
//            radioButton.text = question.answerChoices[index]
//        }
//
//        // Update the UI with the current question
//    }
//}
//
