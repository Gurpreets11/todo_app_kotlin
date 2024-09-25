package com.pack.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.pack.todoapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var taskAdapter: TaskAdapter
    private var currentEditingTask: Task? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        taskAdapter = TaskAdapter(viewModel.tasks.value ?: emptyList()) { task, isEdit ->
            if (isEdit) {
                // Handle editing
                binding.taskNameET.setText(task.name)
                binding.taskDetailET.setText(task.detail)
                // binding.editTextStartDate.setText(formatDate(task.startDate))
                // binding.editTextEndDate.setText(formatDate(task.endDate))
                currentEditingTask = task
            } else {
                viewModel.deleteTask(task)
            }
        }

        binding.recyclerView.adapter = taskAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.tasks.observe(this) { tasks ->
            taskAdapter.updateTasks(tasks)
        }

        binding.buttonAdd.setOnClickListener {
            // Get other details from the UI (you need to add more EditTexts for detail, dates, etc.)
            /*viewModel.addTask(binding.editTextTask.text.toString(), "Detail here", System.currentTimeMillis(), System.currentTimeMillis())
            binding.editTextTask.text.clear()*/


            if (binding.taskNameET.text.toString() == null || binding.taskNameET.text.toString() == "") {
                Toast.makeText(this, "Enter task", Toast.LENGTH_SHORT).show()
            } else if (binding.taskDetailET.text.toString() == null || binding.taskDetailET.text.toString() == "") {
                Toast.makeText(this, "Enter task detail", Toast.LENGTH_SHORT).show()
            } else {

                val taskName = binding.taskNameET.text.toString()
                // Get other details from the UI
                val taskDetail = binding.taskDetailET.text.toString() // Update with actual data
                val taskStartDate = System.currentTimeMillis() // Update with actual start date
                val taskEndDate = System.currentTimeMillis() // Update with actual end date

                if (currentEditingTask == null) {
                    // Add new task
                    viewModel.addTask(taskName, taskDetail, taskStartDate, taskEndDate)
                } else {
                    // Update existing task
                    currentEditingTask?.let {
                        it.name = taskName
                        it.detail = taskDetail
                        it.startDate = taskStartDate
                        it.endDate = taskEndDate
                        viewModel.updateTask(it)
                    }
                    currentEditingTask = null // Reset currentEditingTask after update
                }

                // Clear input fields
                binding.taskNameET.text.clear()
                binding.taskDetailET.text.clear()
                // Clear other input fields if necessary

            }


        }
    }
}