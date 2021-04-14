package com.example.ikt205prosjektoppgave_1

import com.example.ikt205prosjektoppgave_1.data.TodoList
import com.example.ikt205prosjektoppgave_1.data.TodoListItem
import org.junit.Assert
import org.junit.Test

class TodoListTest {
    @Test
    fun test_todoList(){

        val items = mutableListOf<TodoListItem>(
            TodoListItem("item1", false),
            TodoListItem("item2", true),
            TodoListItem("item3", false),
            TodoListItem("item4", true),
            TodoListItem("item5", false),
            TodoListItem("item6", true)
        )
        val todoList : TodoList = TodoList("list1", items, 50)

        Assert.assertEquals("list1", todoList.name)
        Assert.assertEquals("item1", todoList.items[0].name)
        Assert.assertEquals("item2", todoList.items[1].name)
        Assert.assertEquals("item3", todoList.items[2].name)
        Assert.assertEquals("item4", todoList.items[3].name)
        Assert.assertEquals("item5", todoList.items[4].name)
        Assert.assertEquals("item6", todoList.items[5].name)
        Assert.assertEquals(false, todoList.items[0].isCheckedOff)
        Assert.assertEquals(true, todoList.items[1].isCheckedOff)
        Assert.assertEquals(false, todoList.items[2].isCheckedOff)
        Assert.assertEquals(true, todoList.items[3].isCheckedOff)
        Assert.assertEquals(false, todoList.items[4].isCheckedOff)
        Assert.assertEquals(true, todoList.items[5].isCheckedOff)
        Assert.assertEquals(50, todoList.progress)
    }
}