

package com.example.ikt205prosjektoppgave_1.workers
/*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Parcelable
import androidx.core.net.toUri
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.ikt205prosjektoppgave_1.data.TodoList
import com.example.ikt205prosjektoppgave_1.data.TodoListItem
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize
import java.io.*

class FileWorker(appContext: Context, workerParams: WorkerParameters): Worker(appContext, workerParams) {

    private val reciever = object : BroadcastReceiver(){ // TODO: 4/9/2021 må kun registreres en gang cmp object
        override fun onReceive(context: Context?, intent: Intent?) {

            val filename = intent?.extras?.get("data") as List<*>
            println("hello this is fileworkers reciever")

            filename.forEach {
                println("filename")
                println(it)
            }
        }
    }

    val filter = IntentFilter().apply {
        addAction("SAVE_FILE") }

    init {
        applicationContext.registerReceiver(reciever, filter)
    }



    override fun doWork(): Result {
        val filePath = this.inputData.getString("filePath")
        if(filePath != null){
            val content = getFileContent(filePath)
            content.forEach {
                println("filecontent: $it")
            }
            return Result.success()
        }


        val content = this.inputData.getString("fileContent")
        val filename= this.inputData.getString("fileName")


        if(content != null && filename != null){
            val file = saveFile(filename, content)
            return Result.success(workDataOf("fileUri" to file.toUri().toString()))
        }

        return Result.failure()
    }

    private fun saveFile(filename:String, content:String): File {

        val filePath = this.applicationContext.getExternalFilesDir(null)
        val file = File(filePath, filename)

        /*
        // TODO: 4/7/2021 test
        // TODO: 4/7/2021 wrap i trys
        val testfile = File(filePath, "test123")
        val testitem = TodoListItem("itemnametest", true)
        val testitem2 = TodoListItem("itemnametest2", false)
        val testList = TodoList("testlist", mutableListOf(testitem, testitem2), 23)
        val testList2 = TodoList("testlist2", mutableListOf(testitem, testitem2), 36)
        val testlistlist = listOf(testList, testList2)
        val os = FileOutputStream(testfile, false)
        ObjectOutputStream(os).writeObject(testlistlist)

        val is123 = FileInputStream(testfile)
        val testobj = ObjectInputStream(is123).readObject() as List<*> // TODO: 4/9/2021 kan vi kanskje bruke denne alikavell med workeren
        println(testobj)
        // TODO: 4/7/2021 test
        // TODO: 4/7/2021 ok funker

         */
        FileOutputStream(file, true).bufferedWriter().use { writer ->
            writer.write(content)
        }
        applicationContext.unregisterReceiver(reciever)

        return file
    }

    private fun getFileContent(filePath: String):List<String>{
        return FileInputStream(filePath).bufferedReader().use {
            it.readLines()
        }
    }
}

 */