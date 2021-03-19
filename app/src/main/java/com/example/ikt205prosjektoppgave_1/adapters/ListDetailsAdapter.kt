package com.example.ikt205prosjektoppgave_1.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ikt205prosjektoppgave_1.data.TodoListItem
import com.example.ikt205prosjektoppgave_1.databinding.ListDetailsItemBinding

class ListDetailsAdapter(private val itemTodos: MutableList<TodoListItem>) : RecyclerView.Adapter<ListDetailsAdapter.ViewHolder>(){

    class ViewHolder(val binding:ListDetailsItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(todoListItem:TodoListItem){
            binding.listItem.text = todoListItem.Item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ListDetailsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemTodos[position])
    }

    override fun getItemCount(): Int = itemTodos.size

    fun updateList(itemTodo:TodoListItem){
        itemTodos.add(itemTodo)
        notifyDataSetChanged()
    }
}