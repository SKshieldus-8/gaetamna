package com.example.gtn

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.*
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import io.realm.Realm
import java.io.File

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

    var myFrags = arrayOfNulls<MyTabFragment>(4)

    var realmManagerImage = RealmManager(Realm.getDefaultInstance())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.temppage)

        var intent = intent
        token = intent.getStringExtra("token")


        Log.d("flag", "1")
        // DB1에 아무런 정보가 없으면 최초실행
        if(realmManagerImage.findAll() != null) {
            Log.d("flag", "2")
            // 서버와 통신하며 사진을 전부 훑는 작업 필요
            // 0. DB1에 정보가 존재하는지 확인
            // (DB1에 아무런 정보가 존재하지 않는다면)
            // 1. 파일 배열 생성(개인정보가 전부 존재하는 배열, 해당 문서 타입별 배열*3)
            // 2. imageFiles의 첫번째 파일부터 시작해 모든 파일을 API 통신
            // 3. API 통신 결과
            //      3-1. 개인정보 이미지가 존재하는 경우
            //          3-1.1. DB1 갱신
            //          3-1.2. DB2 갱신
            //          3-1.3. 이미지 비식별화
            //      3-2. 개인정보 이미지가 존재하지 않는 경우
            //          3-2.1. 다음 이미지 탐색
        }


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
                // 개인정보가 존재하는지 이미지 탐색
                // DB 테이블 두 개 이용
                // DB1: 개인정보가 존재하는 이미지 테이블
                //      PK(imgname+time), imgname, imgtype, isinformation, time
                // DB2: 탐색주기
                //      Date(YYYY-MM-DD-HH-mm-SS)
                // 1. 새로고침 버튼(메뉴에?) 클릭 시
                // 2. DB2와 현재시각 비교
                // 3. getExternalStorageDirectory() 이용해 이미지 전부 로드 -> imageFiles
                // 4. DB1의 내용과 비교
                //      4-1. DB1에 저장된 가장 마지막 사진 이후의 사진부터 서버에 전송
                //      4-2. 3의 이미지 리스트 내 이미지들의 메타데이터를 확인, DB2의 가장 마지막 시간 이후에 촬영된 사진부터 서버에 전송
                // 5. 개인정보가 포함된 사진이 존재하는 경우 비식별화 및 DB 업데이트
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
            var removedItem = data!!.getStringExtra("removedItem")
            Toast.makeText(this, removedItem, Toast.LENGTH_SHORT).show()
            // DB에서 파일 제거
            if (removedItem != null) {
                realmManagerImage.deleteByName(removedItem)
            }

            // 파일리스트에서 제거
            var temp = imageFiles!!.toMutableList()
            for(i in temp!!.indices){
                if(temp!![i].name == removedItem){
                    //
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
            Log.d("flag", "3")
        }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
            var baseLayout : View? = null

            Log.d("flag", "4")
            if(tabName === "전체") baseLayout = inflater.inflate(R.layout.mainpage_tab1, null)
            if(tabName === "주민등록증") baseLayout = inflater.inflate(R.layout.mainpage_tab2, null)
            if(tabName === "운전면허증") baseLayout = inflater.inflate(R.layout.mainpage_tab3, null)
            if(tabName === "주민등록등본") baseLayout = inflater.inflate(R.layout.mainpage_tab4, null)
            return baseLayout
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            var cont = context

            var realmManagerImage2 = RealmManager(Realm.getDefaultInstance())

            Log.d("flag", "5")
            // ~/DCIM/Camera 의 모든 이미지 파일 불러오기
            imageFiles = File(Environment.getExternalStorageDirectory().absolutePath + "/DCIM/Camera").listFiles()
            //Log.d("imageFiles", "${Environment.getExternalStorageDirectory().absolutePath}/DCIM/Camera")

            // imageFiles를 각 카테고리별로 구분하기
            // allFiles, idcardFiles, licenseFiles, registrationFiles
            var temp: MutableList<File>?  = null
            var tempid: MutableList<File>? = null
            var templicense: MutableList<File>? = null
            var tempregistration: MutableList<File>? = null
            for (i in imageFiles!!.indices){
                var data = realmManagerImage2.find(imageFiles!![i].name)
                if(data != null){
                    temp?.add(imageFiles!![i])
                    when (data.imgType){
                        "id" -> tempid?.add(imageFiles!![i])
                        "license" -> templicense?.add(imageFiles!![i])
                        "registration" -> tempregistration?.add(imageFiles!![i])
                    }
                }
            }
            allFiles = temp?.toTypedArray()
            idcardFiles = tempid?.toTypedArray()
            licenseFiles = templicense?.toTypedArray()
            registrationFiles = tempregistration?.toTypedArray()

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
}