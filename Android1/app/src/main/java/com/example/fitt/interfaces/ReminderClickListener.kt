package com.example.fitt.interfaces

interface ReminderClickListener {

    //check item and delete from db
    fun checkRemoveItem(index: Long)

    //edit item on LONG click
    fun editItem(index: Long)
}