package com.example.ikt205prosjektoppgave_1.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ikt205prosjektoppgave_1.data.TodoList

class ListViewModel: ViewModel() {
    private val listOverview = MutableLiveData<MutableList<TodoList>>()
    val list: MutableLiveData<MutableList<TodoList>> get() = listOverview

    fun updateList(data: MutableList<TodoList>){
        listOverview.value = data
    }
}