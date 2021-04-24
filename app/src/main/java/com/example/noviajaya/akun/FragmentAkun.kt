package com.example.noviajaya.akun

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.noviajaya.ActivitySignin
import com.example.noviajaya.R
import com.example.noviajaya.model.Akun
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

class FragmentAkun : Fragment() {
    lateinit var alertDialog: AlertDialog.Builder
    lateinit var databaseReference: DatabaseReference
    lateinit var namaAkun: TextView
    lateinit var emailAkun: TextView
    lateinit var usernameAkun: TextView
    lateinit var jeniskelaminAkun: TextView
    lateinit var tanggalAkun: TextView
    lateinit var alamatAkun: TextView
    lateinit var telpAkun: TextView
    lateinit var levelAkun: TextView
    lateinit var imgAkun: ImageView
    lateinit var lihatAkun: TextView
    lateinit var btnSignout: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.admin_fragment_akun, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        alertDialog = AlertDialog.Builder(activity!!)
        namaAkun = view!!.findViewById<View>(R.id.namaAkun) as TextView
        emailAkun = view!!.findViewById<View>(R.id.emailAkun) as TextView
        usernameAkun = view!!.findViewById<View>(R.id.usernameAkun) as TextView
        jeniskelaminAkun = view!!.findViewById<View>(R.id.jeniskelaminAkun) as TextView
        tanggalAkun = view!!.findViewById<View>(R.id.tanggalAkun) as TextView
        alamatAkun = view!!.findViewById<View>(R.id.alamatAkun) as TextView
        telpAkun = view!!.findViewById<View>(R.id.telpAkun) as TextView
        levelAkun = view!!.findViewById<View>(R.id.levelAkun) as TextView
        imgAkun = view!!.findViewById<View>(R.id.imgAkun) as ImageView
        lihatAkun = view!!.findViewById<View>(R.id.lihatAkun) as TextView
        btnSignout = view!!.findViewById<View>(R.id.btnSignout) as Button

        databaseReference = FirebaseDatabase.getInstance().reference
        val query = databaseReference.child("Pengguna").orderByChild("username")
            .equalTo(activity!!.intent.getStringExtra("username"))
        query.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                if (datasnapshot != null) {
                    for (snapshot1 in datasnapshot.children) {
                        val allocation = snapshot1.getValue(Akun::class.java)
                        namaAkun.text = allocation!!.nama
                        emailAkun.text = allocation.email
                        usernameAkun.text = allocation.username
                        jeniskelaminAkun.text = allocation.jenis_kelamin
                        tanggalAkun.text = allocation.tanggal_lahir
                        alamatAkun.text = allocation.alamat
                        telpAkun.text = allocation.telp
                        levelAkun.text = allocation.level
                        Picasso.get().load(allocation.foto).into(imgAkun)
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })

        lihatAkun.setOnClickListener {
            val intent = Intent(activity, ActivityProfil::class.java)
            intent.putExtra("username", usernameAkun.text.toString())
            startActivity(intent)
        }

        btnSignout.setOnClickListener {
            alertDialog.setTitle("Keluar Akun")
            alertDialog.setMessage("Apakah anda ingin keluar dari akun ini ?")
                .setCancelable(false)
                .setPositiveButton("YA", object: DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, id:Int) {
                        val intent = Intent(activity, ActivitySignin::class.java)
                        startActivity(intent)
                    }
                })
                .setNegativeButton("TIDAK", object: DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, id:Int) {
                        dialog.cancel()
                    }
                }).create().show()
        }
    }
}