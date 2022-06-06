package com.example.gtn

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.util.regex.Pattern

@Suppress("deprecation")
class MainActivity : AppCompatActivity(){

    lateinit var edittext_id: EditText
    lateinit var edittext_password: EditText
    lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loginpage)

        edittext_id = findViewById(R.id.LoginPage_editTextTextId)
        edittext_password = findViewById(R.id.LoginPage_editTextTextPassword)
        btnLogin = findViewById(R.id.LoginPage_button)

        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), Context.MODE_PRIVATE)

        btnLogin.setOnClickListener {
            var id = edittext_id.text.toString()
            var password = edittext_password.text.toString()

            // 서버와 API 통신을 통해 id, password 일치 여부 확인
            // 서버에 id와 password를 전송하는 코드 작성
            // 서버에서 json파일을 받아오는 코드 작성

            var TorF: String? = null
            var token: String? = null

            // 비밀번호 유효성검사
            if (!Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,20}$", password)) {
                Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                edittext_password.setText("")
            }
            // 아이디 혹은 비밀번호가 일치하지 않는 경우
            else if(TorF == "false"){ //TorF 변수정의 필요
                Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            }
            // 로그인 성공
            else if(TorF == "true") {
                // goto mainpage
                var intent = Intent(applicationContext, SecondActivity::class.java)
                intent.putExtra("token", token)
                startActivity(intent)
            }
            else {
                // 로그인 실패되는 경우가 있나?
                Toast.makeText(this, "서버와의 통신에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
/*
            if(id == "id" && password == "password") {
                // goto mainpage
                var intent = Intent(applicationContext, SecondActivity::class.java)
                startActivity(intent)
            }
            // 유효성검사
            else if (!Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,20}$", password)) {
                Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                edittext_password.setText("")
            }
            else if(){

            }
            else {
                //Toast message
                Toast.makeText(this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
                edittext_password.setText("")
            }
 */
        }
    }
}