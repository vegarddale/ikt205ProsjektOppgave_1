package com.example.ikt205prosjektoppgave_1.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ikt205prosjektoppgave_1.data.TodoListItem

class ListDetailsViewModel : ViewModel() {
    private val details = MutableLiveData<TodoListItem>()
    val items:LiveData<TodoListItem> get() = details

    fun updateDetails(test:TodoListItem){
        details.value = test
    }

}