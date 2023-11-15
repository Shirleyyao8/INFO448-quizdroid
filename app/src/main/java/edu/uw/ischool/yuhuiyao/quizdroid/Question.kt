package edu.uw.ischool.yuhuiyao.quizdroid
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.withContext
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import java.io.FileReader
import java.io.File


// Domain Objects

// before
//data class Topic(
//    val title: String,
//    val shortDescription: String,
//    val longDescription: String,
//    val questions: List<Question>
//)

data class Topic(
    val title: String,
    val desc: String,
    val questions: List<Question>
)

// before
//data class Question(
//    val questionText: String,
//    val answerChoices: List<String>,
//    val correctAnswerIndex: Int
//)

data class Question(
    val text: String,
    val answers: List<String>,
    val answer: Int
)


data class Quiz(
    val questionText: String,
    val answers: List<String>,
    val correctAnswerIndex: Int
)

interface TopicRepository {
    fun getTopics(): List<Topic>
}


class InMemoryTopicRepository(private val applicationContext: Context) : TopicRepository {
    private var topics: List<Topic> = emptyList()
    private var isInitialized = false

//    init {
//        // Use a coroutine to perform the file reading on a background thread
//        GlobalScope.launch(Dispatchers.IO) {
//            val json = readJsonFromFile("questions.json")
//            topics = Gson().fromJson(json, object : TypeToken<List<Topic>>() {}.type)
//            isInitialized = true
//        }
//    }

    init {
        // Use a coroutine to perform the file reading on a background thread
            val json = readJsonFromFile("questions.json")
            topics = Gson().fromJson(json, object : TypeToken<List<Topic>>() {}.type)
            isInitialized = true
    }



    override fun getTopics(): List<Topic> {
        while (!isInitialized) {
            // Wait for initialization
        }
        return topics
    }

    private fun readJsonFromFile(fileName: String): String {
        // Specify the path to the file on the device's storage
        val filePath = applicationContext.filesDir.absolutePath + File.separator + fileName

        // Use a FileReader to read the file
        return FileReader(filePath).use { fileReader ->
            fileReader.readText()
        }
    }
}


//interface TopicRepository {
//    fun getTopics(): List<Topic>
//}
//
//class InMemoryTopicRepository(private val applicationContext: Context) : TopicRepository {
//    private var topics: List<Topic> = emptyList()
//    private var isInitialized = false
//
//    init {
//        // Use a coroutine to perform the network request on a background thread
//        GlobalScope.launch(Dispatchers.IO) {
//            val json = downloadJsonFromUrl("https://tednewardsandbox.site44.com/questions.json")
//            topics = Gson().fromJson(json, object : TypeToken<List<Topic>>() {}.type)
//            isInitialized = true
//        }
//    }
//
//    override fun getTopics(): List<Topic> {
//        while (!isInitialized) {
//
//        }
//        return topics
//    }
//
//    private fun downloadJsonFromUrl(urlString: String): String {
//        val url = URL(urlString)
//        val connection = url.openConnection() as HttpURLConnection
//        connection.requestMethod = "GET"
//
//        val inputStream = connection.inputStream
//        val reader = BufferedReader(InputStreamReader(inputStream))
//        val stringBuilder = StringBuilder()
//        var line: String?
//
//        while (reader.readLine().also { line = it } != null) {
//            stringBuilder.append(line)
//        }
//
//        reader.close()
//        connection.disconnect()
//
//        return stringBuilder.toString()
//    }
//}