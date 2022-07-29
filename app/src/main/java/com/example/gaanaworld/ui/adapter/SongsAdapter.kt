package com.example.gaanaworld.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gaanaworld.R
import com.example.gaanaworld.data.model.Song
import com.example.gaanaworld.ui.HomeFragmentDirections
import com.example.gaanaworld.utils.toast
import com.example.gaanaworld.ui.viewmodels.SongsViewModel

class SongsAdapter(var context: Context , var songsViewModel: SongsViewModel) : RecyclerView.Adapter<SongsAdapter.SongViewHolder>() {
    private var songslst : MutableList<Song> = ArrayList()
    inner class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var thumbnailImg : ImageView = itemView.findViewById(R.id.song_thumbnail)
        var songNameTv : TextView = itemView.findViewById(R.id.song_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.songslist,parent,false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        Glide.with(context).load(songslst[position].thumbnail).into(holder.thumbnailImg)
        holder.songNameTv.text = songslst[position].name
        holder.itemView.setOnClickListener {
            context.toast("click on playbtn")
//            songslst[position].url?.let { it1 -> songsViewModel.playSong(it1) }
            val action = HomeFragmentDirections.actionHomeItemToMusicScreenFragment(songslst.toTypedArray(),position)
            it.findNavController().navigate(action)
        }
    }


    fun setSongslst(newsongslst : MutableList<Song>) {
        songslst.clear()
        songslst.addAll(newsongslst)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return songslst.size
    }

}