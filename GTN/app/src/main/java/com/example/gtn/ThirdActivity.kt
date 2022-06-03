package com.example.gtn

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class ThirdActivity : AppCompatActivity() {

    lateinit var viewName : TextView
    lateinit var switch: Switch
    lateinit var imageView: ImageView
    lateinit var btnPre: ImageButton
    lateinit var btnShare: ImageButton
    lateinit var btnEdit: ImageButton
    lateinit var btnRemove: ImageButton
    lateinit var btnNext: ImageButton

    lateinit var bitmap: Bitmap

    var imageFiles: Array<File>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.viewpage)

        viewName = findViewById(R.id.ViewPage_textviewName)
        switch = findViewById(R.id.ViewPage_switch)
        imageView = findViewById(R.id.ViewPage_imageView)
        btnPre = findViewById(R.id.ViewPage_btnPre)
        btnShare = findViewById(R.id.ViewPage_btnShare)
        btnEdit = findViewById(R.id.ViewPage_btnEdit)
        btnRemove = findViewById(R.id.ViewPage_btnRemove)
        btnNext = findViewById(R.id.ViewPage_btnNext)


        var intent = intent
        var imageNum = intent.getIntExtra("Num", -1) // -> number

        imageFiles = File(Environment.getExternalStorageDirectory().absolutePath + "/DCIM/Camera").listFiles()

        // Set ImageView
        imageView.scaleType = ImageView.ScaleType.FIT_CENTER
        // 이미지의 긴 쪽을 imageView에 맞춰서 이미지가 크게 보이도록 하기
        bitmap = BitmapFactory.decodeFile(imageFiles!![imageNum].toString())
        imageView.setImageBitmap(bitmap)

        // Set TextView with file's name
        viewName.text = imageFiles!![imageNum].name

        // When Switch off
        // 마스킹 해제해서 화면에 출력하기
        switch.setOnClickListener {
            if(switch.isChecked){
                // 스위치를 켠 경우 암호화
                Toast.makeText(this, "switch is on", Toast.LENGTH_SHORT).show()
            }
            else{
                // 스위치를 끈 경우 복호화
            }
        }

        btnPre.setOnClickListener {
            if(imageNum == 0) imageNum = imageFiles!!.size - 1
            imageView.scaleType = ImageView.ScaleType.FIT_CENTER
            bitmap = BitmapFactory.decodeFile(imageFiles!![--imageNum].toString())
            imageView.setImageBitmap(bitmap)
        }

        // 공유버튼?
        btnShare.setOnClickListener {
            // 에뮬레이터로만 돌릴거면 화면 하나 만들어서 인텐트로 띄우기
            // 실제 휴대폰에 사용할거면 다른 앱과 연결하는 화면 띄우기
            // Intent?
        }

        // 편집버튼?
        btnEdit.setOnClickListener {
            //
        }

        // When remove button clicked
        btnRemove.setOnClickListener {
            // 파일을 개인정보파일 리스트에서 제거
            // setResult를 통해 제거된 사진파일 이름 혹은 imageNum을 SecondActivity로 전송
            finish() // 인텐트(ThirdActivity) 종료
        }

        btnNext.setOnClickListener {
            if(imageNum == (imageFiles!!.size - 1)) imageNum = 0
            imageView.scaleType = ImageView.ScaleType.FIT_CENTER
            bitmap = BitmapFactory.decodeFile(imageFiles!![++imageNum].toString())
            imageView.setImageBitmap(bitmap)
        }
    }
}