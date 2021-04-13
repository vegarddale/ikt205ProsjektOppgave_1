package com.example.ikt205prosjektoppgave_1.data
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
import java.io.Serializable

@Parcelize
data class TodoList(val name:String, val items:MutableList<TodoListItem>, var progress:Int= 0):Serializable, Parcelable