package com.example.gtn

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.os.Environment
import android.util.Base64
import android.util.Log
import android.view.*
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.exifinterface.media.ExifInterface
import androidx.fragment.app.FragmentTransaction
import io.realm.Realm
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

var token: String? = null
var imageFiles: Array<File>? = null
var allFiles:Array<File>? = null
var idcardFiles: Array<File>? = null
var licenseFiles: Array<File>? = null
var registrationFiles: Array<File>? = null

@Suppress("deprecation")
class SecondActivity : AppCompatActivity(), ActionBar.TabListener {
    lateinit var tab1: ActionBar.Tab
    lateinit var tab2: ActionBar.Tab
    lateinit var tab3: ActionBar.Tab
    lateinit var tab4: ActionBar.Tab

    // 대칭키, key IV값. (서버에서 받아옴)
    companion object {
        const val SECRET_KEY = "ABCDEFGH12345678"
        const val SECRET_IV = "1234567812345678"
    }

    var myFrags = arrayOfNulls<MyTabFragment>(4)

    //var realmManagerImage = RealmManager(Realm.getDefaultInstance())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.temppage)

        var intent = intent
        token = intent.getStringExtra("token")

        var bar = this.supportActionBar
        bar!!.navigationMode = ActionBar.NAVIGATION_MODE_TABS

        tab1 = bar.newTab()
        tab1.text = "전체"
        tab1.setTabListener(this)
        bar.addTab(tab1)

        tab2 = bar.newTab()
        tab2.text = "주민등록증"
        tab2.setTabListener(this)
        bar.addTab(tab2)

        tab3 = bar.newTab()
        tab3.text = "운전면허증"
        tab3.setTabListener(this)
        bar.addTab(tab3)

