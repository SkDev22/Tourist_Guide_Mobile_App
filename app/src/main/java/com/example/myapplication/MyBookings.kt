package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapters.MyBookingsAdapter
import com.example.myapplication.DataClasses.Event
import com.example.myapplication.DataClasses.Tour
import com.example.myapplication.databinding.ActivityMyBookingsBinding
import com.example.myapplication.databinding.ActivityViewAllEventsAdminBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MyBookings : AppCompatActivity() {

    private lateinit var binding: ActivityMyBookingsBinding
    private lateinit var databaseRef: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private var mList = ArrayList<Tour>()
    private lateinit var adapter: MyBookingsAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyBookingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("Booking").child(uid)

        recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this);

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mList.clear()
                for ( snapshot in snapshot.children){
                    val data = snapshot.getValue(Tour::class.java)!!
                    if( data != null){
                        mList.add(data)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MyBookings, error.message, Toast.LENGTH_SHORT).show()
            }
        })

        adapter = MyBookingsAdapter(mList)
        recyclerView.adapter = adapter


        //Setting onclick on recyclerView each item
        adapter.setOnItemClickListner(object: MyBookingsAdapter.onItemClickListner {
            override fun onItemClick(position: Int) {
                intent = Intent(applicationContext, UpdateBooking::class.java).also {
                    it.putExtra("destination", mList[position].destination)
                    it.putExtra("id", mList[position].id)
                    it.putExtra("nAdults", mList[position].nAdults)
                    it.putExtra("nChildren", mList[position].nChildren)
                    it.putExtra("vType", mList[position].vType)
                    it.putExtra("nDays", mList[position].nDays)
                    it.putExtra("tot", mList[position].tot)
                    startActivity(it)
                }
            }
        })
    }
}