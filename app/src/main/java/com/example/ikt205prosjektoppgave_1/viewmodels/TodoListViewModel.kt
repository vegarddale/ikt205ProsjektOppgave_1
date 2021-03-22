package com.example.ikt205prosjektoppgave_1.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ikt205prosjektoppgave_1.data.TodoList
import com.example.ikt205prosjektoppgave_1.data.TodoListItem

class TodoListViewModel: ViewModel() {

    private val listOverview = mutableListOf<TodoList>()

    val todoLists: MutableList<TodoList> get() = listOverview

    fun updateList(todoList: TodoList) = listOverview.add(todoList)

    fun updateTodoListItems(items:MutableList<TodoListItem>, position: Int){
        listOverview[position].todoList.addAll(items)
    }

    fun getListDetailsByIndex(index:Int) = todoLists[index].todoList

    fun removeListByIndex(index: Int){
        listOverview.removeAt(index)
    }
    fun removeItemByIndex(listIndex:Int , itemIndex: Int){
        println("listIndex: $listIndex, itemIndex: $itemIndex, listSize: ${listOverview[listIndex].todoList.size}")
        listOverview[listIndex].todoList.removeAt(itemIndex)
    }
}