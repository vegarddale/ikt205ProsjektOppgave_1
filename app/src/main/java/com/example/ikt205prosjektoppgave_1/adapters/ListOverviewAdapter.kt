package com.example.ikt205prosjektoppgave_1.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ikt205prosjektoppgave_1.data.TodoList
import com.example.ikt205prosjektoppgave_1.databinding.ListOverviewItemBinding

class ListOverviewAdapter(val listOverview:MutableList<TodoList>) : RecyclerView.Adapter<ListOverviewAdapter.ViewHolder>() {

    class ViewHolder(val binding:ListOverviewItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(list:TodoList){
            binding.listName.text = list.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ListOverviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listOverview[position])
    }

    override fun getItemCount(): Int = listOverview.size

    private fun updateListOverview(list:TodoList){
        listOverview.add(list)
        notifyDataSetChanged()
    }
}