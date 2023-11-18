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
import com.google.android.material.appbar.MaterialToolbar
import android.view.Menu
import android.view.MenuItem
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import android.content.SharedPreferences
import android.view.MenuInflater
import android.app.DownloadManager
import android.net.Uri



class MainActivity : AppCompatActivity() {
    private lateinit var topicRepository: TopicRepository
    private lateinit var toolbar: MaterialToolbar

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

            val topicsWithDescriptions = topics.map { "${it.title}" }.toTypedArray()

            val adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, topicsWithDescriptions)
            listView.adapter = adapter
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedTopic = topicRepository.getTopics()[position] // Get the selected topic object
            navigateToTopicOverview(selectedTopic)
        }


        setSupportActionBar(findViewById(R.id.toolbar))

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        return super.onCreateOptionsMenu(menu)
        val inflator: MenuInflater = menuInflater
        inflator.inflate(R.menu.activity_main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.i("MainActivity", "clicked")

        when (item.itemId) {
            R.id.action_preferences -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                Log.i("MainActivity", "clicked")

//                val sharedPreferences: SharedPreferences =
//                    PreferenceManager.getDefaultSharedPreferences(this)
//                val url = sharedPreferences.getString("pref_key_url", "http://tednewardsandbox.site44.com/questions.json")
//                val interval =
//                    sharedPreferences.getString("pref_key_interval", "10")?.toInt() ?: 10

                return true
            }
            // other menu items...
        }
        return super.onOptionsItemSelected(item)
    }


    private fun navigateToTopicOverview(topic: Topic) {
        val intent = Intent(this, TopicOverviewActivity::class.java)
        intent.putExtra("selectedTopic", topic.title)
        startActivity(intent)
    }
}


