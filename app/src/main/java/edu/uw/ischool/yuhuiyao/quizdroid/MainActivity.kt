package edu.uw.ischool.yuhuiyao.quizdroid

import android.os.Bundle
import android.content.Intent
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.withContext
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var topicRepository: TopicRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.i("MainActivity", "filesDir = $filesDir")

        topicRepository = InMemoryTopicRepository(applicationContext)

        val listView = findViewById<ListView>(R.id.topicListView)

        // Use a CoroutineScope to launch a coroutine for UI updates
        GlobalScope.launch(Dispatchers.Main) {
            val topics = withContext(Dispatchers.IO) {
                topicRepository.getTopics()
            }

            // Create a list of strings that includes both title and shortDescription
            // ORIGINAL WITH SHORT DESC
            // val topicsWithDescriptions = topics.map { "${it.title} - ${it.shortDescription}" }.toTypedArray()
            // CHANGED
            val topicsWithDescriptions = topics.map { "${it.title}" }.toTypedArray()

            val adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, topicsWithDescriptions)
            listView.adapter = adapter
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedTopic = topicRepository.getTopics()[position] // Get the selected topic object
            navigateToTopicOverview(selectedTopic)
        }
    }

    private fun navigateToTopicOverview(topic: Topic) {
        val intent = Intent(this, TopicOverviewActivity::class.java)
        intent.putExtra("selectedTopic", topic.title)
        startActivity(intent)
    }
}


