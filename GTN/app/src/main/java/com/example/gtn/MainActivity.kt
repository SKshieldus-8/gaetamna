package com.example.gtn

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

        btnLogin.setOnClickListener {
            var password = edittext_password.text.toString()
            if(password == "password"){
                // goto mainpage
                Toast.makeText(this, "비밀번호가 올바릅니다.", Toast.LENGTH_SHORT).show()
                edittext_password.setText("")
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