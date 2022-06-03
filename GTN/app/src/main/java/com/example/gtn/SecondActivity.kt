package com.example.gtn

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import java.io.File

@Suppress("deprecation")
class SecondActivity : AppCompatActivity(), ActionBar.TabListener {
    lateinit var tab1: ActionBar.Tab
    lateinit var tab2: ActionBar.Tab
    lateinit var tab3: ActionBar.Tab
    lateinit var tab4: ActionBar.Tab

    var myFrags = arrayOfNulls<MyTabFragment>(4)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.temppage)

        var bar = this.supportActionBar
        bar!!.navigationMode = ActionBar.NAVIGATION_MODE_TABS

        tab1 = bar.newTab()
        tab1.text = "전체"
        tab1.setTabListener(this)
        bar.addTab(tab1)

        tab2 = bar.newTab()
        tab2.text = "신분증"
        tab2.setTabListener(this)
        bar.addTab(tab2)

        tab3 = bar.newTab()
        tab3.text = "통장사본"
        tab3.setTabListener(this)
        bar.addTab(tab3)

        tab4 = bar.newTab()
        tab4.text = "증명서"
        tab4.setTabListener(this)
        bar.addTab(tab4)
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

    class MyTabFragment : androidx.fragment.app.Fragment(){
        var tabName: String? = null
        var imageFiles: Array<File>? = null

        override fun onCreate(savedInstanceState: Bundle?){
            super.onCreate(savedInstanceState)
            var data = arguments
            tabName = data!!.getString("tabName")
        }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
            var baseLayout : View? = null

            if(tabName === "전체") baseLayout = inflater.inflate(R.layout.mainpage_tab1, null)
            if(tabName === "신분증") baseLayout = inflater.inflate(R.layout.mainpage_tab2, null)
            if(tabName === "통장사본") baseLayout = inflater.inflate(R.layout.mainpage_tab3, null)
            if(tabName === "증명서") baseLayout = inflater.inflate(R.layout.mainpage_tab4, null)
            return baseLayout
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            var cont = context

            //
            imageFiles = File(Environment.getExternalStorageDirectory().absolutePath + "/DCIM/Camera").listFiles()
            //Log.d("imageFiles", "${Environment.getExternalStorageDirectory().absolutePath}/DCIM/Camera")
            // 문제점: line 105는 /DCIM/Camera에 있는 모든 파일을 가져옴
            //        개인정보가 존재하는 사진만 가져오도록 바꾸기
            //        internal storage 내 파일에 개인정보가 존재하는 사진 이름을 저장해두기?
            //        다른 방법?

            // 이미지 유형별 분류
            //      1차원 Array<Int>로 유형1, 2, 3 매크로 정의해 사용하면 될 듯
            // -> putIntent로 intArray 전송 가능

            // 이미지 탐색 완료 여부
            //      ??? 전체 파일에 대해서 해야하는 것 아닌가. .. ?


            if(tabName === "전체") {
                var gv = view.findViewById<View>(R.id.tab1_gridView) as GridView
                var gAdapter = MyGridAdapter(context = cont, imageFiles)
                gv.adapter = gAdapter

                // 해당 이미지 넘겨주기
                gv.setOnItemClickListener { adapterView, view, position, id ->
                    var intent = Intent(cont, ThirdActivity::class.java)
                    intent.putExtra("Num", position) // 숫자
                    startActivity(intent)
                }
            }

            if(tabName === "신분증") {
                // 해당되는 이미지만 불러와야함
                var gv = view.findViewById<View>(R.id.tab2_gridView) as GridView
                var gAdapter = MyGridAdapter(context = cont, imageFiles!!)
                gv.adapter = gAdapter
            }

            if(tabName === "통장사본") {
                // 해당되는 이미지만 불러와야함
                var gv = view.findViewById<View>(R.id.tab3_gridView) as GridView
                var gAdapter = MyGridAdapter(context = cont, imageFiles!!)
                gv.adapter = gAdapter
            }

            if(tabName === "증명서") {
                // 해당되는 이미지만 불러와야함
                var gv = view.findViewById<View>(R.id.tab4_gridView) as GridView
                var gAdapter = MyGridAdapter(context = cont, imageFiles!!)
                gv.adapter = gAdapter
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // 데이터가 넘어왔을 경우
        // 파일을 개인정보파일 리스트에서 제거
        if(data != null){

        }
    }

    class MyGridAdapter(var context: Context?, images: Array<File>?) : BaseAdapter() {
        //var imageFname = images?.get(0)?.toString()
        val imageFile = images
        var imageId = 0

        override fun getCount(): Int {
            return imageFile!!.size
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

            //imageview.setImageResource(imageId[p0])

            var bitmap = BitmapFactory.decodeFile(imageFile!![p0].toString())
            imageview.setImageBitmap(bitmap)

            imageId = p0

            return imageview
        }
    }
}