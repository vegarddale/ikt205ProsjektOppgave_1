package com.example.ikt205prosjektoppgave_1

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleObserver
import androidx.navigation.findNavController
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.ikt205prosjektoppgave_1.databinding.FragmentSignInBinding
import com.example.ikt205prosjektoppgave_1.utilities.TAG
import com.example.ikt205prosjektoppgave_1.viewmodels.TodoListViewModel
import com.example.ikt205prosjektoppgave_1.workers.FirebaseWorker
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SignInFragment : Fragment() {

    private var _binding:FragmentSignInBinding? = null
    val binding get() = _binding!!


    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var auth: FirebaseAuth

    private val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if(it.data != null){
            when(auth.currentUser?.email){
                null -> {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                    try {
                        val account = task.getResult(ApiException::class.java)
                        Log.d(TAG, account?.id!!)
                        println(it.resultCode)
                        firebaseAuthWithGoogle(account.idToken)
                    } catch (e: ApiException){
                        Log.w(TAG, "blabla", e)
                    }
                }
                else -> {
                    val action = SignInFragmentDirections.actionSignInFragmentToListOverviewFragment()
                    view?.findNavController()?.navigate(action)
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        auth = Firebase.auth
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentSignInBinding.inflate(layoutInflater)
        val view = binding.root

        binding.googleSignIn.setOnClickListener{
            getContent.launch(googleSignInClient.signInIntent)
        }
        binding.anonymousSignIn.setOnClickListener{
            signInAnonymously()
        }
        return view
    }


    override fun onDestroy() {
        super.onDestroy()
        googleSignInClient.signOut()
        Firebase.auth.signOut() // TODO: 4/1/2021 sign out another way? dont sign out when every time user leaves list overview
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
                .addOnCompleteListener(requireActivity()){
                    if (it.isSuccessful) {
                        Log.d(TAG, "signInWithCredential:success")
                        getDataFromFirebaseToFile(auth.currentUser!!.email)
                        val action = SignInFragmentDirections.actionSignInFragmentToListOverviewFragment(true)
                        view?.findNavController()?.navigate(action)

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", it.exception)
                    }
                }
    }
    private fun signInAnonymously() {
        Firebase.auth.signInAnonymously().addOnSuccessListener {
            Log.d(TAG, "login sucess ${it?.user.toString()}")
            val action = SignInFragmentDirections.actionSignInFragmentToListOverviewFragment(isAnonymous = true)
            view?.findNavController()?.navigate(action)
        }.addOnFailureListener{
            Log.e(TAG, "login failed", it)
        }
    }

    private fun getDataFromFirebaseToFile(userEmail:String?) {
        val firebaseDownloadWorkRequest: OneTimeWorkRequest = OneTimeWorkRequestBuilder<FirebaseWorker>()
                .setInputData(workDataOf("email" to userEmail))
                .build()

        WorkManager.getInstance(requireActivity())
                .enqueue(firebaseDownloadWorkRequest)
    }
}

