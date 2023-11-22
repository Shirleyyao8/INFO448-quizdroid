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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import android.provider.Settings




class MainActivity : AppCompatActivity() {
    private lateinit var topicRepository: TopicRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.i("MainActivity", "filesDir = $filesDir")

        topicRepository = InMemoryTopicRepository(applicationContext)

        val listView = findViewById<ListView>(R.id.topicListView)

//         Use a CoroutineScope to launch a coroutine for UI updates
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

        if (isAirplaneModeOn()) {
            Toast.makeText(
                this,
                "Airplane mode is enabled. Please disable it to access the internet.",
                Toast.LENGTH_SHORT
            ).show()

            val intent = Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS)
            startActivity(intent)
        } else if (!isNetworkAvailable()) {
            Toast.makeText(
                this,
                "No internet access. Please check your connection.",
                Toast.LENGTH_SHORT
            ).show()
        }


    }

    // CHECK IF PHONE IS OFFLINE
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
            return networkCapabilities != null &&
                    (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }

    // CHECK IF PHONE IS ON AIRPLANE MODE
    private fun isAirplaneModeOn(): Boolean {
        return Settings.Global.getInt(
            contentResolver,
            Settings.Global.AIRPLANE_MODE_ON,
            0
        ) != 0
    }

    private fun promptToDisableAirplaneMode() {
        Toast.makeText(this, "Airplane mode is enabled. Please disable it to access the internet.", Toast.LENGTH_SHORT).show()

        val intent = Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
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

                return true
            }
            // other menu items...
        }
        return super.onOptionsItemSelected(item)
    }


    private fun navigateToTopicOverview(topic: Topic) {
        val intent = Intent(this, TopicOverviewActivity::class.java)

        Log.d("MainActivity", "Navigating to TopicOverviewActivity with topic: ${topic.title}")

        intent.putExtra("selectedTopic", topic.title)
        startActivity(intent)
    }
}


