package com.example.fitt.repository

object ReminderRepository {

    fun getMockRepository(): List<ReminderData> {
        val swimming = ReminderData(
                id = 0,
                name = "Плавание",
                days = listOf<String>("Понедельник", "Среда", "Пятница")
        )

        val running = ReminderData(
                id = 0,
                name = "Бег",
                days = listOf<String>("Вторник", "Четверг", "Суббота")
        )

        val cycling = ReminderData(
                id = 0,
                name = "Езда на велосипеде",
                days = listOf<String>("Понедельник", "Вторник")
        )
        return listOf<ReminderData>(swimming, running, cycling)
    }
}