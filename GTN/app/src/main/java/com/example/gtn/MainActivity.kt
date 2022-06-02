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

    lateinit var edittext_password: EditText
    lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loginpage)

        edittext_password = findViewById<EditText>(R.id.LoginPage_editTextTextPassword)
        btnLogin = findViewById<Button>(R.id.LoginPage_button)

        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), Context.MODE_PRIVATE)

        btnLogin.setOnClickListener {
            var password = edittext_password.text.toString()
            if(password == "password"){
                // goto mainpage
                var intent = Intent(applicationContext, SecondActivity::class.java)
                startActivity(intent)
            }
            // 유효성검사
            else if (!Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,20}$", password)) {
                Toast.makeText(this, "비밀번호 형식을 지켜주세요.", Toast.LENGTH_SHORT).show()
                edittext_password.setText("")
            }

            else {
                //Toast message
                Toast.makeText(this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
                edittext_password.setText("")
            }
        }
    }
}