package com.devaon.early_buddy_android.feature.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import com.devaon.early_buddy_android.R
import com.devaon.early_buddy_android.data.login.Login
import com.devaon.early_buddy_android.feature.home.HomeActivity
import com.devaon.early_buddy_android.feature.initial_join.SetNicknameActivity
import kotlinx.android.synthetic.main.activity_signin.*

class SigninActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)


        val id = Login.getUser(this)
        if (id.isNotEmpty()) { //통신 한 번 더. 만약 nickname 설정 안되어 있다면, goToNicknameActivity
            goToHomeActivity(id)
            finish()
        }

        makeController()

    }


    private fun makeController() {

        act_signin_cl_login?.setOnClickListener {
            val intent = Intent(this@SigninActivity, SignupActivity::class.java)

            startActivity(intent)
        }


        act_signin_bt_signup?.setOnClickListener{
            val id = act_signin_et_id?.text.toString()
            val pw = act_signin_et_pw?.text.toString()

            if (id.isEmpty() || pw.isEmpty()) {
                Toast.makeText(this, "아이디와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val response = requestLogin(id, pw)
            if (response) {
                val checkBox: CheckBox = findViewById(R.id.act_signin_auto_checkbox)
                if (checkBox.isChecked) {
                    Login.setUser(this, id)
                    goToHomeActivity(id)
                }
            } else {
                Toast.makeText(this, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
                act_signin_et_id?.requestFocus()
            }
        }
    }

    private fun requestLogin(id: String, pw: String): Boolean {
        return true
    }

    private fun goToHomeActivity(id: String) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("login", id)

        startActivity(intent)
    }

    private fun goToNicknameActivity(id: String) {
        val intent = Intent(this, SetNicknameActivity::class.java)
        intent.putExtra("login", id)

        startActivity(intent)
    }



}
