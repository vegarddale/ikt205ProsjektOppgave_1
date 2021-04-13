package com.example.ikt205prosjektoppgave_1.data

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class TodoListItem(val name:String, var isCheckedOff:Boolean = false):Serializable, Parcelable



