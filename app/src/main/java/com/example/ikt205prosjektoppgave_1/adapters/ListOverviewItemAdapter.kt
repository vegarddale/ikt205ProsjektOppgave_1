package com.example.ikt205prosjektoppgave_1.adapters

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.ikt205prosjektoppgave_1.ListOverviewFragmentDirections
import com.example.ikt205prosjektoppgave_1.data.TodoList
import com.example.ikt205prosjektoppgave_1.databinding.ListOverviewItemBinding
import com.example.ikt205prosjektoppgave_1.utilities.TAG

class ListOverviewItemAdapter : RecyclerView.Adapter<ListOverviewItemAdapter.ViewHolder>() {
    private var todoLists = emptyList<TodoList>()


    class ViewHolder(val binding:ListOverviewItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(list:TodoList, position: Int){
            binding.todoListName.text = list.name
            binding.todoListOverview.setOnClickListener{
                navigateToListDetails(binding.root, position)
            }
            binding.deleteListBtn.setOnClickListener{
                val intent = Intent().also {
                    it.action = "DELETE_LIST"
                    it.putExtra("$TAG.LIST_INDEX", position)
                }
                binding.root.context.sendBroadcast(intent)
            }
            binding.todoListProgressBar.progress = list.progress
        }


         fun navigateToListDetails(view: FrameLayout, position: Int){
            val action = ListOverviewFragmentDirections.actionListOverviewFragmentToListDetailsFragment(position)
            view.findNavController().navigate(action)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ListOverviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(todoLists[position], position)
    }

    override fun getItemCount(): Int = todoLists.size


    fun updateListOverview(list:List<TodoList>){
        todoLists = list
        notifyDataSetChanged()
    }
}