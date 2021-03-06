package com.example.gaanaworld.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.gaanaworld.R
import com.example.gaanaworld.ui.viewmodels.RegisterUserViewModel


class RegisterUserFragment : Fragment() {
    private lateinit var firstnameEt : EditText
    private lateinit var lastnameEt : EditText
    private lateinit var emailEt : EditText
    private lateinit var passwordEt : EditText
    private lateinit var logintv : TextView
    private lateinit var btn : Button
    private val registerUserViewModel : RegisterUserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register_user, container, false)
        registerUserViewModel.getAuthStatus().observe(viewLifecycleOwner) {
            if(it) {
                val action = RegisterUserFragmentDirections.actionRegisterUserFragmentToChooseSingersFragment()
                findNavController().navigate(action)

            }
        }

        firstnameEt = view.findViewById(R.id.register_firstname_et)
        lastnameEt = view.findViewById(R.id.register_lastname_et)
        emailEt = view.findViewById(R.id.register_email_et)
        passwordEt = view.findViewById(R.id.register_pass_et)
        logintv = view.findViewById(R.id.loginViewTv)
        btn = view.findViewById(R.id.registerBtn)

        btn.setOnClickListener {
            registerUserViewModel.registerUser(firstnameEt.text.toString(), lastnameEt.text.toString(),emailEt.text.toString(),passwordEt.text.toString())
        }
        logintv.setOnClickListener {
            val action = RegisterUserFragmentDirections.actionRegisterUserFragmentToLoginFragment()
            findNavController().navigate(action)

        }
        return view
    }


}