        tab4 = bar.newTab()
        tab4.text = "주민등록등본"
        tab4.setTabListener(this)
        bar.addTab(tab4)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        var mInflater = menuInflater
        mInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.refresh -> {
                var temp: MutableList<File>?  = null
                var tempid: MutableList<File>? = null
                var templicense: MutableList<File>? = null
                var tempregistration: MutableList<File>? = null

                for(i in imageFiles!!.indices){
                    //Log.d("imagefile", imageFiles!![i].absolutePath)
                    val imagePath = imageFiles!![i].absolutePath
                    // 1.서버에 Key, IV 요청
                    //  (access_token을 통해 서버로부터 KEY와 IV를 가져옴)




                    // 2.이미지 flag확인
                    // 3.flag GTN이면 문서 타입 확인해 분류 리스트에 추가.
                    // 4.flag GTN아니면 이미지 통신 실행
                    if(getHiddenData(imagePath, "flag") != "GTN") {
                        //  4-1. retrofit 생성
                        var retrofit = Retrofit.Builder()
                            .baseUrl("http://43.200.55.96:1337/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()

                        var req: RequestInformation = retrofit.create(RequestInformation::class.java)

                        //  4-2. requestbody 객체로 변환
                        val requestFile = RequestBody.create(MediaType.parse("image/png"), imageFiles!![i])
                        val body = MultipartBody.Part.createFormData("file", imageFiles!![i].name, requestFile)

                        //  4-3. 통신 진행
                        try {
                            req.requestInformation(body).enqueue(object : Callback<dataResponse> {
                                override fun onFailure(call: Call<dataResponse>, t: Throwable) {
                                    //실패할 경우
                                    Log.d("retrofitResult", "fail")
                                }

                                override fun onResponse(
                                    call: Call<dataResponse>,
                                    response: Response<dataResponse>
                                ) {
                                    //정상응답이 올경우
                                    Log.d("retrofitResult", "success")
                                    var res = response.body()

                                    if (res != null) {
                                        if (res.result == 1) {
                                            var xCoordinate = mutableListOf<Int>()
                                            var yCoordinate = mutableListOf<Int>()
                                            var AreaWidth = mutableListOf<Int>()
                                            var AreaHeight = mutableListOf<Int>()

                                            var count: Int = res.count!!
                                            //Log.e("res", " data : "+res.data)

                                            for(i in res.data!!.indices){
                                                xCoordinate.add(res.data!![i].x1!!)
                                                yCoordinate.add(res.data!![i].y1!!)
                                                AreaWidth.add(res.data!![i].x2!! - res.data!![i].x1!!)
                                                AreaHeight.add(res.data!![i].y2!! - res.data!![i].y1!!)
                                            }

                                            var bitmap = BitmapFactory.decodeFile(imagePath)
                                            //메타데이터 생성
                                            var encrypted = createMeta(count, bitmap, xCoordinate, yCoordinate, AreaWidth, AreaHeight).encryptCBC()
                                            //Log.e("print", " meta : "+encrypted)

                                            //마스킹 씌우기
                                            for (i in 0 until count) {
                                                bitmap = overlay(bitmap, createMask(AreaWidth[i], AreaHeight[i]),xCoordinate[i], yCoordinate[i])
                                            }
                                            saveFile(imageFiles!![i].name, bitmap) //마스킹 이미지 저장(원본에 덮어 씌우기)
                                            setHiddenData(imagePath, "data", encrypted)
                                            setHiddenData(imagePath, "type", res.tag!!)
                                            setHiddenData(imagePath, "flag", "GTN")

                                        }

                                        //Log.d("retrofitResult", message)
                                    } else
                                        Log.d("retrofitResponse", "response is null")
                                }
                            })
                        }catch (e:Exception){
                            e.printStackTrace()
                        }

                    }


                    when(getHiddenData(imagePath, "type")){
                        "idcard" ->  {
                            temp?.add(imageFiles!![i])
                            tempid?.add(imageFiles!![i])
                        }
                        "license" -> {
                            temp?.add(imageFiles!![i])
                            templicense?.add(imageFiles!![i])
                        }
                        "registration" -> {
                            temp?.add(imageFiles!![i])
                            tempregistration?.add(imageFiles!![i])
                        }
                    }

                }

                allFiles = temp?.toTypedArray()
                idcardFiles = tempid?.toTypedArray()
                licenseFiles = templicense?.toTypedArray()
                registrationFiles = tempregistration?.toTypedArray()
                return true
            }
        }
        return false
    }

    override fun onTabSelected(tab: ActionBar.Tab?, ft: FragmentTransaction?) {
        var myTabFrag: MyTabFragment? = null

        if (tab != null) {
            if(myFrags[tab.position] == null){
                myTabFrag = MyTabFragment()
                val data = Bundle()
                data.putString("tabName", tab.text.toString())
                myTabFrag.arguments = data
                myFrags[tab.position] = myTabFrag
            }
            else {
                myTabFrag = myFrags[tab.position]
            }
        }
        ft?.replace(android.R.id.content, myTabFrag!!)
    }

    override fun onTabUnselected(tab: ActionBar.Tab?, ft: FragmentTransaction?) {}
    override fun onTabReselected(tab: ActionBar.Tab?, ft: FragmentTransaction?) {}

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // 데이터가 넘어왔을 경우
        // 파일을 개인정보파일 리스트에서 제거
        if(resultCode == RESULT_OK){
            var removedItem = data!!.getStringExtra("removedItem") // imagename
            // Toast.makeText(this, removedItem, Toast.LENGTH_SHORT).show()

            // 파일리스트에서 제거
            var temp = imageFiles!!.toMutableList()
            for(i in temp!!.indices){
                if(temp!![i].name == removedItem){
                    temp!!.removeAt(i)
                    break
                }
            }
            imageFiles = temp.toTypedArray()
        }
    }

    class MyTabFragment : androidx.fragment.app.Fragment(){
        var tabName: String? = null

        override fun onCreate(savedInstanceState: Bundle?){
            super.onCreate(savedInstanceState)
            var data = arguments
            tabName = data!!.getString("tabName")
        }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
            var baseLayout : View? = null

            if(tabName === "전체") baseLayout = inflater.inflate(R.layout.mainpage_tab1, null)
            if(tabName === "주민등록증") baseLayout = inflater.inflate(R.layout.mainpage_tab2, null)
            if(tabName === "운전면허증") baseLayout = inflater.inflate(R.layout.mainpage_tab3, null)
            if(tabName === "주민등록등본") baseLayout = inflater.inflate(R.layout.mainpage_tab4, null)
            return baseLayout
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            var cont = context

            //var realmManagerImage2 = RealmManager(Realm.getDefaultInstance())

            // ~/DCIM/Camera 의 모든 이미지 파일 불러오기
            imageFiles = File(Environment.getExternalStorageDirectory().absolutePath + "/DCIM/Camera").listFiles()
            //Log.d("imageFiles", "${Environment.getExternalStorageDirectory().absolutePath}/DCIM/Camera")

            if(tabName === "전체") {
                var gv = view.findViewById<View>(R.id.tab1_gridView) as GridView
                var gAdapter = MyGridAdapter(context = cont, imageFiles) // imageFiles 배열 변경 필요
                gv.adapter = gAdapter

                // 해당 이미지 넘겨주기
                gv.setOnItemClickListener { adapterView, view, position, id ->
                    var intent = Intent(cont, ThirdActivity::class.java)
                    intent.putExtra("Num", position) // 숫자
                    intent.putExtra("token", token)
                    startActivity(intent)
                }
            }

            if(tabName === "주민등록증") {
                // 해당되는 이미지만 불러와야함
                var gv = view.findViewById<View>(R.id.tab2_gridView) as GridView
                var gAdapter = MyGridAdapter(context = cont, idcardFiles) // imageFiles 배열 변경 필요
                gv.adapter = gAdapter
            }

            if(tabName === "운전면허증") {
                // 해당되는 이미지만 불러와야함
                var gv = view.findViewById<View>(R.id.tab3_gridView) as GridView
                var gAdapter = MyGridAdapter(context = cont, licenseFiles) // imageFiles 배열 변경 필요
                gv.adapter = gAdapter
            }

            if(tabName === "주민등록등본") {
                // 해당되는 이미지만 불러와야함
                var gv = view.findViewById<View>(R.id.tab4_gridView) as GridView
                var gAdapter = MyGridAdapter(context = cont, registrationFiles) // imageFiles 배열 변경 필요
                gv.adapter = gAdapter
            }
        }
    }

    class MyGridAdapter(var context: Context?, images: Array<File>?) : BaseAdapter() {
        val imageFile = images
        var imageId = 0

        override fun getCount(): Int {
            if(imageFile!=null) return imageFile.size
            else return 0
        }

        override fun getItem(p0: Int): Any {
            return imageFile!![p0].name
        }

        override fun getItemId(p0: Int): Long {
            return imageId.toLong()
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            var imageview = ImageView(context)
            imageview.layoutParams = ViewGroup.LayoutParams(350, 400)
            imageview.scaleType = ImageView.ScaleType.CENTER_CROP
            imageview.setPadding(5, 5, 5, 5)

            var bitmap = BitmapFactory.decodeFile(imageFile!![p0].toString())
            imageview.setImageBitmap(bitmap)

            imageId = p0

            return imageview
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

    //개인정보 영역 -> 메타데이터 암호 문자열
    private fun createMeta(count:Int, bitmap: Bitmap, xCoordinate:List<Int>, yCoordinate:List<Int>, AreaWidth:List<Int>, AreaHeight:List<Int>): String {
        var cropData : String = ""
        for (i in 0 until count){
            //crop이미지 리스트
            var crop = imageCrop(bitmap, xCoordinate[i], yCoordinate[i], AreaWidth[i], AreaHeight[i])

            cropData += if (cropData == "") {
                crop + "||" + xCoordinate[i].toString() + "||" + yCoordinate[i]
            } else {
                "||" + crop + "||" + xCoordinate[i].toString() + "||" + yCoordinate[i]
            }
        }

        return cropData
    }

    // 메타데이터 삽입 메서드
    private fun setHiddenData(imagePath: String, exifTag: String, data:String) {
        var exif : ExifInterface? = null
        try {
            exif = ExifInterface(imagePath)
        } catch (e : IOException) {
            e.printStackTrace()
        }
        when(exifTag){
            "data" -> exif?.setAttribute(ExifInterface.TAG_USER_COMMENT, data)//data
            "type" -> exif?.setAttribute(ExifInterface.TAG_IMAGE_DESCRIPTION, data)//문서 타입
            "flag" -> exif?.setAttribute(ExifInterface.TAG_SOFTWARE, data)//알고리즘 처리 여부
        }

        exif?.saveAttributes()
    }

    // 메타데이터 추출 메서드(개인정보)
    private fun getHiddenData(imagePath: String, exifTag: String) : String {
        var exif : ExifInterface? = null
        try {
            exif = ExifInterface(imagePath)
        } catch (e : IOException) {
            e.printStackTrace()
        }
        var meta : String = ""
        when(exifTag){
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