package com.example.gaanaworld.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.gaanaworld.R
import com.example.gaanaworld.ui.viewmodels.SongsViewModel
import com.google.android.exoplayer2.ui.PlayerView


class MusicScreenFragment : Fragment() {


    private val songsViewModel: SongsViewModel by activityViewModels()
    private lateinit var play_pause_btn:Button
    private lateinit var thumbnail_img : ImageView
    private lateinit var song_name_tv:TextView
    private lateinit var current_duration_tv:TextView
    private lateinit var duration_tv:TextView
    private lateinit var song_singer_name_tv:TextView
    private lateinit var song_seekbar:SeekBar
    private lateinit var playerView : PlayerView
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
        song_name_tv = view.findViewById(R.id.song_name_tv)
        song_singer_name_tv = view.findViewById(R.id.song_singer_name_tv)
        duration_tv = view.findViewById(R.id.song_duration_tv)
        song_seekbar = view.findViewById<SeekBar>(R.id.song_seekbar)
//        playerView = view.findViewById(R.id.player_view)




//        getting current duration of song and setting it in as max value of seekbar
        songsViewModel.getSongDuration().observe(viewLifecycleOwner) {
            Log.d("songduration", "onCreateView: "+it.toInt())
            song_seekbar.max = it.toInt()
            duration_tv.text = songsViewModel.durationConverter(it.toInt())
        }


//        loading song thumbnail in image view
        Glide.with(this).load(args.songsArray[args.currentPosition].thumbnail).into(thumbnail_img)

        //play pause song
        play_pause_btn.setOnClickListener {
            songsViewModel.playPauseSong()
        }

        //setting song name in textview
        song_name_tv.text = args.songsArray[args.currentPosition].name
        
//        setting singer name in textview
//         TODO: singer name in textview
        
//        playing song
        songsViewModel.initialized(args.songsArray[args.currentPosition].url)


        return view
    }


}