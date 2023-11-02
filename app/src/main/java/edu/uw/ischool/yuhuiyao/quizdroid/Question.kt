package edu.uw.ischool.yuhuiyao.quizdroid

class Question (
    val questionText: String,
    val answerChoices: List<String>,
    val correctAnswerIndex: Int

)

val mathQuestions = listOf(
    Question(
        questionText = "What is 2 + 2?",
        answerChoices = listOf("3", "4", "5", "6"),
        correctAnswerIndex = 1
    ),
    Question(
        questionText = "Solve for x: 3x - 7 = 14",
        answerChoices = listOf("3", "5", "7", "8"),
        correctAnswerIndex = 3
    )
)

val physicsQuestions = listOf(
    Question(
        questionText = "What is the SI unit of force?",
        answerChoices = listOf("Newton", "Watt", "Joule", "Ampere"),
        correctAnswerIndex = 0
    ),
    Question(
        questionText = "What is the acceleration due to gravity on Earth?",
        answerChoices = listOf("9.8 m/s²", "6.7 m/s²", "11.3 m/s²", "5.5 m/s²"),
        correctAnswerIndex = 0
    )
)

val marvelQuestions = listOf(
    Question(
        questionText = "Who is the alter ego of Spider-Man?",
        answerChoices = listOf("Bruce Wayne", "Clark Kent", "Peter Parker", "Tony Stark"),
        correctAnswerIndex = 2
    ),
    Question(
        questionText = "What is the name of Thor's enchanted hammer?",
        answerChoices = listOf("Mjolnir", "Excalibur", "Gungnir", "Stormbreaker"),
        correctAnswerIndex = 0
    )
)


