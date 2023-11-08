package edu.uw.ischool.yuhuiyao.quizdroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class QuestionActivity : AppCompatActivity() {

    private var questions: List<Question> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        val answerChoicesGroup = findViewById<RadioGroup>(R.id.answerChoicesGroup)
        val submitButton = findViewById<Button>(R.id.submitButton)

        // Only if users select an option, the submit button will be visible
        answerChoicesGroup.setOnCheckedChangeListener { _, _ ->
            submitButton.visibility = View.VISIBLE
        }

        // Get the selected topic from the "Topic Overview" page
        val selectedTopic = intent.getStringExtra("selectedTopic")
        var questionIndex = intent.getIntExtra("questionIndex", 0)
        var correctAnswerCount = intent.getIntExtra("correctAnswerCount", 0)

        // Initialize a topic repository
        val topicRepository: TopicRepository = InMemoryTopicRepository()

        // Find the selected topic from the repository based on the title
        val selectedTopicData = topicRepository.getTopics().find { it.title == selectedTopic }

        if (selectedTopicData != null) {
            // Assign list of questions based on the selected topic
            questions = selectedTopicData.questions
        } else {
            // Handle unknown topics or provide a default behavior
        }

        // Display the first question
        displayQuestion(questionIndex)

        submitButton.setOnClickListener {
            if (questionIndex < questions.size) {
                // Link to the answer page to check the answer
                val intent = Intent(this, AnswerActivity::class.java)
                intent.putExtra("selectedTopic", selectedTopic)
                intent.putExtra("questionIndex", questionIndex)
                intent.putExtra("correctAnswerCount", correctAnswerCount)

                val selectedRadioButtonId = answerChoicesGroup.checkedRadioButtonId

                if (selectedRadioButtonId != -1) {
                    val selectedRadioButton = findViewById<RadioButton>(selectedRadioButtonId)
                    val selectedAnswer = selectedRadioButton.text.toString()
                    intent.putExtra("userAnswer", selectedAnswer)
                } else {
                    // Display a message to select an answer or handle as needed.
                }

                startActivity(intent)

            }
        }
    }

    private fun displayQuestion(questionIndex : Int) {
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
