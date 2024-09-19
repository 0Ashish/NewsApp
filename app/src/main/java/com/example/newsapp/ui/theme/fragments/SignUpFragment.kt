package com.example.newsapp.ui.theme.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {
    lateinit var binding: FragmentSignUpBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(layoutInflater)

        auth = FirebaseAuth.getInstance()

        binding.backtoSignIn.setOnClickListener{
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment )
        }

        registerUser()
        return binding.root
    }

    private fun registerUser() {
        binding.register.setOnClickListener{

             val email = binding.userEmail.text.toString()
             val username = binding.userName.text.toString()
             val password:String = binding.password.text.toString()
             val confirmPassword = binding.conPassword.text.toString()

             if(email.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
                 Toast.makeText(requireContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show()
             }else if(password != confirmPassword){
                 Toast.makeText(requireContext(),"Password Mismatch",Toast.LENGTH_SHORT).show()
             }else{
                 auth.createUserWithEmailAndPassword(email,password)
                     .addOnCompleteListener { task->
                         if(task.isSuccessful){
                             Toast.makeText(requireContext(),"Registration Successful",Toast.LENGTH_SHORT).show()
                             findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
                         }else{
                             Toast.makeText(requireContext(),"Registration Failed :${task.exception?.message}",Toast.LENGTH_SHORT).show()
                         }
                     }
             }
        }
    }
}