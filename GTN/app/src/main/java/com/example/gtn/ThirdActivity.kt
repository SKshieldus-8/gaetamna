package com.example.gtn

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ThirdActivity : AppCompatActivity() {

    lateinit var viewName : TextView
    lateinit var imageView: ImageView
    lateinit var btnPre: ImageButton
    lateinit var btnNext: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.viewpage)

        var intent = intent
        var imageId = intent.getIntArrayExtra("arrayImage") // -> null
        var imageNum = intent.getIntExtra("Num", 0) // -> number
        var itemId = intent.getIntExtra("itemId", 0) // -> number

        //viewName = findViewById(R.id.ViewPage_textviewName)
        //viewName.setText(imageNum)

        imageView = findViewById(R.id.ViewPage_imageView)
        imageView.setImageResource(itemId)

        imageView.setOnClickListener {
            Toast.makeText(this, "${imageId?.get(imageNum)}", Toast.LENGTH_SHORT).show()
        }

        btnPre = findViewById(R.id.ViewPage_btnPre)
        btnNext = findViewById(R.id.ViewPage_btnNext)

        btnPre.setOnClickListener {
            if(imageNum == 0) imageNum = imageId!!.size - 1
            imageView.setImageResource(imageId!![imageNum])
        }

        btnNext.setOnClickListener {
            if(imageNum == (imageId!!.size - 1)) imageNum = 0
            imageView.setImageResource(imageId!![imageNum])
        }
    }
}