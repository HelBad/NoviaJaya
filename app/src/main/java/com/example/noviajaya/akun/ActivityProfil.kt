package com.example.noviajaya.akun

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextUtils
import android.widget.*
import com.example.noviajaya.R
import com.example.noviajaya.model.User
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class ActivityProfil : AppCompatActivity() {
    lateinit var fotoProfil: ImageView
    lateinit var fototextProfil: TextView
    lateinit var idProfil: TextView
    lateinit var namaProfil: EditText
    lateinit var usernameProfil: EditText
    lateinit var emailProfil: EditText
    lateinit var passwordProfil: EditText
    lateinit var genderProfil: TextView
    lateinit var tanggalProfil: TextView
    lateinit var alamatProfil: EditText
    lateinit var telpProfil: EditText
    lateinit var levelProfil: TextView
    lateinit var btnProfil: Button
    lateinit var databaseReference: DatabaseReference

    lateinit var uri: Uri
    var url: Uri? = null
    lateinit var storageReference: StorageReference

    @SuppressLint("NewApi")
    var formateDate = SimpleDateFormat("dd MMM YYYY")
    val date = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)

        fotoProfil = findViewById(R.id.fotoProfil)
        fototextProfil = findViewById(R.id.fototextProfil)
        idProfil = findViewById(R.id.idProfil)
        namaProfil = findViewById(R.id.namaProfil)
        usernameProfil = findViewById(R.id.usernameProfil)
        emailProfil = findViewById(R.id.emailProfil)
        passwordProfil = findViewById(R.id.passwordProfil)
        genderProfil = findViewById(R.id.genderProfil)
        tanggalProfil = findViewById(R.id.tanggalProfil)
        alamatProfil = findViewById(R.id.alamatProfil)
        telpProfil = findViewById(R.id.telpProfil)
        levelProfil = findViewById(R.id.levelProfil)
        btnProfil = findViewById(R.id.btnProfil)

        storageReference = FirebaseStorage.getInstance().getReference("User")
        fotoProfil.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
        tanggalProfil.setOnClickListener {
            val date = DatePickerDialog(this, DatePickerDialog.OnDateSetListener {
                view, year, month, dayOfMonth -> val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.YEAR, year)
                selectedDate.set(Calendar.MONTH, month)
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                tanggalProfil.text = formateDate.format(selectedDate.time)
            }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH))
            date.show()
        }

        databaseReference = FirebaseDatabase.getInstance().reference
        val query = databaseReference.child("User").orderByChild("username").equalTo(intent.getStringExtra("username"))
        query.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                if (datasnapshot != null) {
                    for (snapshot1 in datasnapshot.children) {
                        val allocation = snapshot1.getValue(User::class.java)
                        Picasso.get().load(allocation!!.foto).into(fotoProfil)
                        fototextProfil.text = allocation.foto
                        idProfil.text = allocation.id_user
                        namaProfil.text = Editable.Factory.getInstance().newEditable(allocation.nama)
                        usernameProfil.text = Editable.Factory.getInstance().newEditable(allocation.username)
                        emailProfil.text = Editable.Factory.getInstance().newEditable(allocation.email)
                        passwordProfil.text = Editable.Factory.getInstance().newEditable(allocation.password)
                        genderProfil.text = allocation.jenis_kelamin
                        tanggalProfil.text = allocation.tanggal_lahir
                        alamatProfil.text = Editable.Factory.getInstance().newEditable(allocation.alamat)
                        telpProfil.text = Editable.Factory.getInstance().newEditable(allocation.telp)
                        levelProfil.text = allocation.level
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })

        btnProfil.setOnClickListener {
            addData()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == RESULT_OK) {
            if(requestCode == 0) {
                var id = idProfil.text.toString()
                uri = data!!.data!!
                var mStorage = storageReference.child(id)
                try {
                    mStorage.putFile(uri).addOnFailureListener {}.addOnSuccessListener {
                    mStorage.downloadUrl.addOnCompleteListener { taskSnapshot ->
                        url = taskSnapshot.result
                        val bitmap = BitmapDrawable(MediaStore.Images.Media.getBitmap(contentResolver, uri))
                        fotoProfil.setImageDrawable(bitmap)
                        fototextProfil.text = url.toString()
                        Toast.makeText(this, "Foto berhasil di unggah", Toast.LENGTH_SHORT).show()
                    }}
                } catch(ex:Exception) {
                    Toast.makeText(this, "Foto gagal di unggah", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun addData() {
        val id_user = idProfil.text.toString().trim()
        val nama = namaProfil.text.toString().trim()
        val email = emailProfil.text.toString().trim()
        val username = usernameProfil.text.toString().trim()
        val password = passwordProfil.text.toString().trim()
        val jenis_kelamin = genderProfil.text.toString().trim()
        val tanggal_lahir = tanggalProfil.text.toString().trim()
        val alamat = alamatProfil.text.toString().trim()
        val telp = telpProfil.text.toString().trim()
        val level = levelProfil.text.toString().trim()
        val foto = fototextProfil.text.toString().trim()

        if(!TextUtils.isEmpty(nama) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)
                && !TextUtils.isEmpty(jenis_kelamin) && !TextUtils.isEmpty(tanggal_lahir) && !TextUtils.isEmpty(alamat)
                && !TextUtils.isEmpty(telp) && !TextUtils.isEmpty(level) && !TextUtils.isEmpty(foto)) {
            val add = User(id_user, nama, email, username, password, jenis_kelamin, tanggal_lahir, alamat, telp, level, foto)
            databaseReference.child("User").child(id_user).setValue(add)
            Toast.makeText(this@ActivityProfil, "Data Terkirim", Toast.LENGTH_LONG).show()
            finish()
        } else {
            Toast.makeText(this@ActivityProfil, "Lengkapi Data", Toast.LENGTH_LONG).show()
        }
    }
}