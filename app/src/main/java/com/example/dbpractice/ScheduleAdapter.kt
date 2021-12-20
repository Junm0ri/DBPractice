package com.example.dbpractice

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter

class ScheduleAdapter(data:OrderedRealmCollection<Schedule>):RealmRecyclerViewAdapter<Schedule,ScheduleAdapter.ViewHolder>(data,true){

    init{
        setHasStableIds(true)
    }

    class ViewHolder(cell: View) : RecyclerView.ViewHolder(cell) {
        val title:TextView=cell.findViewById(android.R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val view=inflater.inflate(android.R.layout.simple_list_item_1,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val schedule: Schedule? = getItem(position)
//        holder.date.text= DateFormat.format("yyyy/MM/dd HH:mm", schedule?.date)
        holder.title.text=schedule?.name
    }

    override fun getItemId(position: Int): Long {
        return getItem(position)?.id ?:0
    }

}