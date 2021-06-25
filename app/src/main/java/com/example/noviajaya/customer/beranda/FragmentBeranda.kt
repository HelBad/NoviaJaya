package com.example.noviajaya.customer.beranda

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.models.SlideModel
import com.example.noviajaya.R
import com.example.noviajaya.adapter.ViewholderBeranda
import com.example.noviajaya.model.Produk
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.*

class FragmentBeranda : Fragment() {
    lateinit var imageSlider: com.denzcoskun.imageslider.ImageSlider
    lateinit var mRecyclerView: RecyclerView
    lateinit var mLayoutManager: LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.customer_fragment_beranda, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val actionBar = requireActivity().findViewById(R.id.toolbarBeranda) as Toolbar
        (activity as AppCompatActivity).setSupportActionBar(actionBar)

        imageSlider = requireActivity().findViewById(R.id.imgSlider)
        mRecyclerView = requireActivity().findViewById(R.id.recyclerBeranda)

        val slideModels = arrayListOf<SlideModel>()
        slideModels.add(SlideModel(R.drawable.sampel1))
        slideModels.add(SlideModel(R.drawable.sampel2))
        imageSlider.setImageList(slideModels, true)

        mLayoutManager = LinearLayoutManager(requireActivity())
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = mLayoutManager
    }

    override fun onStart() {
        super.onStart()
        val query = FirebaseDatabase.getInstance().getReference("Produk")
        val firebaseRecyclerAdapter = object: FirebaseRecyclerAdapter<Produk, ViewholderBeranda>(
            Produk::class.java,
            R.layout.card_beranda,
            ViewholderBeranda::class.java,
            query
        ) {
            override fun populateViewHolder(viewHolder:ViewholderBeranda, model:Produk, position:Int) {
                viewHolder.setDetails(activity!!.applicationContext, model.nama_produk, model.harga_produk, model.deskripsi, model.gambar)
            }
            override fun onCreateViewHolder(parent:ViewGroup, viewType:Int):ViewholderBeranda {
                val viewHolder = super.onCreateViewHolder(parent, viewType)
                viewHolder.setOnClickListener(object: ViewholderBeranda.ClickListener {
                    override fun onItemClick(view:View, position:Int) {
                        val namaBeranda = view.findViewById(R.id.namaBeranda) as TextView
                        val namaB = namaBeranda.text.toString()
                        val intent = Intent(view.context, ActivityCheckout::class.java)
                        intent.putExtra("nama", namaB)
                        startActivity(intent)
                    }
                    override fun onItemLongClick(view:View, position:Int) {
                    }
                })
                return viewHolder
            }
        }
        mRecyclerView.adapter = firebaseRecyclerAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val inflater = requireActivity().menuInflater
        inflater.inflate(R.menu.customer_beranda, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.pesanan) {
            val intent = Intent(activity, ActivityPesanan::class.java)
            startActivity(intent)
            return true
        } else if (id == R.id.kontak) {
            val intent = Intent(activity, ActivityKontak::class.java)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}