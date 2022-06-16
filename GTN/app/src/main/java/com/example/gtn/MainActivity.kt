package com.example.gtn

import android.content.Context
import android.content.Intent
import com.google.android.material.snackbar.Snackbar
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.regex.Pattern

@Suppress("deprecation")
class MainActivity : AppCompatActivity(){
    lateinit var edittext_id: EditText
    lateinit var edittext_password: EditText
    lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loginpage)

        var retrofit = Retrofit.Builder()
            .baseUrl("http://43.200.55.96:1337")
            //.baseUrl("https://gtn.billymin.ga:1337")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var loginService: LoginService = retrofit.create(LoginService::class.java)

        edittext_id = findViewById(R.id.LoginPage_editTextTextId)
        edittext_password = findViewById(R.id.LoginPage_editTextTextPassword)
        btnLogin = findViewById(R.id.LoginPage_button)

        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), Context.MODE_PRIVATE)

        btnLogin.setOnClickListener {
            var id = edittext_id.text.toString()
            var password = edittext_password.text.toString()

            var TorF: String? = null
            var access_token: String? = null

            var cont: Context = this
            // 순서 수정하기
            // 비밀번호 유효성검사
            if (!Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,20}$", password)) {
                Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                edittext_password.setText("")
            }
            else {
                // 서버와 API 통신을 통해 id, password 일치 여부 확인
                // 서버에 id와 password를 전송하는 코드 작성
                // 서버에서 json파일을 받아오는 코드 작성

                loginService.requestLogin(id,password).enqueue(object: Callback<Login> {
                    override fun onFailure(call: Call<Login>, t: Throwable) {
                        t.message?.let { it1 -> Log.e("LOGIN", it1) }
                        var dialog = AlertDialog.Builder(this@MainActivity)

                        dialog.setTitle("에러")
                        dialog.setMessage("호출실패했습니다.")
                        dialog.show()
                    }

                    override fun onResponse(call: Call<Login>, response: Response<Login>) {
                        var login = response.body()
                        Log.d("LOGIN","msg : "+login?.access_token)
                        Log.d("LOGIN","code : "+login?.result)
                        Log.d("LOGIN", "token: ${login?.msg}")
                        TorF = login?.result
                        access_token = login?.access_token

                        var dialog = AlertDialog.Builder(this@MainActivity)
                        /*
                        dialog.setTitle(login?.access_token)
                        dialog.setMessage(login?.result)
                        dialog.show()
                         */
                        //Log.d("TorF", "${TorF}")
                        //Log.d("token", "${access_token}")

                        // 아이디 혹은 비밀번호가 일치하지 않는 경우
                        if (TorF == "0") { //TorF 변수정의 필요
                            Toast.makeText(cont, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                        }
                        // 로그인 성공
                        else if (TorF == "1") {
                            // goto mainpage
                            var intent = Intent(applicationContext, SecondActivity::class.java)
                            intent.putExtra("access_token", access_token)
                            startActivity(intent)
                        }
                        else {
                            // 로그인 실패되는 경우가 있나?
                            Toast.makeText(cont, "서버와의 통신에 실패했습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
/*
                Log.d("TorF", "$TorF")
                // 아이디 혹은 비밀번호가 일치하지 않는 경우
                if (TorF === "0") { //TorF 변수정의 필요
                    Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                }
                // 로그인 성공
                else if (TorF === "1") {
                    // goto mainpage
                    var intent = Intent(applicationContext, SecondActivity::class.java)
                    intent.putExtra("token", token)
                    startActivity(intent)
                }
                else {
                    // 로그인 실패되는 경우가 있나?
                    Toast.makeText(this, "서버와의 통신에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }

 */
            }
        }
    }
}