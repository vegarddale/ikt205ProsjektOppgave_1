package com.example.ikt205prosjektoppgave_1.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ikt205prosjektoppgave_1.data.TodoList
import com.example.ikt205prosjektoppgave_1.data.TodoListItem

class TodoListViewModel: ViewModel() {


    private val listOverview = mutableListOf<TodoList>()

    val progress = MutableLiveData<Int>()
    var test = MutableLiveData<MutableList<TodoList>>()
    val todoLists: MutableList<TodoList> get(){
        return listOverview
    }

    // kan vi wrappe todoLists i en livedata og bare observe den?
    init {
        test = MutableLiveData()
        progress.value = 0
    }

    fun updateList(todoList: TodoList) = listOverview.add(todoList)

    fun updateTodoListItems(items:List<TodoListItem>, position: Int){
        listOverview[position].items.addAll(items)
    }

    fun getListDetailsByIndex(index:Int) = todoLists[index].items

    fun removeListByIndex(index: Int){
        listOverview.removeAt(index)
    }
    fun removeItemByIndex(listIndex:Int , itemIndex: Int){
        listOverview[listIndex].items.removeAt(itemIndex)
    }

    fun updateProgressBar(index: Int, progress:Int){
        listOverview[index].progress += progress
        println(listOverview[index].progress)
    }
    fun updateTodoListCheckBox(listIndex: Int, itemIndex: Int){
        listOverview[listIndex].items[itemIndex].isCheckedOff = !listOverview[listIndex].items[itemIndex].isCheckedOff
    }
}