package com.example.gaanaworld.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.gaanaworld.R
import com.example.gaanaworld.ui.viewmodels.SongsViewModel
import com.google.android.exoplayer2.ui.PlayerView


class MusicScreenFragment : Fragment() {


    private val songsViewModel: SongsViewModel by activityViewModels()
    private lateinit var play_pause_btn:ImageButton
    private lateinit var thumbnail_img : ImageView
    private lateinit var song_name_tv:TextView
    private lateinit var current_duration_tv:TextView
    private lateinit var duration_tv:TextView
    private lateinit var song_singer_name_tv:TextView
    private lateinit var song_seekbar:SeekBar
    private var updateSeekbar = true
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
        current_duration_tv = view.findViewById(R.id.song_current_duration_tv)
        song_seekbar = view.findViewById(R.id.song_seekbar)

        song_seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if(p2) {
                    updateCurrentDurationUI(p1.toLong())
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                updateSeekbar = false
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                p0?.let {
                    songsViewModel.seekTo(it.progress.toLong())
                    updateSeekbar = true
                }
            }

        })


//        initialzing the exoplayer with song song
        songsViewModel.initialized(args.songsArray[args.currentPosition].url)

//        getting total duration of song and setting it in as max value of seekbar
        songsViewModel.getSongDuration().observe(viewLifecycleOwner) {
            Log.d("songduration", "onCreateView: "+it.toInt())
            song_seekbar.max = it.toInt()
            duration_tv.text = songsViewModel.durationConverter(it.toInt())
        }

        // getting current duration of song and updating the seekbar progress
        songsViewModel.currentPosition.observe(viewLifecycleOwner) {
            Log.d("current", "onCreateView: "+it.toInt())
            updateCurrentDurationUI(it!!)
        }

//        loading song thumbnail in image view
        Glide.with(this).load(args.songsArray[args.currentPosition].thumbnail).into(thumbnail_img)

        //play pause song
        play_pause_btn.setOnClickListener {
            songsViewModel.playPauseSong()
        }

        //setting song name in textview
        song_name_tv.text = args.songsArray[args.currentPosition].name

        return view
    }

    private fun updateCurrentDurationUI(it: Long) {
        if(updateSeekbar) {
            song_seekbar.progress = it.toInt()
            current_duration_tv.text = songsViewModel.durationConverter(it.toInt())
        }
    }


}