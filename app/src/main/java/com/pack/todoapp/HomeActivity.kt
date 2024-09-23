package com.pack.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.pack.todoapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_home)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        taskAdapter = TaskAdapter(viewModel.tasks.value ?: mutableListOf()) { position ->
            viewModel.deleteTask(position)
        }

        binding.recyclerView.adapter = taskAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.tasks.observe(this) { tasks ->
            taskAdapter.updateTasks(tasks)
        }

        binding.buttonAdd.setOnClickListener {
            viewModel.addTask(binding.editTextTask.text.toString())
            binding.editTextTask.text.clear()
        }
    }
}