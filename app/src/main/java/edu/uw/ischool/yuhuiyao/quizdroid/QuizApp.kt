package edu.uw.ischool.yuhuiyao.quizdroid
import android.app.Application
import android.util.Log

class QuizApp : Application() {

    companion object {
        private lateinit var instance: QuizApp
        private lateinit var topicRepository: TopicRepository

        fun getInstance(): QuizApp {
            return instance
        }

        fun getTopicRepository(): TopicRepository {
            return topicRepository
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("QuizApp", "QuizApp is being loaded and run")
        instance = this
        topicRepository = InMemoryTopicRepository(applicationContext) // You can use any implementation you like here
    }
}
