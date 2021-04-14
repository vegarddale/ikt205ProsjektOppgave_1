package com.example.ikt205prosjektoppgave_1.viewmodels

import androidx.lifecycle.ViewModel
import com.example.ikt205prosjektoppgave_1.data.TodoList
import com.example.ikt205prosjektoppgave_1.data.TodoListItem


class TodoListViewModel: ViewModel() {

    private val listOverview = mutableListOf<TodoList>()

    val todoLists: MutableList<TodoList> get() = listOverview

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
    }
    fun updateTodoListCheckBox(listIndex: Int, itemIndex: Int){
        listOverview[listIndex].items[itemIndex].isCheckedOff = !listOverview[listIndex].items[itemIndex].isCheckedOff
    }

    fun load(todoLists :List<TodoList>){
         listOverview.clear()
        listOverview.addAll(todoLists)
    }

    fun clear(){
        listOverview.clear()
    }


}