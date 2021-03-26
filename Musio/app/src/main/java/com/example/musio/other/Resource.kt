package com.example.musio.other

// Generic class for handle states
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    // emit Resource's - in VIEW we can check status of resource
    companion object {
        fun <T> success(data: T?) = Resource(Status.SUCCESS, data, null)

        fun <T> error(message: String?, data: T?) = Resource(Status.ERROR, data, message)

        fun <T> loading(data: T?) = Resource(Status.LOADING, data, null)
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}