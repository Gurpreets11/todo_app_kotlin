package com.pack.todoapp


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pack.todoapp.databinding.ItemTaskBinding


class TaskAdapter(private var tasks: List<Task>, private val onDelete: (Task, Boolean) -> Unit) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.binding.task = tasks[position]
        holder.binding.onDelete = View.OnClickListener { onDelete(tasks[position], false) }
        holder.binding.onEdit = View.OnClickListener { onDelete(tasks[position], true) } // Add edit functionality
        holder.binding.executePendingBindings()
    }

    override fun getItemCount() = tasks.size

    fun updateTasks(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }
}