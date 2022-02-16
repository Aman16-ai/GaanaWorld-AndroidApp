package com.example.gaanaworld.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gaanaworld.R
import com.example.gaanaworld.adapter.SingersAdapter
import com.example.gaanaworld.data.model.Singers
import com.example.gaanaworld.viewmodels.ChooseSingersViewModel
import com.google.firebase.firestore.QueryDocumentSnapshot


class ChooseSingersFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private val chooseSingersViewModel : ChooseSingersViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_choose_singers, container, false)
        var singersAdapter = SingersAdapter(requireContext())

        recyclerView = view.findViewById(R.id.signers_recyclerview)
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context,2)
            adapter = singersAdapter
        }
        chooseSingersViewModel.getAllSingers().observe(viewLifecycleOwner) {
            for(i in it) {
                Log.d("singers", "onCreateView: "+i.get("name"))
            }
            singersAdapter.setSingers(it.toList() as MutableList<QueryDocumentSnapshot>)
        }


        return view
    }


}