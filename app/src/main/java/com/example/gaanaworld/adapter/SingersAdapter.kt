package com.example.gaanaworld.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gaanaworld.R
import com.example.gaanaworld.data.model.Singers
import com.example.gaanaworld.utils.toast
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot

class SingersAdapter(var context: Context) : RecyclerView.Adapter<SingersAdapter.myViewHolder>() {
    private var singerslist : MutableList<QueryDocumentSnapshot> = ArrayList()

    private var selectedSingersList : MutableList<QueryDocumentSnapshot> = ArrayList()

    inner class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         var singerImgView : ImageView = itemView.findViewById(R.id.singer_img)
         var singerNameTv : TextView = itemView.findViewById(R.id.singer_name_tv)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.singerslist,parent,false)
        return myViewHolder(view)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        var singer = singerslist[position]
        Glide.with(context).load(singer.get("profileImgUrl")).into(holder.singerImgView)
        holder.singerNameTv.text = singer.getString("name")
        holder.itemView.setOnClickListener{
            context.toast("Clicked")

            var isPresent = false
            for(i in selectedSingersList) {
                if(i.id == singer.id) {
                    isPresent = true
                }
            }

            if(isPresent) {
                selectedSingersList.remove(singer)
            }
            else {
                selectedSingersList.add(singer)
            }
            //selectedSingersList.add(singer)
        }
    }
    fun setSingers(singers: MutableList<QueryDocumentSnapshot>) {
        singerslist.clear()
        singerslist.addAll(singers)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return singerslist.size
    }

    fun getUserSelectedSingers() : MutableList<QueryDocumentSnapshot> {
        return selectedSingersList
    }

}