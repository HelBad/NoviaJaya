package com.example.noviajaya.customer.beranda

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.denzcoskun.imageslider.models.SlideModel
import com.example.noviajaya.R

class FragmentBeranda : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.customer_fragment_beranda, container, false)
    }

    lateinit var imageSlider: com.denzcoskun.imageslider.ImageSlider

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val actionBar = activity!!.findViewById(R.id.toolbarBeranda) as Toolbar
        (activity as AppCompatActivity).setSupportActionBar(actionBar)

        imageSlider = activity!!.findViewById(R.id.imgSlider)

        val slideModels = arrayListOf<SlideModel>()
        slideModels.add(SlideModel(R.drawable.sampel1))
        slideModels.add(SlideModel(R.drawable.sampel2))
        imageSlider.setImageList(slideModels, true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val inflater = activity!!.menuInflater
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