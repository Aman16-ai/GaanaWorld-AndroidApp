package com.example.gaanaworld.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gaanaworld.R
import com.example.gaanaworld.ui.adapter.SongsAdapter
import com.example.gaanaworld.ui.viewmodels.SongsViewModel


class HomeFragment : Fragment() {
    private lateinit var btn : Button

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
        val view =  inflater.inflate(R.layout.fragment_home, container, false)


        recyclerView = view.findViewById(R.id.songs_recyclerview)
        var songsAdapter = SongsAdapter(requireContext(),songsViewModel)

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = songsAdapter
        }
        songsViewModel.getSongs().observe(viewLifecycleOwner) {
            for(i in it) {
                Log.d("allsongs", "onCreateView: "+i.name)
            }

            songsAdapter.setSongslst(it)
        }


        return view
    }


}