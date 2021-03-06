package com.example.gaanaworld.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.gaanaworld.R
import com.example.gaanaworld.ui.viewmodels.logoutUserViewModel


class ProfileFragment : Fragment() {


    private lateinit var btn :Button
    private val model : logoutUserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
          val view = inflater.inflate(R.layout.fragment_profile, container, false)

        model.getAuthState().observe(viewLifecycleOwner) {
            if(!it) {
                val action = ProfileFragmentDirections.actionProfileItemToMainActivity()
                findNavController().navigate(action)
            }
        }
        btn = view.findViewById(R.id.logoutbtn)

        btn.setOnClickListener {
            model.logoutUser()

        }
        return view
    }


}