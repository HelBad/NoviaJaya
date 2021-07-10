package com.example.noviajaya.customer.beranda

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.example.noviajaya.R
import com.example.noviajaya.model.Pesanan
import com.example.noviajaya.model.Produk
import com.example.noviajaya.model.User
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class ActivityCheckout : AppCompatActivity() {
    lateinit var databaseReference: DatabaseReference
    lateinit var imgProdukCheckout: ImageView
    lateinit var namaProdukCheckout: TextView
    lateinit var deskripsiCheckout: TextView
    lateinit var namaCheckout: TextView
    lateinit var emailCheckout: TextView
    lateinit var tanggalCheckout: TextView
    lateinit var waktuCheckout: TextView
    lateinit var lokasiCheckout: EditText
    lateinit var jumlahCheckout: EditText
    lateinit var totalCheckout: TextView
    lateinit var cekTotalCheckout: TextView
    lateinit var btnCheckout: Button
    lateinit var biaya: String
    lateinit var id_user: String
    var id_pesanan = 0
    var totalBayar = 0

    @SuppressLint("NewApi")
    var formatTgl = SimpleDateFormat("dd MMM YYYY")
    var formatWaktu = SimpleDateFormat("hh:mm aa")
    val pesanan = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        imgProdukCheckout = findViewById(R.id.imgProdukCheckout)
        namaProdukCheckout = findViewById(R.id.namaProdukCheckout)
        deskripsiCheckout = findViewById(R.id.deskripsiCheckout)
        namaCheckout = findViewById(R.id.namaCheckout)
        emailCheckout = findViewById(R.id.emailCheckout)
        tanggalCheckout = findViewById(R.id.tanggalCheckout)
        waktuCheckout = findViewById(R.id.waktuCheckout)
        lokasiCheckout = findViewById(R.id.lokasiCheckout)
        jumlahCheckout = findViewById(R.id.jumlahCheckout)
        totalCheckout = findViewById(R.id.totalCheckout)
        cekTotalCheckout = findViewById(R.id.cekTotalCheckout)
        btnCheckout = findViewById(R.id.btnCheckout)

        tanggalCheckout.setOnClickListener {
            val datePesan = DatePickerDialog(
                this,
                { view, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(Calendar.YEAR, year)
                    selectedDate.set(Calendar.MONTH, month)
                    selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    tanggalCheckout.text = formatTgl.format(selectedDate.time)
                },
                pesanan.get(Calendar.YEAR),
                pesanan.get(Calendar.MONTH),
                pesanan.get(Calendar.DAY_OF_MONTH)
            )
            datePesan.show()
        }

        waktuCheckout.setOnClickListener {
            val timePesan = TimePickerDialog(this, { view, hourOfDay, minute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTime.set(Calendar.MINUTE, minute)
                waktuCheckout.text = formatWaktu.format(selectedTime.time)
            }, pesanan.get(Calendar.HOUR_OF_DAY), pesanan.get(Calendar.MINUTE), false)
            timePesan.show()
        }

        databaseReference = FirebaseDatabase.getInstance().reference
        val queryProduk = databaseReference.child("Produk").orderByChild("nama_produk")
            .equalTo(intent.getStringExtra("nama_produk"))
        queryProduk.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                if (datasnapshot != null) {
                    for (snapshot1 in datasnapshot.children) {
                        val allocation = snapshot1.getValue(Produk::class.java)
                        namaProdukCheckout.text = allocation!!.nama_produk
                        deskripsiCheckout.text = allocation.deskripsi
                        Picasso.get().load(allocation.gambar).into(imgProdukCheckout)
                        biaya = allocation.harga_produk
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

        databaseReference = FirebaseDatabase.getInstance().reference
        val queryUser = databaseReference.child("User").orderByChild("id_user")
            .equalTo(intent.getStringExtra("id_user"))
        queryUser.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                if (datasnapshot != null) {
                    for (snapshot1 in datasnapshot.children) {
                        val allocation = snapshot1.getValue(User::class.java)
                        namaCheckout.text = allocation!!.nama
                        emailCheckout.text = allocation.email
                        id_user = allocation.id_user
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

        cekTotalCheckout.setOnClickListener {
            totalBayar = jumlahCheckout.text.toString().toInt() * biaya.toInt()
            var formatter: NumberFormat = DecimalFormat("#,###")
            totalCheckout.text = "Rp. " + formatter.format(totalBayar) + ",00"
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Pesanan")
        databaseReference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    id_pesanan = (snapshot.childrenCount.toInt())
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })

        btnCheckout.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view: View) {
                addData()
            }
        })
    }

    private fun addData() {
        val id_pesanan = "Psn" + id_pesanan.toString().trim()
        val id_user = id_user.trim()
        val nama_produk = namaProdukCheckout.text.toString().trim()
        val waktu = waktuCheckout.text.toString().trim()
        val tanggal = tanggalCheckout.text.toString().trim()
        val lokasi = lokasiCheckout.text.toString().trim()
        val jumlah = jumlahCheckout.text.toString().trim()
        val total = totalBayar.toString().trim()
        val status = "Menunggu Konfirmasi Penjual".trim()

        if(!TextUtils.isEmpty(waktu) && !TextUtils.isEmpty(tanggal) && !TextUtils.isEmpty(lokasi)
            && !TextUtils.isEmpty(jumlah) && !TextUtils.isEmpty(total)) {
            val add = Pesanan(id_pesanan, id_user, nama_produk, waktu, tanggal, lokasi, jumlah, total, status)
            databaseReference.child(status).child(id_pesanan).setValue(add)
            Toast.makeText(this@ActivityCheckout, "Berhasil Dipesan", Toast.LENGTH_LONG).show()
            finish()
        } else {
            Toast.makeText(this@ActivityCheckout, "Lengkapi Data", Toast.LENGTH_LONG).show()
        }
    }
}