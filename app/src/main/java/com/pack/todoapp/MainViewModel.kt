package com.pack.todoapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _tasks = MutableLiveData<MutableList<Task>>(mutableListOf())
    val tasks: LiveData<MutableList<Task>> get() = _tasks

    fun addTask(taskName: String) {
        if (taskName.isNotEmpty()) {
            _tasks.value?.add(Task(taskName))
            _tasks.value = _tasks.value // Trigger update
        }
    }

    fun deleteTask(position: Int) {
        _tasks.value?.removeAt(position)
        _tasks.value = _tasks.value // Trigger update
    }
}