package com.example.gaanaworld.helper

sealed class TaskProgressTracker(val message:String?) {
    class Done(message: String) : TaskProgressTracker(message)
    class Loading : TaskProgressTracker(message = null)
    class Error(message: String) : TaskProgressTracker(message)
}