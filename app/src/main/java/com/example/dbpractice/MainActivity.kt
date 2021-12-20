package com.example.dbpractice

import android.app.Activity
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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

        //ADDボタンを押した時の処理
        binding.button.setOnClickListener {
            binding.textView.text="tapped"
            realm.executeTransaction { db:Realm->
                val maxId=db.where<Schedule>().max("id")
                val nextId=(maxId?.toLong() ?:0L)+1L
                val schedule=db.createObject<Schedule>(nextId)
                schedule.name=binding.NameEdit.text.toString()
                schedule.Kansei=binding.NumberEdit.text.toString()
            }
        }

        //Deleteボタンを押した時の処理
        binding.DeleteButton.setOnClickListener {
            realm.executeTransaction { db:Realm->
                db.where<Schedule>()?.findFirst()?.deleteFromRealm()
            }
        }

        binding.InputButton.setOnClickListener {
            val myedit = EditText(this)
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("文字を入力してください")
            dialog.setView(myedit)
            dialog.setPositiveButton("OK", DialogInterface.OnClickListener { _, _ ->
                val userText = myedit.getText().toString()
                Toast.makeText(this, "$userText と入力しました", Toast.LENGTH_SHORT).show()
                //DBに追加
                realm.executeTransaction { db:Realm->
                    val maxId=db.where<Schedule>().max("id")
                    val nextId=(maxId?.toLong() ?:0L)+1L
                    val schedule=db.createObject<Schedule>(nextId)
                    schedule.name=userText
                    schedule.Kansei=binding.NumberEdit.text.toString()
                }
            })
            dialog.setNegativeButton("キャンセル", null)
            dialog.show()

        }

        //アダプターのアイテムを押した時の処理
        adapter.setOnItemClickListener { id ->
            binding.textView.text=id.toString()
            val schedule=realm.where<Schedule>().equalTo("id",id).findFirst()
            val str = schedule?.name+schedule?.Kansei
            binding.textView.text=str
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}