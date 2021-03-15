package com.example.ikt205prosjektoppgave_1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import com.example.ikt205prosjektoppgave_1.databinding.FragmentListOverviewBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_list_overview.*
import kotlinx.android.synthetic.main.fragment_list_overview.view.*


class ListOverviewFragment : Fragment() {


    private var _binding: FragmentListOverviewBinding? = null
    val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListOverviewBinding.inflate(layoutInflater)
        val view = binding.root
        val action = ListOverviewFragmentDirections.actionListOverviewFragmentToListDetailsFragment()
        view.test.setOnClickListener{
            view.findNavController().navigate(action)
        }
        return view
    }

}