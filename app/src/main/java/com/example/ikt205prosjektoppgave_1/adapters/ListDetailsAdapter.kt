package com.example.ikt205prosjektoppgave_1.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ikt205prosjektoppgave_1.data.TodoListItem
import com.example.ikt205prosjektoppgave_1.databinding.ListDetailsItemBinding
import com.example.ikt205prosjektoppgave_1.utilities.TAG


class ListDetailsAdapter : RecyclerView.Adapter<ListDetailsAdapter.ViewHolder>() {
    private var todoListItems = emptyList<TodoListItem>()

    class ViewHolder(private val binding: ListDetailsItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(todoListItem: TodoListItem, position: Int, itemCount: Int) {

            binding.todoListItemCheckBox.text = todoListItem.name
            binding.todoListItemCheckBox.isChecked = todoListItem.isCheckedOff

            binding.todoListItemCheckBox.setOnClickListener {
                var progress: Float = 1 / itemCount.toFloat()


                if(todoListItem.isCheckedOff){
                    progress = -progress
                }

                val intent = Intent().also {
                    it.action = "UPDATE_PROGRESS"
                    it.putExtra("$TAG.LIST_PROGRESS", progress)
                    it.putExtra("$TAG.LIST_ITEM_INDEX", position)

                }
                binding.root.context.sendBroadcast(intent)
            }

            binding.deleteItemBtn.setOnClickListener {
                val intent = Intent().also {
                    it.action = "DELETE_ITEM"
                    it.putExtra("$TAG.ITEM_INDEX", position)
                }
                binding.root.context.sendBroadcast(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ListDetailsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(todoListItems[position], position, itemCount)
    }

    override fun getItemCount(): Int = todoListItems.size

    fun updateList(list: List<TodoListItem>) {
        todoListItems = list
        notifyDataSetChanged()
    }


}