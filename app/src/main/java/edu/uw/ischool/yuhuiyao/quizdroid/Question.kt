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
//import android.app.AlertDialog
//import android.content.DialogInterface
//
//
//
//data class Topic(
//    val title: String,
//    val desc: String,
//    val questions: List<Question>
//)
//
//
//data class Question(
//    val text: String,
//    val answers: List<String>,
//    val answer: Int
//)
//
//
//interface TopicRepository {
//    fun getTopics(): List<Topic>
//}
//
//interface TopicsInitializationCallback {
//    fun onTopicsInitialized(topics: List<Topic>)
//    fun onInitializationError(error: Exception)
//}
//
//class InMemoryTopicRepository(private val applicationContext: Context) : TopicRepository {
//    private var topics: List<Topic> = emptyList()
//    private var isInitialized = false
//
//    private val callback: TopicsInitializationCallback? = null
//
//    init {
//        initializeTopics()
//    }
//
//    private fun initializeTopics() {
//        try {
//            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
//            val url =
//                sharedPreferences.getString("pref_key_url", "http://tednewardsandbox.site44.com/questions.json")
//                    ?: ""
//
//            Toast.makeText(applicationContext, "Downloading from: $url", Toast.LENGTH_SHORT).show()
//
//
//                try {
//                    downloadJsonFile(
//                        url,
//                        applicationContext.getExternalFilesDir(null)?.absolutePath + "/question.json"
//                    )
//
//                    val json = readJsonFromFile("question.json")
//                    topics = Gson().fromJson(json, object : TypeToken<List<Topic>>() {}.type)
//
//                    Log.i("Question", "$topics")
//                    isInitialized = true
//
//                    callback?.onTopicsInitialized(topics)
//                } catch (e: Exception) {
//                    callback?.onInitializationError(e)
//                }
//
//        } catch (e: Exception) {
//            Log.e("InMemoryTopicRepository", "Error: ${e.message}", e)
//            callback?.onInitializationError(e)
//        }
//    }
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
//
//
//
//
//fun downloadJsonFile(urlString: String, destinationPath: String) {
//    try {
//        val url = URL(urlString)
//        val connection = url.openConnection()
//        connection.connect()
//
//
//        // Input stream to read data from the URL
//        val inputStream = connection.getInputStream()
//
//
//        // Output stream to write data to the local file
//        val file = File(destinationPath)
//        val outputStream = FileOutputStream(file)
//
//
//        // Buffer for reading data from the input stream
//        val buffer = ByteArray(1024)
//        var bytesRead: Int
//
//
//        // Read from the input stream and write to the output stream until the end of the file
//        while (inputStream.read(buffer).also { bytesRead = it } != -1) {
//            outputStream.write(buffer, 0, bytesRead)
//        }
//
//
//        // Close streams
//        inputStream.close()
//        outputStream.close()
//
//
//        Log.d("Download", "File downloaded successfully to ${file.absolutePath}")
//    } catch (e: Exception) {
//        Log.e("Download", "Error downloading file: ${e.message}", e)
//    }
//}

