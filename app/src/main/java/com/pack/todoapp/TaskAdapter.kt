package com.pack.todoapp


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pack.todoapp.databinding.ItemTaskBinding


class TaskAdapter(private var tasks: MutableList<Task>, private val onDelete: (Int) -> Unit) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.binding.task = tasks[position]
        holder.binding.onDelete = View.OnClickListener { onDelete(position) }
        holder.binding.executePendingBindings()
    }

    override fun getItemCount() = tasks.size

    fun updateTasks(newTasks: MutableList<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }
}