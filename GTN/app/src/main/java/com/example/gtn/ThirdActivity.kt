package com.example.gtn

import android.app.Activity
import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.os.Environment
import android.util.Base64
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.exifinterface.media.ExifInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    private var SECRET_IV : String = ""
    private var SECRET_KEY : String = ""


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
        //var imageNum = intent.getIntExtra("Num", -1) // -> number
        var imageName = intent.getStringExtra("ImageName")
        var access_token = intent.getStringExtra("access_token")
        var imageNum = -1

        var filePath = Environment.getExternalStorageDirectory().absolutePath + "/DCIM/Camera"
        imageFiles = File(filePath).listFiles()

        for(i in imageFiles!!.indices){
            if(imageFiles!![i].name == imageName) {
                imageNum = i
                break
            }
        }

        var imagePath = "$filePath/$imageName"

        //서버로부터 복호화 key 요청
        var retrofit = Retrofit.Builder()
            .baseUrl("http://43.200.55.96:1337")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var requestkey: RequestKey = retrofit.create(RequestKey::class.java)

        Log.d("Decryp","호출 시작")
        try {
            requestkey.requestkey(access_token).enqueue(object : Callback<dataKey> {
                override fun onFailure(call: Call<dataKey>, t: Throwable) {
                    t.message?.let { it1 -> Log.e("KEY", it1) }
                    var dialog = AlertDialog.Builder(this@ThirdActivity)

                    Log.d("Decryp", "호출 실패")
                }

                override fun onResponse(call: Call<dataKey>, response: Response<dataKey>) {
                    var responseKey = response.body()

                    SECRET_IV = responseKey!!.iv
                    SECRET_KEY = responseKey!!.key
                }
            })
        } catch (e:Exception){
            Log.d("Exception", "예외발생")
        }

        // Set ImageView
        imageView.scaleType = ImageView.ScaleType.FIT_CENTER
        // 이미지의 긴 쪽을 imageView에 맞춰서 이미지가 크게 보이도록 하기
        bitmap = BitmapFactory.decodeFile(imageFiles!![imageNum].toString())
        //bitmap 크기 줄이기.
        var resized = resizePhoto(bitmap)

        imageView.setImageBitmap(resized)
        var shareImage:Bitmap = bitmap

        // Set TextView with file's name
        viewName.text = imageFiles!![imageNum].name

        // When Switch off
        // 마스킹 해제해서 화면에 출력하기
        switch.setOnClickListener {
            if(switch.isChecked){
                // 스위치를 켠 경우 비식별화
                // 해당 메소드 호출
                var enBitmap = BitmapFactory.decodeFile(imagePath)

                var resized = resizePhoto(enBitmap)
                imageView.setImageBitmap(resized)

            }
            else{
                // 스위치를 끈 경우 비식별처리 해제

                var deBitmap = BitmapFactory.decodeFile(imagePath)//photo.absolutePath)
                var decrypted = getHiddenData(imagePath, "data").decryptCBC(SECRET_IV, SECRET_KEY)//photo).decryptCBC()

                val list = decrypted.split("||")

                //복호화한 메타데이터를 화면에 그림
                for(i in list.indices step 3){
                    var reImage = StringToBitmap(list[i])
                    var x = list[i+1].toInt()
                    var y = list[i+2].toInt()

                    deBitmap = overlay(deBitmap, reImage, x, y)
                }
                var resized = resizePhoto(deBitmap)
                imageView.setImageBitmap(resized)
                shareImage = deBitmap

            }
        }

        btnPre.setOnClickListener {
            if(imageNum == 0) imageNum = imageFiles!!.size - 1
            imageView.scaleType = ImageView.ScaleType.FIT_CENTER
            bitmap = BitmapFactory.decodeFile(imageFiles!![--imageNum].toString())
            viewName.text = imageFiles!![imageNum].name
            var resized = resizePhoto(bitmap)
            imageView.setImageBitmap(resized)
        }

        // 공유버튼
        btnShare.setOnClickListener {
            // 에뮬레이터로만 돌릴거면 화면 하나 만들어서 인텐트로 띄우기
            // 실제 휴대폰에 사용할거면 다른 앱과 연결하는 화면 띄우기

            var file: File? = null
            var out: OutputStream? = null

            try{
                file?.createNewFile()
                out = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            }catch (e: Exception){
                Log.d("Exception", "예외발생")
            }finally {
                out?.close()
            }


            val shareIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, file) // 변경된 이미지
                type = "image/jpeg"
            }
            startActivity(Intent.createChooser(shareIntent, "image"))


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
            outIntent.putExtra("type", getHiddenData(imagePath, "type"))
            setResult(Activity.RESULT_OK, outIntent)

            //원본 이미지로 복원 및 저장.
            var deBitmap = BitmapFactory.decodeFile(imagePath)//photo.absolutePath)
            var decrypted = getHiddenData(imagePath, "data").decryptCBC(SECRET_IV, SECRET_KEY)//photo).decryptCBC()
            val list = decrypted.split("||")

            //복호화한 메타데이터를 비트맵에 그림
            for(i in list.indices step 3){
                var reImage = StringToBitmap(list[i])
                var x = list[i+1].toInt()
                var y = list[i+2].toInt()

                deBitmap = overlay(deBitmap, reImage, x, y)
            }

            saveFile(imageFiles!![imageNum].name, deBitmap) //마스킹 이미지 저장(원본에 덮어 씌우기)
            setHiddenData(imagePath, "data", "")
            setHiddenData(imagePath, "type", "")
            setHiddenData(imagePath, "flag", "GTN")

            finish() // 인텐트(ThirdActivity) 종료
        }

        btnNext.setOnClickListener {
            if(imageNum == (imageFiles!!.size - 1)) imageNum = 0
            imageView.scaleType = ImageView.ScaleType.FIT_CENTER
            bitmap = BitmapFactory.decodeFile(imageFiles!![++imageNum].toString())
            viewName.text = imageFiles!![imageNum].name
            var resized = resizePhoto(bitmap)
            imageView.setImageBitmap(resized)
        }
    }

    //이미지 크롭
    private fun imageCrop(bitmap: Bitmap, x1:Int, y1:Int, AreaWidth:Int, AreaHeight:Int): String {
        var crop = BitmapToString(Bitmap.createBitmap(bitmap, x1, y1, AreaWidth, AreaHeight))

        return crop
    }

    // * CBC 암호화 메서드
    private fun String.encryptCBC(SECRET_IV: String, SECRET_KEY:String): String {
        val iv = IvParameterSpec(SECRET_IV.toByteArray())
        val keySpec = SecretKeySpec(SECRET_KEY.toByteArray(), "AES")    /// 키
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")     //싸이퍼
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv)       // 암호화 모드
        val crypted = cipher.doFinal(this.toByteArray())
        val encodedByte = Base64.encode(crypted, Base64.DEFAULT)

        return String(encodedByte)
    }


    // * CBC 복호화 메서드
    private fun String.decryptCBC(SECRET_IV: String, SECRET_KEY:String): String {
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
            Log.d("Exception", "예외발생")
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
            Log.d("Exception", "예외발생")
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

    //이미지 저장
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

    fun resizePhoto(bitmap: Bitmap): Bitmap {
        val w = bitmap.width
        val h = bitmap.height
        val aspRat = w / h
        val W = w/2
        val H = h/2
        val b = Bitmap.createScaledBitmap(bitmap, W, H, false)

        return b
    }

}