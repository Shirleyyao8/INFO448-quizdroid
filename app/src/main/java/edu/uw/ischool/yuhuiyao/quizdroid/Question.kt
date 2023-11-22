package edu.uw.ischool.yuhuiyao.quizdroid


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.FileReader
import java.net.URL
import androidx.preference.PreferenceManager
import android.widget.Toast



data class Topic(
    val title: String,
    val desc: String,
    val questions: List<Question>
)


data class Question(
    val text: String,
    val answers: List<String>,
    val answer: Int
)


interface TopicRepository {
    fun getTopics(): List<Topic>
}

interface TopicsInitializationCallback {
    fun onTopicsInitialized(topics: List<Topic>)
    fun onInitializationError(error: Exception)
}

class InMemoryTopicRepository(private val applicationContext: Context) : TopicRepository {
    private var topics: List<Topic> = emptyList()
    private var isInitialized = false

    private val callback: TopicsInitializationCallback? = null

    init {
        initializeTopics()
    }

    private fun initializeTopics() {
        try {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
            val url =
                sharedPreferences.getString("pref_key_url", "http://tednewardsandbox.site44.com/questions.json")
                    ?: ""

            Toast.makeText(applicationContext, "Downloading from: $url", Toast.LENGTH_SHORT).show()


                try {
                    downloadJsonFile(
                        url,
                        applicationContext.getExternalFilesDir(null)?.absolutePath + "/question.json"
                    )

                    val json = readJsonFromFile("question.json")
                    topics = Gson().fromJson(json, object : TypeToken<List<Topic>>() {}.type)

                    Log.i("Question", "$topics")
                    isInitialized = true

                    callback?.onTopicsInitialized(topics)
                } catch (e: Exception) {
                    callback?.onInitializationError(e)
                }

        } catch (e: Exception) {
            Log.e("InMemoryTopicRepository", "Error: ${e.message}", e)
            callback?.onInitializationError(e)
        }
    }

    override fun getTopics(): List<Topic> {
        while (!isInitialized) {
            // Wait for initialization
        }
        return topics
    }

    private fun readJsonFromFile(fileName: String): String {
        val filePath = File(applicationContext.getExternalFilesDir(null), fileName).absolutePath
        return FileReader(filePath).use { fileReader ->
            fileReader.readText()
        }
    }
}





fun downloadJsonFile(urlString: String, destinationPath: String) {
    try {
        val url = URL(urlString)
        val connection = url.openConnection()
        connection.connect()


        // Input stream to read data from the URL
        val inputStream = connection.getInputStream()


        // Output stream to write data to the local file
        val file = File(destinationPath)
        val outputStream = FileOutputStream(file)


        // Buffer for reading data from the input stream
        val buffer = ByteArray(1024)
        var bytesRead: Int


        // Read from the input stream and write to the output stream until the end of the file
        while (inputStream.read(buffer).also { bytesRead = it } != -1) {
            outputStream.write(buffer, 0, bytesRead)
        }


        // Close streams
        inputStream.close()
        outputStream.close()


        Log.d("Download", "File downloaded successfully to ${file.absolutePath}")
    } catch (e: Exception) {
        Log.e("Download", "Error downloading file: ${e.message}", e)
    }
}



