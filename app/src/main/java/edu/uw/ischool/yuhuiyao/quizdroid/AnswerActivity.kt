package edu.uw.ischool.yuhuiyao.quizdroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class AnswerActivity : AppCompatActivity() {

    private var questions: List<Question> = emptyList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)

        val userAnswerTextView = findViewById<TextView>(R.id.userAnswerTextView)
        val correctAnswerTextView = findViewById<TextView>(R.id.correctAnswerTextView)
        val resultTextView = findViewById<TextView>(R.id.resultTextView)
        val nextButton = findViewById<Button>(R.id.nextButton)

        // Retrieve the user's answer and correct answer from the Intent.
        val userAnswer = intent.getStringExtra("userAnswer")
        var questionIndex = intent.getIntExtra("questionIndex", 0)
        var correctAnswersCount = intent.getIntExtra("correctAnswerCount", 0)
        val selectedTopic = intent.getStringExtra("selectedTopic")


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

        val correctIndex = questions[questionIndex].correctAnswerIndex
        val correctAnswer = questions[questionIndex].answerChoices[correctIndex]

        // Set the text for the user's answer and correct answer TextViews.
        userAnswerTextView.text = "Your Answer: $userAnswer"
        correctAnswerTextView.text = "Correct Answer: $correctAnswer"

        // Compare the user's answer with the correct answer.
        val isCorrect = userAnswer == correctAnswer


        val totalQuestionsCount = questions.size

        if (isCorrect) {
            correctAnswersCount++
        }


        resultTextView.text = "You have $correctAnswersCount out of $totalQuestionsCount correct."

        if (questionIndex<questions.size - 1) {
            nextButton.text="Next"
            nextButton.setOnClickListener {
                questionIndex++

                val intent = Intent(this, QuestionActivity::class.java)
                intent.putExtra("selectedTopic", selectedTopic)
                intent.putExtra("questionIndex", questionIndex)
                intent.putExtra("correctAnswerCount", correctAnswersCount)
                startActivity(intent)
            }

        } else {
            nextButton.text="Finish"
            nextButton.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

    }
}
