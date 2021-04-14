package com.example.ikt205prosjektoppgave_1.workers

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.net.toUri
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.ikt205prosjektoppgave_1.data.TodoList
import com.example.ikt205prosjektoppgave_1.data.TodoListItem
import com.example.ikt205prosjektoppgave_1.utilities.TAG
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.component1
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.io.FileDescriptor

class FirebaseWorker(appcontext: Context, workerParams: WorkerParameters): Worker(appcontext, workerParams) {

    override fun doWork(): Result {

        val fileUri = this.inputData.getString("fileUri")
        val userEmail = this.inputData.getString("userEmail")
        if(fileUri != null && userEmail != null){
            upload(fileUri.toUri(), userEmail)
        }

        val email = this.inputData.getString("email")
        if(email != null){
            download(email)
        }
        return Result.success()
    }

    
    private fun upload(file: Uri, userEmail: String){

        val storageRef = FirebaseStorage.getInstance().reference.child("$userEmail/${file.lastPathSegment}")
        val uploadTask = storageRef.putFile(file)
        uploadTask.addOnSuccessListener {
            Log.d(TAG, "saved file to fb $it")
        }.addOnFailureListener{
            Log.e(TAG, "failed to save file to fb $it")
        }
    }


    private fun download(email:String) {

        val storageRef = FirebaseStorage.getInstance().reference.child("${email}/TodoLists")
        val filePath = this.applicationContext.getExternalFilesDir(null)
        val file = File("$filePath", "TodoLists")

        storageRef.getFile(file.toUri())
                .addOnSuccessListener {
                    Log.d(TAG, "downloaded file from firebase")
                    val intent = Intent().apply {
                        action = "DOWNLOAD_COMPLETE"
                    }
                    applicationContext.sendBroadcast(intent)
                }
                .addOnFailureListener { Log.e(TAG, "failed to get file from firebase", it) }
    }
}