//package edu.uw.ischool.yuhuiyao.quizdroid
//
//import android.content.Context
//import android.os.Bundle
//import android.util.Log
//import androidx.appcompat.app.AppCompatActivity
//import com.google.gson.Gson
//import com.google.gson.reflect.TypeToken
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.GlobalScope
//import kotlinx.coroutines.launch
//import java.io.File
//import java.io.FileOutputStream
//import java.io.FileReader
//import java.net.URL
//import androidx.preference.PreferenceManager
//import android.widget.Toast
//
//data class Topic(
//    val title: String,
//    val desc: String,
//    val questions: List<Question>
//)
//
//data class Question(
//    val text: String,
//    val answers: List<String>,
//    val answer: Int
//)
//
//interface TopicRepository {
//    fun getTopics(): List<Topic>
//}
//
//class InMemoryTopicRepository(private val applicationContext: Context) : TopicRepository {
//    private var topics: List<Topic> = emptyList()
//    private var isInitialized = false
//
//
//
//        init {
//            // Use a coroutine to perform the file reading on a background thread
//
//                try {
//                    // Retrieve the URL from SharedPreferences with a default value
//                    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
//                    val url = sharedPreferences.getString("pref_key_url", "http://tednewardsandbox.site44.com/questions.json") ?: ""
//
//                    // Display a Toast with the current URL
//
//                    Toast.makeText(applicationContext, "Downloading from: $url", Toast.LENGTH_SHORT).show()
//
//
//                    // Download the JSON file using the retrieved URL
//                    downloadJsonFile(url, applicationContext.getExternalFilesDir(null)?.absolutePath + "/question.json")
//
//                    // Read and parse the JSON file
//                    val json = readJsonFromFile("question.json")
//                    topics = Gson().fromJson(json, object : TypeToken<List<Topic>>() {}.type)
//
//                    Log.i("Question", "$topics")
//
//                    isInitialized = true
//                } catch (e: Exception) {
//                    Log.e("InMemoryTopicRepository", "Error: ${e.message}", e)
//                }
//
//        }
//
//
//    override fun getTopics(): List<Topic> {
//        while (!isInitialized) {
//            // Wait for initialization
//        }
//        return topics
//    }
//
//    private fun readJsonFromFile(fileName: String): String {
//        val filePath = File(applicationContext.getExternalFilesDir(null), fileName).absolutePath
//        return FileReader(filePath).use { fileReader ->
//            fileReader.readText()
//        }
//    }
//}
//
//fun downloadJsonFile(urlString: String, destinationPath: String) {
//    try {
//        val url = URL(urlString)
//        val connection = url.openConnection()
//        connection.connect()
//
//        // Input stream to read data from the URL
//        val inputStream = connection.getInputStream()
//
//        // Output stream to write data to the local file
//        val file = File(destinationPath)
//        val outputStream = FileOutputStream(file)
//
//        // Buffer for reading data from the input stream
//        val buffer = ByteArray(1024)
//        var bytesRead: Int
//
//        // Read from the input stream and write to the output stream until the end of the file
//        while (inputStream.read(buffer).also { bytesRead = it } != -1) {
//            outputStream.write(buffer, 0, bytesRead)
//        }
//
//        // Close streams
//        inputStream.close()
//        outputStream.close()
//
//        Log.d("Download", "File downloaded successfully to ${file.absolutePath}")
//
//    } catch (e: Exception) {
//        Log.e("Download", "Error downloading file: ${e.message}", e)
//    }
//}


