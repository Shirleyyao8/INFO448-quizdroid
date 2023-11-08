package edu.uw.ischool.yuhuiyao.quizdroid

// Domain Objects


data class Topic(
    val title: String,
    val shortDescription: String,
    val longDescription: String,
    val questions: List<Question>
)


data class Question(
    val questionText: String,
    val answerChoices: List<String>,
    val correctAnswerIndex: Int
)


data class Quiz(
    val questionText: String,
    val answers: List<String>,
    val correctAnswerIndex: Int
)


// TopicRepository Interface


interface TopicRepository {
    fun getTopics(): List<Topic>
}


// In-Memory Implementation of TopicRepository


class InMemoryTopicRepository : TopicRepository {
    private val topics: List<Topic> = listOf(
        Topic(
            title = "Math",
            shortDescription = "The world of numbers and equations",
            longDescription = "Mathematics is the science of numbers, quantities, and shapes. It plays a crucial role in various fields.",
            questions = listOf(
                Question(
                    questionText = "What is 2 + 2?",
                    answerChoices = listOf("3", "4", "5", "6"),
                    correctAnswerIndex = 1
                ),
                Question(
                    questionText = "What is the square root of 16?",
                    answerChoices = listOf("2", "4", "8", "16"),
                    correctAnswerIndex = 1
                )
            )
        ),
        Topic(
            title = "Physics",
            shortDescription = "The study of matter, energy, and the universe",
            longDescription = "Physics is the fundamental science that seeks to understand the laws governing the behavior of the universe.",
            questions = listOf(
                Question(
                    questionText = "What is the SI unit for measuring time?",
                    answerChoices = listOf("Hour", "Day", "Second", "Minute"),
                    correctAnswerIndex = 2
                ),
                Question(
                    questionText = "What force keeps the planets in orbit around the sun?",
                    answerChoices = listOf(
                        "Electromagnetic force",
                        "Gravity",
                        "Nuclear force",
                        "Friction"
                    ),
                    correctAnswerIndex = 1
                )
            )
        ),
        Topic(
            title = "Marvel Heroes",
            shortDescription = "Explore the Marvel superhero universe",
            longDescription = "Marvel heroes are iconic characters with superhuman abilities, known for their epic adventures.",
            questions = listOf(
                Question(
                    questionText = "Who is the strongest Avenger?",
                    answerChoices = listOf("Iron Man", "Thor", "Black Widow", "Hulk"),
                    correctAnswerIndex = 3
                ),
                Question(
                    questionText = "What is the real name of Captain America?",
                    answerChoices = listOf(
                        "Tony Stark",
                        "Bruce Banner",
                        "Steve Rogers",
                        "Peter Parker"
                    ),
                    correctAnswerIndex = 2
                )
            )
        )
    )


    override fun getTopics(): List<Topic> {
        return topics
    }
}