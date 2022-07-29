package com.example.gaanaworld.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gaanaworld.R
import com.example.gaanaworld.data.model.Singers
import com.example.gaanaworld.ui.adapter.SingersAdapter
import com.example.gaanaworld.utils.toast
import com.example.gaanaworld.ui.viewmodels.ChooseSingersViewModel
import com.google.firebase.firestore.QueryDocumentSnapshot


class ChooseSingersFragment : Fragment() {

    private lateinit var saveSingersBtn : Button
    private lateinit var skipbtn : Button
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
        saveSingersBtn = view.findViewById(R.id.save_singers_btn)
        skipbtn = view.findViewById(R.id.skipe_btn)
        recyclerView = view.findViewById(R.id.signers_recyclerview)

        chooseSingersViewModel.navStatus.observe(viewLifecycleOwner) {
            if(it) {
                val action = ChooseSingersFragmentDirections.actionChooseSingersFragmentToHomeActivity()
                findNavController().navigate(action)
            }
        }

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context,2)
            adapter = singersAdapter
        }
        chooseSingersViewModel.getAllSingers().observe(viewLifecycleOwner) {
            it?.let {
                for(i in it) {
                    Log.d("singers", "id ${i.toString()} name ${i.name}")
                }
                singersAdapter.setSingers(it.toList() as MutableList<Singers>)
            }
        }


        skipbtn.setOnClickListener {
            chooseSingersViewModel.skipedSingers()

        }
        saveSingersBtn.setOnClickListener {
            context?.toast("saving your choices")
            val singers = singersAdapter.getUserSelectedSingers()
            for (i in singers) {
                Log.d("usersingers", "onCreateView: "+i.name)
            }
            chooseSingersViewModel.saveUserSingers(singers)
        }

        return view
    }


}