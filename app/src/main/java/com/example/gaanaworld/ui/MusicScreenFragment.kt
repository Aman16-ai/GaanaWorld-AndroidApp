package com.example.gaanaworld.ui

import android.media.Image
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.gaanaworld.R
import com.example.gaanaworld.viewmodels.SongsViewModel


class MusicScreenFragment : Fragment() {


    private val songsViewModel: SongsViewModel by activityViewModels()
    private lateinit var play_pause_btn:Button
    private lateinit var thumbnail_img : ImageView
    val args : MusicScreenFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_music_screen, container, false)
        play_pause_btn = view.findViewById(R.id.play_pause_btn)
        thumbnail_img = view.findViewById(R.id.current_song_thumbnail)

        songsViewModel.getCurrentSongDuration().observe(viewLifecycleOwner) {
            Log.d("songduration", "onCreateView: "+it)
        }

        Glide.with(this).load(args.songsArray[args.currentPosition].thumbnail).into(thumbnail_img)
        play_pause_btn.setOnClickListener {
            songsViewModel.playPauseSong()
        }

        args.songsArray.forEach { Log.d("allsongs", "onCreateView: "+it.Name) }
        songsViewModel.playSong(args.songsArray[args.currentPosition].url)

        return view
    }


}