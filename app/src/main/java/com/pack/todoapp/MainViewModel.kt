package com.pack.todoapp

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val taskDao = AppDatabase.getDatabase(application).taskDao()
    val tasks: LiveData<List<Task>> = taskDao.getAllTasks()

    fun addTask(name: String, detail: String, startDate: Long, endDate: Long) {
        val newTask = Task(name = name, detail = detail, startDate = startDate, endDate = endDate)
        viewModelScope.launch {
            taskDao.insert(newTask)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            taskDao.update(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskDao.delete(task)
        }
    }
}
