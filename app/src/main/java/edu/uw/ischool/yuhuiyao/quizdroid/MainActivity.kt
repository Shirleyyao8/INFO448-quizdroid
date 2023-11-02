package edu.uw.ischool.yuhuiyao.quizdroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView


class MainActivity : AppCompatActivity() {
    private val topics = arrayOf("Math", "Physics", "Marvel Super Heroes")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.topicListView)

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, topics)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            // Handle item click here
            val selectedTopic = topics[position]
            navigateToTopicOverview(selectedTopic)
        }
    }

    private fun navigateToTopicOverview(topicName: String) {
        val intent = Intent(this, TopicOverviewActivity::class.java)
        intent.putExtra("selectedTopic", topicName)
        startActivity(intent)
    }

}