//package edu.uw.ischool.yuhuiyao.quizdroid
//
//import android.content.Context
//import android.os.Bundle
//import android.util.Log
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.google.gson.Gson
//import com.google.gson.reflect.TypeToken
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.GlobalScope
//import kotlinx.coroutines.launch
//import java.io.File
//import java.io.FileOutputStream
//import java.io.FileReader
//import java.net.URL
//import androidx.preference.PreferenceManager
//import androidx.preference.PreferenceFragmentCompat
//import android.app.AlarmManager
//import android.app.PendingIntent
//import android.content.Intent
//import android.os.SystemClock
//import java.util.concurrent.TimeUnit
//import android.os.Handler
//import android.os.Looper
//import kotlinx.coroutines.launch
//
//
//data class Topic(
//    val title: String,
//    val desc: String,
//    val questions: List<Question>
//)
//
//data class Question(
//    val text: String,
//    val answers: List<String>,
//    val answer: Int
//)
//
//interface TopicRepository {
//    fun getTopics(): List<Topic>
//}
//
//class InMemoryTopicRepository(private val applicationContext: Context) : TopicRepository {
//    private var topics: List<Topic> = emptyList()
//    private var isInitialized = false
//
//    private val handler = Handler(Looper.getMainLooper())
//    private var downloadRunnable: Runnable? = null
//
//
//        init {
//
//            try {
//
//
//                // WORK!!
//                // Retrieve the URL from SharedPreferences
//                val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
//                val url = sharedPreferences.getString("pref_key_url", "http://tednewardsandbox.site44.com/questions.json") ?: ""
//                val interval = sharedPreferences.getString("pref_key_interval", "60")
//
//
//                Toast.makeText(applicationContext, "Downloading from: $url", Toast.LENGTH_SHORT).show()
//
//                downloadJsonFile(url, applicationContext.getExternalFilesDir(null)?.absolutePath + "/question.json")
//
//                val json = readJsonFromFile("question.json")
//                topics = Gson().fromJson(json, object : TypeToken<List<Topic>>() {}.type)
//
//                Log.i("Question", "$topics")
//
//                isInitialized = true
//
//            } catch (e: Exception) {
//                Log.e("InMemoryTopicRepository", "Error: ${e.message}", e)
//            }
//
//    }
//
//
//    override fun getTopics(): List<Topic> {
//        while (!isInitialized) {
//            // Wait for initialization
//        }
//        return topics
//    }
//
//
//
//    private fun readJsonFromFile(fileName: String): String {
//        val filePath = File(applicationContext.getExternalFilesDir(null), fileName).absolutePath
//        return FileReader(filePath).use { fileReader ->
//            fileReader.readText()
//        }
//    }
//}
//
//fun downloadJsonFile(urlString: String, destinationPath: String) {
//    try {
//        val url = URL(urlString)
//        val connection = url.openConnection()
//        connection.connect()
//
//        // Input stream to read data from the URL
//        val inputStream = connection.getInputStream()
//
//        // Output stream to write data to the local file
//        val file = File(destinationPath)
//        val outputStream = FileOutputStream(file)
//
//        // Buffer for reading data from the input stream
//        val buffer = ByteArray(1024)
//        var bytesRead: Int
//
//        // Read from the input stream and write to the output stream until the end of the file
//        while (inputStream.read(buffer).also { bytesRead = it } != -1) {
//            outputStream.write(buffer, 0, bytesRead)
//        }
//
//        // Close streams
//        inputStream.close()
//        outputStream.close()
//
//        Log.d("Download", "File downloaded successfully to ${file.absolutePath}")
//    } catch (e: Exception) {
//        Log.e("Download", "Error downloading file: ${e.message}", e)
//    }
//}


