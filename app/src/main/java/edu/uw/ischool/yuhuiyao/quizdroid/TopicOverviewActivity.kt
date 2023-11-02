package edu.uw.ischool.yuhuiyao.quizdroid

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.Button

class TopicOverviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_overview)

        val topicTitle = findViewById<TextView>(R.id.topicTitle)
        val topicDescription = findViewById<TextView>(R.id.topicDescription)
        val questionsNumber = findViewById<TextView>(R.id.questionsNumber)
        val beginButton = findViewById<Button>(R.id.beginButton)

        // Get the selected topic from the "Topic List" page
        val selectedTopic = intent.getStringExtra("selectedTopic")

        // Display the topic title and description based on the selected topic
        when (selectedTopic) {
            "Math" -> {
                topicTitle.text = "Math Overview"
                topicDescription.text = "This topic covers various mathematical concepts and problems."
                questionsNumber.text = "Total number of questions: 2"

                beginButton.setOnClickListener {
                    val intent = Intent(this, firstMathQuestion::class.java)
                    startActivity(intent)
                }
            }
            "Physics" -> {
                topicTitle.text = "Physics Overview"
                topicDescription.text = "Learn about the laws of physics and the behavior of matter and energy."
                questionsNumber.text = "Total number of questions: 2"

                beginButton.setOnClickListener {
                    val intent = Intent(this, firstPhysicsQuestion::class.java)
                    startActivity(intent)
                }
            }
            "Marvel Super Heroes" -> {
                topicTitle.text = "Marvel Super Heroes Overview"
                topicDescription.text = "Explore the world of Marvel superheroes and their adventures."
                questionsNumber.text = "Total number of questions: 2"

                beginButton.setOnClickListener {
                    val intent = Intent(this, firstMarvelQuestion::class.java)
                    startActivity(intent)
                }
            }
            else -> {
                // Handle unknown topics or provide a default description
            }
        }


    }
}
