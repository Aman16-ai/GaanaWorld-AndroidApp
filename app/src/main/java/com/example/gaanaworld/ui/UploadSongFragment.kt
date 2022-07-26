package com.example.gaanaworld.ui

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.example.gaanaworld.R
import com.example.gaanaworld.helper.TaskProgressTracker
import com.example.gaanaworld.helper.getFileExtension
import com.example.gaanaworld.helper.isImageFile
import com.example.gaanaworld.helper.isSongFile
import com.example.gaanaworld.ui.viewmodels.SongsViewModel
import com.example.gaanaworld.utils.toast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UploadSongFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UploadSongFragment : Fragment() {

    private lateinit var uploadBtn: Button
    private lateinit var SongBtn: Button
    private lateinit var imgUri:Uri
    private lateinit var songUri: Uri
    private lateinit var img:ImageView
    private lateinit var nameet:EditText
    private lateinit var langet:EditText
    private lateinit var progressBar: ProgressBar

    private val songsViewModel : SongsViewModel by activityViewModels()
    private val getResult = registerForActivityResult(ActivityResultContracts.GetContent()) {
        it?.let {
            context?.let { context->
               val type : String? = getFileExtension(context,it)
                type?.let { type ->
                    if(isImageFile(type)) {
                        imgUri = it
                        img.setImageURI(it)
                    }
                    else if(isSongFile(type)) {
                        songUri = it
                        SongBtn.text = it.toString()
                    }
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_upload_song, container, false)

        uploadBtn = view.findViewById(R.id.uploadBtn)
        SongBtn = view.findViewById(R.id.pick_song_btn)
        img = view.findViewById(R.id.pick_thumbnail_img)
        nameet = view.findViewById(R.id.song_name_et)
        langet = view.findViewById(R.id.song_lang_et)
        progressBar = view.findViewById(R.id.upload_progress)
        progressBar.visibility = View.GONE

        img.setOnClickListener {
            getResult.launch("image/*")
        }

        SongBtn.setOnClickListener {
            getResult.launch("audio/mpeg")
        }

        songsViewModel.uploadProgressTracker.observe(viewLifecycleOwner) {
            it.let {
                when(it) {
                    is TaskProgressTracker.Done -> {
                        progressBar.visibility = View.GONE
                        context?.toast(it.message?:"Done")
                    }
                    is TaskProgressTracker.Loading -> {
                        progressBar.visibility = View.VISIBLE
                    }
                    is TaskProgressTracker.Error -> {
                        progressBar.visibility = View.GONE
                        context?.toast(it.message?:"something went wrong")
                    }
                }
            }
        }

        uploadBtn.setOnClickListener {
//            context?.let { context ->
//                getFileExtension(context,songUri as Uri)?.let { songType ->
//                    getFileExtension(context,imgUri as Uri)?.let { thumbnailType ->
//                        songsViewModel.uploadSongtoDB(nameet.text.toString(),langet.text.toString(),imgUri as Uri,songUri as Uri,
//                            songType,
//                            thumbnailType
//                        )
//                    }
//                }
//            }
            context?.let {
                songsViewModel.uploadSongtoDB(nameet.text.toString(),langet.text.toString(),imgUri,songUri,"mp3","jpeg")
            }
        }
        return view
    }




}