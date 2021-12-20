package com.example.dbpractice

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.lang.reflect.Array.get
import java.util.*

open class Schedule:RealmObject() {
    @PrimaryKey
    var id:Long=0
//    var date:Date=Date()
    var title:String =""
    var detail:String=""
//    リスト（配列）ではなく文字列でデータを管理する（後でsplit,parseIntする）
}
