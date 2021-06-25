package com.example.noviajaya.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.noviajaya.R
import com.squareup.picasso.Picasso

class ViewholderBeranda(itemView:View): RecyclerView.ViewHolder(itemView) {
    internal var mView:View
    private var mClickListener: ClickListener? = null

    init{
        mView = itemView
        itemView.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view:View) {
                mClickListener!!.onItemClick(view, adapterPosition)
            }
        })
        itemView.setOnLongClickListener(object: View.OnLongClickListener {
            override fun onLongClick(view:View):Boolean {
                mClickListener!!.onItemLongClick(view, adapterPosition)
                return true
            }
        })
    }

    fun setDetails(ctx: Context, nama:String, harga:String, deskripsi:String, gambar:String) {
        val namaBeranda = mView.findViewById(R.id.namaBeranda) as TextView
        val hargaBeranda = mView.findViewById(R.id.hargaBeranda) as TextView
        val deskripsiBeranda = mView.findViewById(R.id.deskripsiBeranda) as TextView
        val gambarBeranda = mView.findViewById(R.id.gambarBeranda) as ImageView

        namaBeranda.text = nama
        hargaBeranda.text = harga
        deskripsiBeranda.text = deskripsi
        Picasso.get().load(gambar).into(gambarBeranda)
    }

    interface ClickListener {
        fun onItemClick(view:View, position:Int)
        fun onItemLongClick(view:View, position:Int)
    }

    fun setOnClickListener(clickListener:ViewholderBeranda.ClickListener) {
        mClickListener = clickListener
    }
}