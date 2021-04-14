package com.example.ikt205prosjektoppgave_1

import com.example.ikt205prosjektoppgave_1.data.TodoList
import com.example.ikt205prosjektoppgave_1.data.TodoListItem
import org.junit.Test
import org.junit.Assert.*

class TodoListItemTest {

    @Test
    fun test_TodoListItem(){
        val item1 = TodoListItem("item1")
        val item2 = TodoListItem("item2", true)
        val item3 = TodoListItem("item3", false)

        assertEquals("item1", item1.name)
        assertEquals("item2", item2.name)
        assertEquals("item3", item3.name)
        assertEquals(false, item1.isCheckedOff)
        assertEquals(true, item2.isCheckedOff)
        assertEquals(false, item3.isCheckedOff)

    }
}