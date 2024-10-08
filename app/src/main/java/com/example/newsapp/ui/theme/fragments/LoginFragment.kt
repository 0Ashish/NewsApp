package com.example.newsapp.ui.theme.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.newsapp.MainActivity
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginFragment : Fragment(R.layout.fragment_login) {
    lateinit var binding : FragmentLoginBinding
    private lateinit var auth :FirebaseAuth

//    override fun onStart() {
//        super.onStart()
//        val currentUser:FirebaseUser? = auth.currentUser
//        requireActivity().apply {
//            if(currentUser!=null){
//                startActivity(Intent(requireContext(), MainActivity::class.java))
//                finish()
//            }
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentLoginBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        binding.signUp.setOnClickListener { findNavController().navigate(resId = R.id.action_loginFragment_to_signUpFragment) }

        binding.login.setOnClickListener{
            val userName = binding.userID.text.toString()
            val pass = binding.password.text.toString()

            if(userName.isEmpty() || pass.isEmpty()){
                Toast.makeText(requireContext(),"Please fill all fields",Toast.LENGTH_SHORT).show()
            }else{
                 auth.signInWithEmailAndPassword(userName,pass)
                     .addOnCompleteListener { task->
                         if(task.isSuccessful){
                             Toast.makeText(requireContext(),"Login Successful",Toast.LENGTH_SHORT).show()
                             // Create NavOptions with popUpTo
                             val navOptions = NavOptions.Builder()
                                 .setPopUpTo(R.id.loginFragment, true)  // inclusive = true
                                 .build()


                             // Navigate to headlineFragment with NavOptions
                             Log.d("LoginFragment", "Navigating to HeadlineFragment with popUpTo")
                             findNavController().navigate(R.id.action_loginFragment_to_headlineFragment, null, navOptions)
                         }else{
                             Toast.makeText(requireContext(),"Login Failed ${task.exception?.message}",Toast.LENGTH_SHORT).show()
                         }
                     }
            }

        }
        return binding.root
    }



}