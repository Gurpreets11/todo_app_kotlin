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
    private var currentEditingTask: Task? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_home)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this



        taskAdapter = TaskAdapter(viewModel.tasks.value ?: emptyList()) { task, isEdit ->
            if (isEdit) {
                // Handle editing
                binding.editTextTask.setText(task.name)
                // Populate other fields for editing
                // Assuming you have other EditTexts for detail, startDate, endDate
                // binding.editTextDetail.setText(task.detail)
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



            val taskName = binding.editTextTask.text.toString()
            // Get other details from the UI
            val taskDetail = "Detail here" // Update with actual data
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
            binding.editTextTask.text.clear()
            // Clear other input fields if necessary

        }
    }
}