package edu.uw.ischool.yuhuiyao.quizdroid
import android.os.Bundle
import android.content.Intent
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var topicRepository: TopicRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        topicRepository = InMemoryTopicRepository()

        val listView = findViewById<ListView>(R.id.topicListView)
        val topics = topicRepository.getTopics()

        // Create a list of strings that includes both title and shortDescription
        val topicsWithDescriptions = topics.map { "${it.title} - ${it.shortDescription}" }.toTypedArray()

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, topicsWithDescriptions)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedTopic = topics[position] // Get the selected topic object
            navigateToTopicOverview(selectedTopic)
        }
    }

    private fun navigateToTopicOverview(topic: Topic) {
        val intent = Intent(this, TopicOverviewActivity::class.java)
        intent.putExtra("selectedTopic", topic.title)
        startActivity(intent)
    }
}
