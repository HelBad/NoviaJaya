package com.example.noviajaya

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.annotation.NonNull
import com.example.noviajaya.model.Akun
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.text.SimpleDateFormat
import java.util.*

class ActivitySignup : AppCompatActivity() {
    lateinit var textNama: EditText
    lateinit var textEmail: EditText
    lateinit var textUsername: EditText
    lateinit var textPassword: EditText
    lateinit var textJenisKelamin: TextView
    lateinit var spinnerJenisKelamin: Spinner
    lateinit var textTanggal: TextView
    lateinit var textAlamat: EditText
    lateinit var textTelp: EditText
    lateinit var textLevel: TextView
    lateinit var textimgAkun: TextView
    lateinit var imgAkun: ImageView
    lateinit var btnSignup: Button
    lateinit var databaseReference: DatabaseReference
    lateinit var storageReference: StorageReference

    var id_user = 0
    lateinit var uri: Uri
    var url: Uri? = null
    var formatTanggal = SimpleDateFormat("dd MMM YYYY")
    val kalender = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        textNama = findViewById(R.id.textNama)
        textEmail = findViewById(R.id.textEmail)
        textUsername = findViewById(R.id.textUsername)
        textPassword = findViewById(R.id.textPassword)
        textJenisKelamin = findViewById(R.id.textJenisKelamin)
        spinnerJenisKelamin = findViewById(R.id.spinnerJenisKelamin)
        textTanggal = findViewById(R.id.textTanggal)
        textAlamat = findViewById(R.id.textAlamat)
        textTelp = findViewById(R.id.textTelp)
        textLevel = findViewById(R.id.textLevel)
        textimgAkun = findViewById(R.id.textimgAkun)
        imgAkun = findViewById(R.id.imgAkun)
        btnSignup = findViewById(R.id.btnSignup)

        val gender = arrayOf("Laki-laki", "Perempuan")
        spinnerJenisKelamin.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, gender)
        spinnerJenisKelamin.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                textJenisKelamin.text = "Pilih Jenis Kelamin Anda"
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                textJenisKelamin.text = gender[position]
            }
        }

        textTanggal.setOnClickListener {
            val tanggalLahir = DatePickerDialog(this, DatePickerDialog.OnDateSetListener {
                    view, year, month, dayOfMonth -> val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.YEAR, year)
                selectedDate.set(Calendar.MONTH, month)
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                textTanggal.text = formatTanggal.format(selectedDate.time)
            }, kalender.get(Calendar.YEAR), kalender.get(Calendar.MONTH), kalender.get(Calendar.DAY_OF_MONTH))
            tanggalLahir.show()
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Pengguna")
        databaseReference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    id_user = (snapshot.childrenCount.toInt())
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
        btnSignup.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view: View) {
                addData()
            }
        })

        storageReference = FirebaseStorage.getInstance().getReference("Pengguna")
        imgAkun.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK) {
            if(requestCode == 0) {
                uri = data!!.data!!
                var mStorage = storageReference.child(id_user.toString())
                try {
                    mStorage.putFile(uri).addOnFailureListener {}.addOnSuccessListener {
                    mStorage.downloadUrl.addOnCompleteListener { taskSnapshot ->
                        url = taskSnapshot.result
                        val bitmap = BitmapDrawable(MediaStore.Images.Media.getBitmap(contentResolver, uri))
                        imgAkun.setImageDrawable(bitmap)
                        textimgAkun.text = url.toString()
                        Toast.makeText(this, "Foto berhasil di unggah", Toast.LENGTH_SHORT).show()
                    }}
                } catch(ex:Exception) {
                    Toast.makeText(this, "Foto gagal di unggah", Toast.LENGTH_SHORT).show()
                }
            }}
    }

    private fun addData() {
        val id = id_user.toString().trim()
        val nama = textNama.text.toString().trim()
        val email = textEmail.text.toString().trim()
        val username = textUsername.text.toString().trim()
        val password = textPassword.text.toString().trim()
        val jenis_kelamin = textJenisKelamin.text.toString().trim()
        val tanggal_lahir = textTanggal.text.toString().trim()
        val alamat = textAlamat.text.toString().trim()
        val telp = textTelp.text.toString().trim()
        val level = textLevel.text.toString().trim()
        val foto = textimgAkun.text.toString().trim()

        if(!TextUtils.isEmpty(nama) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)
            && !TextUtils.isEmpty(jenis_kelamin) && !TextUtils.isEmpty(tanggal_lahir) && !TextUtils.isEmpty(alamat)
            && !TextUtils.isEmpty(telp) && !TextUtils.isEmpty(level) && !TextUtils.isEmpty(foto)) {
            val add = Akun(id, nama, email, username, password, jenis_kelamin, tanggal_lahir, alamat, telp, level, foto)
            databaseReference.child(id_user.toString()).setValue(add)
            Toast.makeText(this@ActivitySignup, "Data Terkirim", Toast.LENGTH_LONG).show()
            finish()
        } else {
            Toast.makeText(this@ActivitySignup, "Lengkapi Data", Toast.LENGTH_LONG).show()
        }
    }
}