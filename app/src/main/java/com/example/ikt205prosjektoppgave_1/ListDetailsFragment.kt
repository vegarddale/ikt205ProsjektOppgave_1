package com.example.ikt205prosjektoppgave_1

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.*
import com.example.ikt205prosjektoppgave_1.adapters.ListDetailsAdapter
import com.example.ikt205prosjektoppgave_1.data.TodoList
import com.example.ikt205prosjektoppgave_1.data.TodoListItem
import com.example.ikt205prosjektoppgave_1.databinding.FragmentListDetailsBinding
import com.example.ikt205prosjektoppgave_1.utilities.TAG
import com.example.ikt205prosjektoppgave_1.viewmodels.TodoListViewModel
import com.example.ikt205prosjektoppgave_1.workers.FirebaseWorker
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.io.*


class ListDetailsFragment : Fragment() {

    private lateinit var adapter:ListDetailsAdapter

    private val args: ListDetailsFragmentArgs by navArgs()
    private val todoListViewModel : TodoListViewModel by activityViewModels()

    val filter = IntentFilter().apply {
        addAction("DELETE_ITEM")
        addAction("CHECKBOX_CLICKED")
    }

    val reciever = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when(intent?.action){
                "DELETE_ITEM" -> {
                    val itemIndex = intent.extras?.get("$TAG.ITEM_INDEX") as Int
                    val listIndex = args.ListDetailsPosition
                    todoListViewModel.removeItemByIndex(listIndex, itemIndex)
                    adapter.updateList(todoListViewModel.getListDetailsByIndex(listIndex))
                }
                "CHECKBOX_CLICKED" -> {
                    val progress = intent.extras?.get("$TAG.LIST_PROGRESS") as Float
                    val progressPercent = progress * 100
                    todoListViewModel.updateProgressBar(args.ListDetailsPosition, progressPercent.toInt())

                    val listItemIndex = intent.extras?.get("$TAG.LIST_ITEM_INDEX") as Int
                    todoListViewModel.updateTodoListCheckBox(args.ListDetailsPosition, listItemIndex)
                }
            }
        }
    }

    private var _binding: FragmentListDetailsBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = ListDetailsAdapter()
        requireActivity().registerReceiver(reciever, filter)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        binding.listDetailsItems.layoutManager = LinearLayoutManager(this.context)
        binding.listDetailsItems.adapter = adapter

        val list = todoListViewModel.getListDetailsByIndex(args.ListDetailsPosition)
        adapter.updateList(list)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addListItemBtn.setOnClickListener{
            val itemName = binding.itemName.text.toString()
            if(itemName != ""){
                val items = listOf(TodoListItem(itemName))
                todoListViewModel.updateTodoListItems(items, args.ListDetailsPosition)
                val list = todoListViewModel.getListDetailsByIndex(args.ListDetailsPosition)
                adapter.updateList(list)
            }
            else{
                Toast.makeText(requireActivity(), "Item name cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().unregisterReceiver(reciever)

    }
}