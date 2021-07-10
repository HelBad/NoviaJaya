package com.example.noviajaya.customer.aktivitas

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.noviajaya.R
import com.example.noviajaya.model.User
import com.google.firebase.database.*

class FragmentAktivitas : Fragment() {
    lateinit var aktivitas1: RelativeLayout
    lateinit var aktivitas2: RelativeLayout
    lateinit var aktivitas3: RelativeLayout
    lateinit var aktivitas4: RelativeLayout
    lateinit var aktivitas5: RelativeLayout
    lateinit var databaseReference: DatabaseReference
    lateinit var id_user: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.customer_fragment_aktivitas, container, false)
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        aktivitas1 = view!!.findViewById<View>(R.id.aktivitas1) as RelativeLayout
        aktivitas2 = view!!.findViewById<View>(R.id.aktivitas2) as RelativeLayout
        aktivitas3 = view!!.findViewById<View>(R.id.aktivitas3) as RelativeLayout
        aktivitas4 = view!!.findViewById<View>(R.id.aktivitas4) as RelativeLayout
        aktivitas5 = view!!.findViewById<View>(R.id.aktivitas5) as RelativeLayout

        val actionBar = activity!!.findViewById(R.id.toolbarAktivitas) as Toolbar
        (activity as AppCompatActivity).setSupportActionBar(actionBar)

        databaseReference = FirebaseDatabase.getInstance().reference
        val query = databaseReference.child("User").orderByChild("username")
            .equalTo(activity!!.intent.getStringExtra("username"))
        query.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                if (datasnapshot != null) {
                    for (snapshot1 in datasnapshot.children) {
                        val allocation = snapshot1.getValue(User::class.java)
                        id_user = allocation!!.id_user
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })

        aktivitas1.setOnClickListener {
            val intent = Intent(activity, ActivityPesanan::class.java)
            intent.putExtra("id_user", id_user)
            startActivity(intent)
        }
    }
}