package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapters.EventsAdminAdapter
import com.example.myapplication.DataClasses.Event
import com.example.myapplication.DataClasses.Hotel
import com.example.myapplication.databinding.ActivityAddHotelsBinding
import com.example.myapplication.databinding.ActivityViewAllEventsAdminBinding
import com.example.myapplication.databinding.ActivityViewAllHotelsAdminBinding
import com.google.firebase.database.*

class viewAllEventsAdmin : AppCompatActivity() {

    private lateinit var binding: ActivityViewAllEventsAdminBinding
    private lateinit var databaseRef: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private var mList = ArrayList<Event>()
    private lateinit var adapter: EventsAdminAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewAllEventsAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseRef = FirebaseDatabase.getInstance().reference.child("Events")

        recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this);

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mList.clear()
                for ( snapshot in snapshot.children){
                    val data = snapshot.getValue(Event::class.java)!!
                    if( data != null){
                        mList.add(data)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@viewAllEventsAdmin, error.message, Toast.LENGTH_SHORT).show()
            }
        })

        adapter = EventsAdminAdapter(mList)
        recyclerView.adapter = adapter


        //Setting onclick on recyclerView each item
        adapter.setOnItemClickListner(object: EventsAdminAdapter.onItemClickListner {
            override fun onItemClick(position: Int) {
                intent = Intent(applicationContext, UpdateEvent::class.java).also {
                    it.putExtra("name", mList[position].name)
                    it.putExtra("id", mList[position].id)
                    it.putExtra("description", mList[position].description)
                    it.putExtra("location", mList[position].location)
                    startActivity(it)
                }
            }
        })

        binding.btnAdd.setOnClickListener {
            intent = Intent(applicationContext, AddEvent::class.java)
            startActivity(intent)
        }

    }
}