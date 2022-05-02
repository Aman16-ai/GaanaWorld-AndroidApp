package com.example.gaanaworld.ui

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gaanaworld.R
import com.example.gaanaworld.adapter.SongsAdapter
import com.example.gaanaworld.viewmodels.SongsViewModel
import com.example.gaanaworld.viewmodels.logoutUserViewModel
import com.google.firebase.firestore.QueryDocumentSnapshot


class HomeFragment : Fragment() {
    private lateinit var btn : Button
    private val model : logoutUserViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private val songsViewModel : SongsViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        model.getAuthState().observe(viewLifecycleOwner) {
            if(!it) {
                val action = HomeFragmentDirections.actionHomeFragmentToMainActivity()
                findNavController().navigate(action)
            }
        }
        val view =  inflater.inflate(R.layout.fragment_home, container, false)
        btn = view.findViewById(R.id.logoutbtn)

        recyclerView = view.findViewById(R.id.songs_recyclerview)
        var songsAdapter = SongsAdapter(requireContext(),songsViewModel)

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = songsAdapter
        }
        songsViewModel.getSongs().observe(viewLifecycleOwner) {
            for(i in it) {
                Log.d("allsongs", "onCreateView: "+i.Name)
            }

            songsAdapter.setSongslst(it)
        }

        btn.setOnClickListener {
            model.logoutUser()

        }
        return view
    }


}