package com.example.recylerviewapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recylerviewapp.databinding.RecyclerviewItemBinding


// passing the task list which we want to use to the adapter
class MainAdapter(val taskList: List<Task>) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    // inheriting from actual view holder
    inner class MainViewHolder(val itemBinding: RecyclerviewItemBinding) :
    // this requires a view as a parameter but because we are using viewBinding we need to access the root view of itemBinding
        RecyclerView.ViewHolder(itemBinding.root)
    // definition of MainViewHolder inner class
    {
        fun bindItem(task: Task) {
            itemBinding.titleTv.text = task.title
            itemBinding.timeTv.text = task.timeStamp
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            RecyclerviewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val task = taskList[position]
        holder.bindItem(task)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }
}