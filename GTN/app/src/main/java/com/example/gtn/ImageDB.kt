package com.example.gtn

import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.kotlin.where

open class ImageDB: RealmObject() {
    @PrimaryKey
    var pk = ""
    var imgName = ""
    var imgType = "" // id, license, registration
    var personalInfo = ""
    var shootingTime = ""
}

open class Timestamp: RealmObject() {
    @PrimaryKey
    var date = "" // YYYY-MM-DD-HH-mm-SS
}

class RealmManager(val realm: Realm) {
    fun find(name: String): ImageDB? {
        return realm.where(ImageDB::class.java).equalTo("imgName", name).findFirst()
    }

    fun findAll(): List<ImageDB>{
        return realm.where(ImageDB::class.java).findAll()
    }

    fun create(curdata: ImageDB){
        realm.beginTransaction()

        val data = realm.createObject(ImageDB::class.java, curdata.pk)
        //data.pk = curdata.imgName + curdata.shootingTime
        data.imgName = curdata.imgName
        data.imgType = curdata.imgType
        data.personalInfo = curdata.personalInfo
        data.shootingTime = curdata.shootingTime

        realm.commitTransaction()
    }

    fun update(name: String, curdata: ImageDB){
        realm.beginTransaction()

        val data = find(name)
        data?.pk = curdata.imgName + curdata.shootingTime
        data?.imgName = curdata.imgName
        data?.imgType = curdata.imgType
        data?.personalInfo = curdata.personalInfo
        data?.shootingTime = curdata.shootingTime

        realm.commitTransaction()
    }

    fun deleteByName(name: String){
        realm.beginTransaction()

        val data = realm.where<ImageDB>().equalTo("imgName", name).findAll()
        data.deleteAllFromRealm()

        realm.commitTransaction()
    }
}