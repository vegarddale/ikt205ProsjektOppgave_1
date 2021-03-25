package com.example.ikt205prosjektoppgave_1

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ikt205prosjektoppgave_1.adapters.ListOverviewItemAdapter
import com.example.ikt205prosjektoppgave_1.data.TodoList
import com.example.ikt205prosjektoppgave_1.databinding.FragmentListOverviewBinding
import com.example.ikt205prosjektoppgave_1.utilities.TAG
import com.example.ikt205prosjektoppgave_1.viewmodels.TodoListViewModel
import kotlinx.android.synthetic.main.fragment_list_overview.view.*
import java.util.*


class ListOverviewFragment : Fragment() {

    private val todoListViewModel : TodoListViewModel by activityViewModels()
    private lateinit var adapter: ListOverviewItemAdapter

    private var _binding: FragmentListOverviewBinding? = null
    val binding get() = _binding!!

    val filter = IntentFilter().apply {
        addAction("DELETE_LIST")
    }
    val reciever = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val index = intent?.extras?.get("$TAG.LIST_INDEX") as Int
            todoListViewModel.removeListByIndex(index)
            adapter.updateListOverview(todoListViewModel.todoLists)
            goAsync()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = ListOverviewItemAdapter()

        todoListViewModel.progress.observe(requireActivity(),  {
            adapter.updateProgressBar(it)
        })

        requireActivity().registerReceiver(reciever, filter)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListOverviewBinding.inflate(layoutInflater)
        val view = binding.root

        binding.listRecyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.listRecyclerView.adapter = adapter

        binding.addTodoListBtn.setOnClickListener{
            // TODO: 3/21/2021 check empty input
            val name = view.addTodoListName.text.toString()
            val todoList = TodoList(name, mutableListOf())
            todoListViewModel.updateList(todoList)
            adapter.updateListOverview(todoListViewModel.todoLists)
        }

        return view
    }


}