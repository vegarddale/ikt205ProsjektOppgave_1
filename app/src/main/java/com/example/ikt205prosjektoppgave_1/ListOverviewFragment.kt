package com.example.ikt205prosjektoppgave_1
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.*
import com.example.ikt205prosjektoppgave_1.adapters.ListOverviewItemAdapter
import com.example.ikt205prosjektoppgave_1.data.TodoList
import com.example.ikt205prosjektoppgave_1.databinding.FragmentListOverviewBinding
import com.example.ikt205prosjektoppgave_1.utilities.TAG
import com.example.ikt205prosjektoppgave_1.viewmodels.TodoListViewModel
import com.example.ikt205prosjektoppgave_1.workers.FirebaseWorker
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_list_overview.view.*
import java.io.*


class ListOverviewFragment : Fragment() {

    private val todoListViewModel : TodoListViewModel by activityViewModels()
    private lateinit var adapter: ListOverviewItemAdapter
    private val args : ListOverviewFragmentArgs by navArgs()

    private var _binding: FragmentListOverviewBinding? = null
    val binding get() = _binding!!

    val filter = IntentFilter().apply {
        addAction("DELETE_LIST")
        addAction("DOWNLOAD_COMPLETE")
    }

    val reciever = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when(intent?.action){
                "DELETE_LIST" -> {
                    val index = intent.extras?.get("$TAG.LIST_INDEX") as Int
                    todoListViewModel.removeListByIndex(index)
                    adapter.updateListOverview(todoListViewModel.todoLists)}
                "DOWNLOAD_COMPLETE" -> {
                    val lists = getListsFromFile("TodoLists")
                    if (lists != null){
                        val todoLists = formatLists(lists)
                        todoListViewModel.load(todoLists)
                        adapter.updateListOverview(todoListViewModel.todoLists)
                    }
                }
            }
            goAsync()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = ListOverviewItemAdapter()

        if(!args.freshLogin && !args.isAnonymous){
            val lists = getListsFromFile("TodoLists")
            if(lists != null){
                val todoLists = formatLists(lists)
                todoListViewModel.load(todoLists)

                adapter.updateListOverview(todoListViewModel.todoLists)
            }
        }
        if(args.isAnonymous){
            val lists = getListsFromFile("Anonymous")
            if(lists != null){
                val todoLists = formatLists(lists)
                todoListViewModel.load(todoLists)

                adapter.updateListOverview(todoListViewModel.todoLists)
            }
        }
        requireActivity().registerReceiver(reciever, filter)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListOverviewBinding.inflate(layoutInflater)
        val view = binding.root

        binding.listRecyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.listRecyclerView.adapter = adapter

        binding.addTodoListBtn.setOnClickListener{
            val name = view.addTodoListName.text.toString()
            if(name != ""){
                val todoList = TodoList(name, mutableListOf())
                todoListViewModel.updateList(todoList)
                adapter.updateListOverview(todoListViewModel.todoLists)
            }
            else{
                Toast.makeText(requireActivity(), "List name cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().unregisterReceiver(reciever)
        todoListViewModel.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        when(args.isAnonymous){
            true -> {
                val fileUri = saveFile("Anonymous", todoListViewModel.todoLists)
                uploadToFirebase(fileUri, "Anonymous")
            }
            else -> {
                val fileUri = saveFile("TodoLists", todoListViewModel.todoLists)
                uploadToFirebase(fileUri, Firebase.auth.currentUser!!.email)
            }
        }
    }

    private fun getListsFromFile(filename:String):List<*>?{

        val filePath = requireActivity().getExternalFilesDir(null)
        val file = File(filePath, filename)
        try{
            val fis = FileInputStream(file)
            return ObjectInputStream(fis).readObject() as List<*>
        }catch (e:IOException){
            Log.e(TAG, e.toString())
        }
        return null
    }

    private fun formatLists(lists: List<*>) : List<TodoList>{
        val todoLists = mutableListOf<TodoList>()
        lists.forEach {
            todoLists.add(it as TodoList)
        }
        return todoLists
    }

    private fun saveFile(filename:String, todoLists: List<TodoList>): Uri {
        
        val filePath = requireActivity().getExternalFilesDir(null)
        val file = File(filePath, filename)
        val fos = FileOutputStream(file, false)
        ObjectOutputStream(fos).writeObject(todoLists)
        return file.toUri()
    }

    private fun uploadToFirebase(fileUri : Uri, userEmail : String?) {
        val firebaseUploadWorkRequest: OneTimeWorkRequest = OneTimeWorkRequestBuilder<FirebaseWorker>()
                .setInputData(workDataOf("fileUri" to fileUri.toString(), "userEmail" to userEmail))
                .build()

        WorkManager.getInstance(requireActivity())
                .enqueue(firebaseUploadWorkRequest)

    }
}