package com.example.gtn

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ImageDB: RealmObject() {
    @PrimaryKey
    var imageName = ""
    var imageType = ""
    var personalData = ""
}

