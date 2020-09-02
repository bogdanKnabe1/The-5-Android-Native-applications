package com.example.fitt.repository

object ReminderRepository {

    fun getMockRepository(): List<ReminderData> {
        val swimming = ReminderData(
                id = 0,
                name = "Плавание",
                days = listOf("Понедельник", "Среда", "Пятница")
        )

        val running = ReminderData(
                id = 0,
                name = "Бег",
                days = listOf("Вторник", "Четверг", "Суббота")
        )

        val cycling = ReminderData(
                id = 0,
                name = "Езда на велосипеде",
                days = listOf("Понедельник", "Вторник")
        )
        return listOf(swimming, running, cycling)
    }
}