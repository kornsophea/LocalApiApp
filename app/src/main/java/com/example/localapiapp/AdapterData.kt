package com.example.localapiapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class AdapterData(private var list:ArrayList<Data> =ArrayList()):RecyclerView.Adapter<AdapterData.View>() {
    private var onClickDeleteItem:((Data)->Unit)?=null
    private var onClickItem:((Data)->Unit)?=null
    @SuppressLint("NotifyDataSetChanged")
    fun addItems(item: ArrayList<Data>){
        this.list=item
        notifyDataSetChanged()
    }
    fun setOnClickItem(callback:(Data)->Unit){
        this.onClickItem=callback
    }
    fun setOnClickDeleteItem(callback:(Data)->Unit){
        this.onClickDeleteItem=callback
    }
    class View(view:android.view.View):RecyclerView.ViewHolder(view){
        val id=view.findViewById(R.id.tv_id) as TextView
        val email=view.findViewById(R.id.tv_email) as TextView
        val imgDelete=view.findViewById(R.id.img_delete) as ImageView
        val profile=view.findViewById(R.id.img_profile) as ImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): View {
         return View(LayoutInflater.from(parent.context).inflate(R.layout.list_data_item,parent,false))
    }

    override fun onBindViewHolder(holder: View, position: Int) {
        val dataList=list[position]
        holder.id.text=dataList.id.toString()
        holder.email.text=dataList.name
        Picasso.get().load("http://192.168.41.44:8080/storage/"+dataList.image).into(holder.profile)
        holder.itemView.setOnClickListener { onClickItem?.invoke(dataList) }
        holder.imgDelete.setOnClickListener { onClickDeleteItem?.invoke(dataList) }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}