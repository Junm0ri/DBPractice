package com.example.dbpractice

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dbpractice.databinding.ActivityMainBinding
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where

class MainActivity : AppCompatActivity() {

    private lateinit var realm:Realm
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        realm=Realm.getDefaultInstance()

        binding= ActivityMainBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        binding.list.layoutManager=LinearLayoutManager(this)
        val schedules=realm.where<Schedule>().findAll()
        val adapter=ScheduleAdapter(schedules)
        binding .list.adapter=adapter

        binding.button.setOnClickListener {
            binding.textView.text="tapped"
            realm.executeTransaction { db:Realm->
                val maxId=db.where<Schedule>().max("id")
                val nextId=(maxId?.toLong() ?:0L)+1L
                val schedule=db.createObject<Schedule>(nextId)
//                val date="aaa"
                schedule.title="Title"
                schedule.detail="Detail"
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}