package com.example.gtn

import android.app.Activity
import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.os.Environment
import android.util.Base64
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.exifinterface.media.ExifInterface
import java.io.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

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


    // 대칭키, key IV값. (서버에서 받아옴)
    companion object {
        const val SECRET_KEY = "ABCDEFGH12345678"
        const val SECRET_IV = "1234567812345678"
    }


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
        var access_token = intent.getStringExtra("access_token")

        var filePath = Environment.getExternalStorageDirectory().absolutePath + "/DCIM/Camera"
        imageFiles = File(filePath).listFiles()

        var imagePath = filePath + "/" + imageFiles!![imageNum].name
        //Log.d("log", "imagePath: $imagePath")

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
                // 스위치를 켠 경우 비식별화
                // 해당 메소드 호출
                var enBitmap = BitmapFactory.decodeFile(imagePath)//photo.absolutePath)

                imageView.setImageBitmap(enBitmap)

                Toast.makeText(this, "switch is on", Toast.LENGTH_SHORT).show()
            }
            else{
                // 스위치를 끈 경우 비식별처리 해제
                // 해당 메소드 호출
                var deBitmap = BitmapFactory.decodeFile(imagePath)//photo.absolutePath)
                var decrypted = getHiddenData(imagePath, "data").decryptCBC()//photo).decryptCBC()

                val list = decrypted.split("||")

                //복호화한 메타데이터를 화면에 그림
                for(i in list.indices step 3){
                    var reImage = StringToBitmap(list[i])
                    var x = list[i+1].toInt()
                    var y = list[i+2].toInt()

                    deBitmap = overlay(deBitmap, reImage, x, y)
                }
                imageView.setImageBitmap(deBitmap)

                Toast.makeText(this, "switch is off", Toast.LENGTH_SHORT).show()

            }
        }

        btnPre.setOnClickListener {
            if(imageNum == 0) imageNum = imageFiles!!.size - 1
            imageView.scaleType = ImageView.ScaleType.FIT_CENTER
            bitmap = BitmapFactory.decodeFile(imageFiles!![--imageNum].toString())
            viewName.text = imageFiles!![imageNum].name
            imageView.setImageBitmap(bitmap)
        }

        // 공유버튼?
        btnShare.setOnClickListener {
            // 에뮬레이터로만 돌릴거면 화면 하나 만들어서 인텐트로 띄우기
            // 실제 휴대폰에 사용할거면 다른 앱과 연결하는 화면 띄우기
            /*
            val shareIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, ) // 변경된 이미지
                type = "image/jpeg"
            }
            startActivity(Intent.createChooser(shareIntent, "idimage"))

             */
        }

        // 편집버튼?
        btnEdit.setOnClickListener {
            //
        }

        // When remove button clicked
        btnRemove.setOnClickListener {
            // 파일을 개인정보파일 DB에서 제거 -> SecondActivity에서 진행
            // setResult를 통해 제거된 사진파일 이름 혹은 imageNum을 SecondActivity로 전송
            var outIntent = Intent(applicationContext, SecondActivity::class.java)
            outIntent.putExtra("removedItem", imageFiles!![imageNum].name)
            setResult(Activity.RESULT_OK, outIntent)
            finish() // 인텐트(ThirdActivity) 종료
        }

        btnNext.setOnClickListener {
            if(imageNum == (imageFiles!!.size - 1)) imageNum = 0
            imageView.scaleType = ImageView.ScaleType.FIT_CENTER
            bitmap = BitmapFactory.decodeFile(imageFiles!![++imageNum].toString())
            viewName.text = imageFiles!![imageNum].name
            imageView.setImageBitmap(bitmap)
        }
    }

    //이미지 크롭
    private fun imageCrop(bitmap: Bitmap, x1:Int, y1:Int, AreaWidth:Int, AreaHeight:Int): String {
        var crop = BitmapToString(Bitmap.createBitmap(bitmap, x1, y1, AreaWidth, AreaHeight))

        return crop
    }

    // * CBC 암호화 메서드
    private fun String.encryptCBC(): String {
        val iv = IvParameterSpec(SECRET_IV.toByteArray())
        val keySpec = SecretKeySpec(SECRET_KEY.toByteArray(), "AES")    /// 키
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")     //싸이퍼
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv)       // 암호화 모드
        val crypted = cipher.doFinal(this.toByteArray())
        val encodedByte = Base64.encode(crypted, Base64.DEFAULT)

        return String(encodedByte)
    }


    // * CBC 복호화 메서드
    private fun String.decryptCBC(): String {
        var decodedByte: ByteArray = Base64.decode(this, Base64.DEFAULT)
        val iv = IvParameterSpec(SECRET_IV.toByteArray())
        val keySpec = SecretKeySpec(SECRET_KEY.toByteArray(), "AES")
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(Cipher.DECRYPT_MODE, keySpec, iv)       // 복호화 모드
        val output = cipher.doFinal(decodedByte)

        return String(output)
    }

    // 메타데이터 삽입 메서드
    private fun setHiddenData(imagePath: String, Tag: String, data:String) {
        var exif : ExifInterface? = null
        try {
            exif = ExifInterface(imagePath)
        } catch (e : IOException) {
            e.printStackTrace()
        }
        when(Tag){
            "data" -> exif?.setAttribute(ExifInterface.TAG_USER_COMMENT, data)//data
            "type" -> exif?.setAttribute(ExifInterface.TAG_IMAGE_DESCRIPTION, data)//문서 타입
            "flag" -> exif?.setAttribute(ExifInterface.TAG_SOFTWARE, data)//알고리즘 처리 여부
        }

        exif?.saveAttributes()
    }

    // 메타데이터 추출 메서드(개인정보)
    private fun getHiddenData(imagePath: String, Tag: String) : String {
        var exif : ExifInterface? = null
        try {
            exif = ExifInterface(imagePath)
        } catch (e : IOException) {
            e.printStackTrace()
        }
        var meta : String = ""
        when(Tag){
            "data" -> meta = exif?.getAttribute(ExifInterface.TAG_USER_COMMENT).toString()//data
            "type" -> meta = exif?.getAttribute(ExifInterface.TAG_IMAGE_DESCRIPTION).toString()//문서 타입
            "flag" -> meta = exif?.getAttribute(ExifInterface.TAG_SOFTWARE).toString()//알고리즘 처리 여부
        }

        return meta
    }


    // Bitmap -> String
    private fun BitmapToString(bitmap: Bitmap): String {//String {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val b = stream.toByteArray()

        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    // String -> Bitmap
    private fun StringToBitmap(string:String): Bitmap {//String {
        val imageBytes = Base64.decode(string,0)
        val image= BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

        return image
    }

    //겹쳐 그리기
    private fun overlay(bmp1: Bitmap, bmp2: Bitmap, x:Int, y:Int): Bitmap? {
        val bmOverlay = Bitmap.createBitmap(bmp1.width, bmp1.height, bmp1.config)
        val canvas = Canvas(bmOverlay)

        canvas.drawBitmap(bmp1, Matrix(), null)
        canvas.drawBitmap(bmp2, x.toFloat(), y.toFloat(), null)

        return bmOverlay
    }

    //검정이미지 생성
    private fun createMask(w:Int, h:Int): Bitmap {
        val bmMask = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmMask)
        canvas.drawColor(Color.BLACK)

        return bmMask
    }


    private fun saveFile(imgName:String, newImage: Bitmap) {
        var fos: OutputStream? = null

        val imagesDir =
            Environment.getExternalStorageDirectory().absolutePath+"/DCIM/Camera"
        val image = File(imagesDir, imgName)
        fos = FileOutputStream(image)

        fos?.use {
            newImage.compress(Bitmap.CompressFormat.PNG, 100, it)
        }

    }

}