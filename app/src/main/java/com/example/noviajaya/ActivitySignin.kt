package com.example.noviajaya

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.noviajaya.model.User
import com.google.firebase.database.*

class ActivitySignin : AppCompatActivity(), View.OnClickListener {
    lateinit var alertDialog: AlertDialog.Builder
    lateinit var btnSignin: Button
    lateinit var textSignup: TextView
    lateinit var textUsername: EditText
    lateinit var textPassword: EditText
    lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        alertDialog = AlertDialog.Builder(this)
        textUsername = findViewById(R.id.textUsername)
        textPassword = findViewById(R.id.textPassword)
        btnSignin = findViewById(R.id.btnSignin)
        textSignup = findViewById(R.id.textSignup)
        btnSignin.setOnClickListener(this)
        databaseReference = FirebaseDatabase.getInstance().reference

        textSignup.setOnClickListener {
            val intent = Intent(this, ActivitySignup::class.java)
            startActivity(intent)
        }
    }

    override fun onClick(view: View) {
        if (view === btnSignin) {
            signinAkun()
        }
    }

    private fun signinAkun() {
        val query = databaseReference.child("Pengguna").orderByChild("username").equalTo(textUsername.text.toString().trim())
        query.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (user in dataSnapshot.children) {
                        val usersBean = user.getValue(User::class.java)
                        if (usersBean!!.password == textPassword.text.toString().trim()) {
                            if(usersBean.level == "admin") {
                                Toast.makeText(this@ActivitySignin, "Masuk sebagai Admin", Toast.LENGTH_LONG).show()
                                val intent = Intent(applicationContext, com.example.noviajaya.admin.ActivityUtama::class.java)
                                intent.putExtra("username", textUsername.text.toString())
                                startActivity(intent)
                                finish()
                            } else if(usersBean.level == "customer") {
                                Toast.makeText(this@ActivitySignin, "Masuk sebagai customer", Toast.LENGTH_LONG).show()
                                val intent = Intent(applicationContext, com.example.noviajaya.customer.ActivityUtama::class.java)
                                intent.putExtra("username", textUsername.text.toString())
                                startActivity(intent)
                                finish()
                            }
                        } else {
                            Toast.makeText(this@ActivitySignin, "Password salah", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    Toast.makeText(this@ActivitySignin, "Pengguna tidak ditemukan", Toast.LENGTH_LONG).show()
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    override fun onBackPressed() {
        Toast.makeText(this@ActivitySignin, "Back is Clicked", Toast.LENGTH_SHORT).show()
        alertDialog.setTitle("Close Application")
        alertDialog.setMessage("Do you want to close the application ?")
            .setCancelable(false)
            .setPositiveButton("YES", object: DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, id:Int) {
                    finishAffinity()
                }
            })
            .setNegativeButton("NO", object: DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, id:Int) {
                    dialog.cancel()
                }
            }).create().show()
    }
}