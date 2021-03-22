package com.example.ikt205prosjektoppgave_1.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.ikt205prosjektoppgave_1.data.TodoListItem
import com.example.ikt205prosjektoppgave_1.databinding.ListDetailsItemBinding
import com.example.ikt205prosjektoppgave_1.viewmodels.TodoListViewModel

class ListDetailsAdapter : RecyclerView.Adapter<ListDetailsAdapter.ViewHolder>(){
    private val todoListItems = mutableListOf<TodoListItem>()

    class ViewHolder(private val binding:ListDetailsItemBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(todoListItem:TodoListItem, position: Int){
            binding.listItem.text = todoListItem.name
            binding.deleteItemBtn.setOnClickListener{
                println("hello from viewholder class")
                val intent = Intent().also {
                    it.action = "DELETE_ITEM"
                    it.putExtra("com.example.ikt205prosjektoppgave_1.ITEM_INDEX", position)
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
        holder.bind(todoListItems[position], position)
    }

    override fun getItemCount(): Int = todoListItems.size

    fun updateList(list: MutableList<TodoListItem>){
        todoListItems.clear()
        todoListItems.addAll(list)
        notifyDataSetChanged()
    }


}