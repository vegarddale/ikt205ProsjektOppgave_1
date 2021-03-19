package com.example.ikt205prosjektoppgave_1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ikt205prosjektoppgave_1.adapters.ListDetailsAdapter
import com.example.ikt205prosjektoppgave_1.data.TodoListItem
import com.example.ikt205prosjektoppgave_1.databinding.FragmentListDetailsBinding
import com.example.ikt205prosjektoppgave_1.viewmodels.ListDetailsViewModel
import com.example.ikt205prosjektoppgave_1.viewmodels.ListViewModel
import kotlinx.android.synthetic.main.fragment_list_details.view.*


class ListDetailsFragment : Fragment() {

    private lateinit var observer:Observer<TodoListItem>
    private lateinit var adapter:ListDetailsAdapter

    private val viewModel: ListDetailsViewModel by viewModels()
    private val listViewModel : ListViewModel by activityViewModels()

    private val detailListItems = mutableListOf<TodoListItem>(
            TodoListItem("1"),
            TodoListItem("2"),
            TodoListItem("3"),
            TodoListItem("4"),
            TodoListItem("5"),
            TodoListItem("6"),
            TodoListItem("7"),
            TodoListItem("8"),
            TodoListItem("9"),
            TodoListItem("10"),
            TodoListItem("11")
    )



    private var _binding: FragmentListDetailsBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = ListDetailsAdapter(detailListItems)
        observer = Observer<TodoListItem> {

            adapter.updateList(TodoListItem("test"))
        }
        viewModel.items.observe(this, observer)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        binding.listDetailsItems.layoutManager = LinearLayoutManager(this.context)
        binding.listDetailsItems.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.addListItemBtn.setOnClickListener{
            // TODO: check for non empty input
            val name:String = view.itemName.text.toString()
            viewModel.updateDetails(TodoListItem(name))
        }
    }
}