package com.example.ikt205prosjektoppgave_1.data

data class TodoList(val name:String, val items:MutableList<TodoListItem>, var progress:Int= 0)