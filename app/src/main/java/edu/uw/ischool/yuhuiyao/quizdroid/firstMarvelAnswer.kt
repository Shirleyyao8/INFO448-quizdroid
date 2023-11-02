package edu.uw.ischool.yuhuiyao.quizdroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class firstMarvelAnswer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_marvel_answer)

        val userAnswerTextView = findViewById<TextView>(R.id.userAnswerTextView)
        val correctAnswerTextView = findViewById<TextView>(R.id.correctAnswerTextView)
        val resultTextView = findViewById<TextView>(R.id.resultTextView)
        val nextButton = findViewById<Button>(R.id.nextButton)

        // Retrieve the user's answer and correct answer from the Intent.
        val userAnswer = intent.getStringExtra("userAnswer")
        val correctAnswer = "Peter Parker" // Set the correct answer here.

        // Set the text for the user's answer and correct answer TextViews.
        userAnswerTextView.text = "Your Answer: $userAnswer"
        correctAnswerTextView.text = "Correct Answer: $correctAnswer"

        // Compare the user's answer with the correct answer.
        val isCorrect = userAnswer == correctAnswer


        var correctAnswersCount = 0 // Initialize this variable accordingly.
        val totalQuestionsCount = 2

        if (isCorrect) {
            correctAnswersCount++
        }


        resultTextView.text = "You have $correctAnswersCount out of $totalQuestionsCount correct."

        // You can implement navigation to the next question or Finish Page when clicking the "Next" button.
        nextButton.setOnClickListener {
            val firstMarvelAnswerIntent = Intent(this, secondMarvelQuestion::class.java)
            firstMarvelAnswerIntent.putExtra("correctAnswersCount", correctAnswersCount)
            startActivity(firstMarvelAnswerIntent)
        }
    }
}
