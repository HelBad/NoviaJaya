package com.example.noviajaya.customer.aktivitas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noviajaya.R
import com.example.noviajaya.adapter.ViewholderPesanan
import com.example.noviajaya.model.Pesanan
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.*

class ActivityPesanan : AppCompatActivity() {
    lateinit var mRecyclerView: RecyclerView
    lateinit var mLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.customer_activity_pesanan)

        mRecyclerView = findViewById(R.id.recyclerPesanan)
        mLayoutManager = LinearLayoutManager(this)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = mLayoutManager
    }

    override fun onStart() {
        super.onStart()
        val query = FirebaseDatabase.getInstance().getReference("Pesanan").child("Menunggu Konfirmasi Penjual").orderByChild(intent.getStringExtra("id_user"))
        val firebaseRecyclerAdapter = object: FirebaseRecyclerAdapter<Pesanan, ViewholderPesanan>(
            Pesanan::class.java,
            R.layout.card_pesanan,
            ViewholderPesanan::class.java,
            query
        ) {
            override fun populateViewHolder(viewHolder: ViewholderPesanan, model: Pesanan, position:Int) {
                viewHolder.setDetails(applicationContext, model.nama_produk, model.total, model.status)
            }
            override fun onCreateViewHolder(parent: ViewGroup, viewType:Int): ViewholderPesanan {
                val viewHolder = super.onCreateViewHolder(parent, viewType)
                viewHolder.setOnClickListener(object: ViewholderPesanan.ClickListener {
                    override fun onItemClick(view: View, position:Int) {}
                    override fun onItemLongClick(view: View, position:Int) {}
                })
                return viewHolder
            }
        }
        mRecyclerView.adapter = firebaseRecyclerAdapter
    }
}