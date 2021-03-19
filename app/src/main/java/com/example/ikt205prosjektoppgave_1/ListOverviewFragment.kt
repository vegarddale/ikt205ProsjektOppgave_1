package com.example.ikt205prosjektoppgave_1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ikt205prosjektoppgave_1.adapters.ListOverviewAdapter
import com.example.ikt205prosjektoppgave_1.databinding.FragmentListOverviewBinding
import com.example.ikt205prosjektoppgave_1.viewmodels.ListViewModel
import kotlinx.android.synthetic.main.fragment_list_overview.view.*


class ListOverviewFragment : Fragment() {

    private val listViewModel : ListViewModel by activityViewModels()
    //private lateinit var adapter: ListOverviewAdapter

    private var _binding: FragmentListOverviewBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //adapter = ListOverviewAdapter(listViewModel.list.value!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListOverviewBinding.inflate(layoutInflater)
        val view = binding.root

        view.test.setOnClickListener{
            listViewModel.list.value?.forEach{
                println(it)
            }
            navigateToListDetails(view)
        }

        //binding.listRecyclerView.layoutManager = LinearLayoutManager(this.context)
        //binding.listRecyclerView.adapter = adapter

        return view
    }

    private fun navigateToListDetails(view:FrameLayout){
        val action = ListOverviewFragmentDirections.actionListOverviewFragmentToListDetailsFragment()
        view.findNavController().navigate(action)
    }
}