//package edu.uw.ischool.yuhuiyao.quizdroid
//
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
//import android.app.AlertDialog
//import android.content.DialogInterface
//
//
//
//data class Topic(
//    val title: String,
//    val desc: String,
//    val questions: List<Question>
//)
//
//
//data class Question(
//    val text: String,
//    val answers: List<String>,
//    val answer: Int
//)
//
//
//interface TopicRepository {
//    fun getTopics(): List<Topic>
//}
//
//interface TopicsInitializationCallback {
//    fun onTopicsInitialized(topics: List<Topic>)
//    fun onInitializationError(error: Exception)
//}
//
//class InMemoryTopicRepository(private val applicationContext: Context) : TopicRepository {
//    private var topics: List<Topic> = emptyList()
//    private var isInitialized = false
//
//    private val callback: TopicsInitializationCallback? = null
//
//    init {
//        initializeTopics()
//    }
//
//    private fun initializeTopics() {
//        try {
//            val sharedPreferences =
//                PreferenceManager.getDefaultSharedPreferences(applicationContext)
//            val url =
//                sharedPreferences.getString(
//                    "pref_key_url",
//                    "http://tednewardsandbox.site44.com/questions.json"
//                )
//                    ?: ""
//
//            Toast.makeText(applicationContext, "Downloading from: $url", Toast.LENGTH_SHORT).show()
//
//            GlobalScope.launch(Dispatchers.Main) {
//                try {
//                    downloadJsonFile(
//                        url,
//                        applicationContext.getExternalFilesDir(null)?.absolutePath + "/question.json",
//                        {
//                            Toast.makeText(
//                                applicationContext,
//                                "Downloading from: $url",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        },
//                        { success ->
//                            if (success) {
//                                Toast.makeText(
//                                    applicationContext,
//                                    "File downloaded successfully",
//                                    Toast.LENGTH_SHORT
//                                ).show()
//                            } else {
//                                Toast.makeText(
//                                    applicationContext,
//                                    "Failed to download file. Please check your internet connection.",
//                                    Toast.LENGTH_SHORT
//                                ).show()
//                            }
//                        }
//                    )
//
//                    val json = readJsonFromFile("question.json")
//                    topics = Gson().fromJson(json, object : TypeToken<List<Topic>>() {}.type)
//
//                    Log.i("Question", "$topics")
//                    isInitialized = true
//
//                    callback?.onTopicsInitialized(topics)
//                } catch (e: Exception) {
//                    callback?.onInitializationError(e)
//                }
//            }
//
//            } catch (e: Exception) {
//                Log.e("InMemoryTopicRepository", "Error: ${e.message}", e)
//                callback?.onInitializationError(e)
//            }
//
//    }
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
//    private fun showRetryOrQuitDialog() {
//        val builder = AlertDialog.Builder(applicationContext)
//        builder.setTitle("Download Failed")
//        builder.setMessage("Failed to download questions. Do you want to retry or quit the application?")
//        builder.setPositiveButton("Retry") { _, _ ->
//            // Retry download
//            // You can call downloadJsonFile again with the appropriate parameters
//        }
//        builder.setNegativeButton("Quit") { _, _ ->
//            // Quit the application
//            // You can use finish() to close the current activity or System.exit(0) to exit the application
//
//        }
//        builder.setOnCancelListener {
//            // Handle dialog cancel (e.g., treat as quitting the application)
//
//        }
//        builder.create().show()
//    }
//}
//
//
//fun downloadJsonFile(
//    urlString: String,
//    destinationPath: String,
//    initialToastCallback: () -> Unit,
//    resultCallback: (Boolean) -> Unit
//) {
//    GlobalScope.launch(Dispatchers.Main) {
//        initialToastCallback() // Show initial toast on the main thread
//
//        try {
//            val url = URL(urlString)
//            val connection = url.openConnection()
//            connection.connect()
//
//            // Input stream to read data from the URL
//            val inputStream = connection.getInputStream()
//
//            // Output stream to write data to the local file
//            val file = File(destinationPath)
//            val outputStream = FileOutputStream(file)
//
//            // Buffer for reading data from the input stream
//            val buffer = ByteArray(1024)
//            var bytesRead: Int
//
//            // Read from the input stream and write to the output stream until the end of the file
//            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
//                outputStream.write(buffer, 0, bytesRead)
//            }
//
//            // Close streams
//            inputStream.close()
//            outputStream.close()
//
//            Log.d("Download", "File downloaded successfully to ${file.absolutePath}")
//            resultCallback(true) // Notify success
//        } catch (e: Exception) {
//            Log.e("Download", "Error downloading file: ${e.message}", e)
//            resultCallback(false) // Notify failure
//
//            // Show dialog to retry or quit
//
//        }
//    }
//}
//
