package edu.uw.ischool.yuhuiyao.quizdroid

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.Button
import android.util.Log


class TopicOverviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_overview)

        val topicTitle = findViewById<TextView>(R.id.topicTitle)
        val topicDescription = findViewById<TextView>(R.id.topicDescription)
        val questionsNumber = findViewById<TextView>(R.id.questionsNumber)
        val beginButton = findViewById<Button>(R.id.beginButton)


        // Get the selected topic title from the "Topic List" page
        val selectedTopicTitle = intent.getStringExtra("selectedTopic")
        // Add this in TopicOverviewActivity.onCreate()
        Log.d("TopicOverviewActivity", "Selected Topic Title: $selectedTopicTitle")



        // Initialize a topic repository
        val topicRepository: TopicRepository = InMemoryTopicRepository(applicationContext)


        // Find the selected topic from the repository
        val selectedTopic = topicRepository.getTopics().find { it.title == selectedTopicTitle }

        Log.d("TopicOverviewActivity", "Selected Topic Data: $selectedTopic")


        // Check if the selected topic exists
        if (selectedTopic != null) {
            // Display the selected topic title
            topicTitle.text = selectedTopic.title

            topicDescription.text = selectedTopic.desc


            // Display the number of questions
            questionsNumber.text = "Total number of questions: ${selectedTopic.questions.size}"
        } else {
            // Handle the case where the topic title is not found
            // You can show an error message or navigate back to the topic list
        }

        var questionIndex = 0
        var correctAnswerCount = 0

        beginButton.setOnClickListener {
            // Now you can start the question activity with the selected topic title
            val intent = Intent(this, QuestionActivity::class.java)
            intent.putExtra("selectedTopic", selectedTopicTitle)
            intent.putExtra("questionIndex", questionIndex)
            intent.putExtra("correctAnswerCount", correctAnswerCount)
            startActivity(intent)
        }
    }
}
