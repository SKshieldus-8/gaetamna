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

        var curNum : Int = 0
        var imageFiles: Array<File>? = null
        lateinit var imageFname: String

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
            // 각 탭 별로 화면 내에서 처리할 이벤트 정의
            // 해당하는 유형의 사진 출력
            // 사진이 눌렸을 경우 실행할 이벤트를 함수로 만들어 재사용 할 것
            //
            try {
                imageFiles =
                    File(Environment.getExternalStorageDirectory().absolutePath + "/DCIM").listFiles()
                //이게 왜 Null일까,,,
            }catch (e: Exception){
                Log.e("error", "$e")
            }
            //imageFname = imageFiles!![0].toString()

            if(tabName === "전체") {
                var gv = view.findViewById<View>(R.id.tab1_gridView) as GridView
                var gAdapter = MyGridAdapter(context = cont, imageFiles)
                gv.adapter = gAdapter

                // 해당 이미지 넘겨주기
                gv.setOnItemClickListener { adapterView, view, position, id ->

                    var intent = Intent(cont, ThirdActivity::class.java)
                    //var item = gAdapter.getItem(position) as Array<Int>
                    //var itemId = gAdapter.getItemId(position)

                    //intent.putExtra("arrayImage", item)
                    //intent.putExtra("Num", position) // 숫자
                    //intent.putExtra("itemId", itemId.toInt())
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

    class MyGridAdapter(var context: Context?, images: Array<File>?) : BaseAdapter() {
        var imageFname = images?.get(0)?.toString()

        override fun getCount(): Int {
            return 0
        }

        override fun getItem(p0: Int): Any {
            return 0
        }

        override fun getItemId(p0: Int): Long {
            return 0
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            var imageview = ImageView(context)
            imageview.layoutParams = ViewGroup.LayoutParams(350, 400)
            imageview.scaleType = ImageView.ScaleType.FIT_CENTER
            imageview.setPadding(5, 5, 5, 5)

            //imageview.setImageResource(imageId[p0])
            //imageview.setImageURI()

            var bitmap = BitmapFactory.decodeFile(imageFname)
            imageview.setImageBitmap(bitmap)

            return imageview
        }
    }
}