//package edu.uw.ischool.yuhuiyao.quizdroid
//
//import android.content.Context
//import android.os.Bundle
//import android.util.Log
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.google.gson.Gson
//import com.google.gson.reflect.TypeToken
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.GlobalScope
//import kotlinx.coroutines.launch
//import java.io.File
//import java.io.FileOutputStream
//import java.io.FileReader
//import java.net.URL
//import androidx.preference.PreferenceManager
//import androidx.preference.PreferenceFragmentCompat
//import android.app.AlarmManager
//import android.app.PendingIntent
//import android.content.Intent
//import android.os.SystemClock
//import java.util.concurrent.TimeUnit
//import android.os.Handler
//import android.os.Looper
//import kotlinx.coroutines.launch
//import android.content.BroadcastReceiver
//
//
//data class Topic(
//    val title: String,
//    val desc: String,
//    val questions: List<Question>
//)
//
//data class Question(
//    val text: String,
//    val answers: List<String>,
//    val answer: Int
//)
//
//interface TopicRepository {
//    fun getTopics(): List<Topic>
//}
//
//class InMemoryTopicRepository(private val applicationContext: Context) : TopicRepository {
//    private var topics: List<Topic> = emptyList()
//    private var isInitialized = false
//
//    private val handler = Handler(Looper.getMainLooper())
//    private var downloadRunnable: Runnable? = null
//
//    // BroadcastReceiver to trigger file download
//    class DownloadReceiver : BroadcastReceiver() {
//        override fun onReceive(context: Context?, intent: Intent?) {
//            val url = intent?.getStringExtra("url") ?: ""
//            val destinationPath = context?.getExternalFilesDir(null)?.absolutePath + "/question.json"
//
//            Log.d("DownloadReceiver", "Download started at ${System.currentTimeMillis()} for URL: $url")
//
//            downloadJsonFile(url, destinationPath)
//
//            Log.d("DownloadReceiver", "Download started at ${System.currentTimeMillis()} for URL: $url")
//
//        }
//    }
//    init {
//        try {
//            // Retrieve the URL and interval from SharedPreferences
//            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
//            val url = sharedPreferences.getString("pref_key_url", "http://tednewardsandbox.site44.com/questions.json") ?: ""
//            val interval = sharedPreferences.getString("pref_key_interval", "1")
//
//            Toast.makeText(applicationContext, "Downloading from: $url", Toast.LENGTH_SHORT).show()
//
//            // Schedule periodic download using AlarmManager
//            val alarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//            val intent = Intent(applicationContext, DownloadReceiver::class.java)
//            intent.putExtra("url", url) // Pass the URL to the BroadcastReceiver
//
//            val pendingIntent = PendingIntent.getBroadcast(
//                applicationContext,
//                0,
//                intent,
//                PendingIntent.FLAG_UPDATE_CURRENT
//            )
//
//            val intervalMillis = TimeUnit.MINUTES.toMillis(interval?.toLongOrNull() ?: 1)
//
//            // Schedule the download to occur every N minutes
//            alarmManager.setRepeating(
//                AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                SystemClock.elapsedRealtime() + intervalMillis,
//                intervalMillis,
//                pendingIntent
//            )
//
//            // Download the file immediately
//            downloadJsonFile(url, applicationContext.getExternalFilesDir(null)?.absolutePath + "/question.json")
//
//            // Read the downloaded file
//            val json = readJsonFromFile("question.json")
//            topics = Gson().fromJson(json, object : TypeToken<List<Topic>>() {}.type)
//
//            Log.i("Question", "$topics")
//
//            isInitialized = true
//
//        } catch (e: Exception) {
//            Log.e("InMemoryTopicRepository", "Error: ${e.message}", e)
//        }
//    }
//
//
//
//    override fun getTopics(): List<Topic> {
//        while (!isInitialized) {
//            // Wait for initialization
//        }
//        return topics
//    }
//
//
//    private fun readJsonFromFile(fileName: String): String {
//        val filePath = File(applicationContext.getExternalFilesDir(null), fileName).absolutePath
//        return FileReader(filePath).use { fileReader ->
//            fileReader.readText()
//        }
//    }
//}
//
//fun downloadJsonFile(urlString: String, destinationPath: String) {
//    try {
//        val url = URL(urlString)
//        val connection = url.openConnection()
//        connection.connect()
//
//        // Input stream to read data from the URL
//        val inputStream = connection.getInputStream()
//
//        // Output stream to write data to the local file
//        val file = File(destinationPath)
//        val outputStream = FileOutputStream(file)
//
//        // Buffer for reading data from the input stream
//        val buffer = ByteArray(1024)
//        var bytesRead: Int
//
//        // Read from the input stream and write to the output stream until the end of the file
//        while (inputStream.read(buffer).also { bytesRead = it } != -1) {
//            outputStream.write(buffer, 0, bytesRead)
//        }
//
//        // Close streams
//        inputStream.close()
//        outputStream.close()
//
//        Log.d("Download", "File downloaded successfully to ${file.absolutePath}")
//    } catch (e: Exception) {
//        Log.e("Download", "Error downloading file: ${e.message}", e)
//    }
//}
//
//
