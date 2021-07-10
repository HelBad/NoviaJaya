package com.example.noviajaya.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.noviajaya.R
import com.squareup.picasso.Picasso
import java.text.DecimalFormat
import java.text.NumberFormat

class ViewholderPesanan(itemView: View): RecyclerView.ViewHolder(itemView) {
    internal var mView: View
    private var mClickListener: ClickListener? = null

    init{
        mView = itemView
        itemView.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view: View) {
                mClickListener!!.onItemClick(view, adapterPosition)
            }
        })
        itemView.setOnLongClickListener(object: View.OnLongClickListener {
            override fun onLongClick(view: View):Boolean {
                mClickListener!!.onItemLongClick(view, adapterPosition)
                return true
            }
        })
    }

    fun setDetails(ctx: Context, nama_produk:String, total:String, status:String) {
        var formatter: NumberFormat = DecimalFormat("#,###")
        val namaPesanan = mView.findViewById(R.id.produkPesanan) as TextView
        val hargaPesanan = mView.findViewById(R.id.hargaPesanan) as TextView
        val statusPesanan = mView.findViewById(R.id.statusPesanan) as TextView

        namaPesanan.text = nama_produk
        hargaPesanan.text = "Rp. " + formatter.format(total.toInt()) + ",00"
        statusPesanan.text = status
    }

    interface ClickListener {
        fun onItemClick(view: View, position:Int)
        fun onItemLongClick(view: View, position:Int)
    }

    fun setOnClickListener(clickListener:ViewholderPesanan.ClickListener) {
        mClickListener = clickListener
    }
}