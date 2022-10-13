package com.example.localapiapp.Node


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.example.localapiapp.Audio.AudioDisplayActivity
import com.example.localapiapp.R
import com.example.localapiapp.Video.VideoDisplayActivity
import com.squareup.picasso.Picasso

class UserAdapter(private var list:ArrayList<User>,var context: Context):RecyclerView.Adapter<UserAdapter.View>() {
    class View(view:android.view.View):RecyclerView.ViewHolder(view) {
        val id = view.findViewById<TextView>(R.id.text_id) as TextView
        val email = view.findViewById<TextView>(R.id.text_email) as TextView
        val image = view.findViewById<ImageView>(R.id.img_profile) as ImageView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): View {
        return View(LayoutInflater.from(parent.context).inflate(R.layout.node_list_data,parent,false))
    }

    override fun onBindViewHolder(holder: View, position: Int) {
        val data=list[position]
        holder.id.text=data.id.toString()
        holder.email.text=data.email
        Picasso.get().load(list[position].image).into(holder.image)

        holder.id.setOnClickListener {
            val intent=Intent(context,VideoDisplayActivity::class.java)
            intent.putExtra("videoUrl",data.video)
            intent.putExtra("audio",data.audio)
            context.startActivity(intent)
        }

        holder.email.setOnClickListener {
            val intent=Intent(context,AudioDisplayActivity::class.java)
            intent.putExtra("audio",data.audio)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}