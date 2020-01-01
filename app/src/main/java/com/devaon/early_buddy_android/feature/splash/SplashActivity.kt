package com.devaon.early_buddy_android.feature.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.devaon.early_buddy_android.R
import com.devaon.early_buddy_android.data.login.Login
import com.devaon.early_buddy_android.feature.home.HomeActivity
import com.devaon.early_buddy_android.feature.initial_join.SetNicknameActivity
import com.devaon.early_buddy_android.feature.user.SigninActivity
import com.devaon.early_buddy_android.feature.user.SignupActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //자동로그인
        val id = Login.getUser(this)
        val nickName = Login.getNickName(this)

        //아이디 있을 경우
        if (id.isNotEmpty()) {
            //통신
            //닉네임 있으면
            if(nickName.isNotEmpty()){
                //홈
                /*goToHomeActivity()*/
                goToHomeActivity(nickName) //닉네임을 보내줘야하나?
                finish()
            }else{ //닉네임 없으면 설정하러
                goToSetNickNameActivity()
                finish()
            }
        }else{//회원가입하러
            goToSigninActivity(id)
            finish()
        }


    }

    private fun goToHomeActivity(id: String) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("login", id)
        startActivity(intent)
    }

    private fun goToSigninActivity(nickName: String) {
        val intent = Intent(this, SigninActivity::class.java)
        intent.putExtra("nickname", nickName)
        startActivity(intent)
    }

    private fun goToSetNickNameActivity() {
        val intent = Intent(this, SetNicknameActivity::class.java)
        startActivity(intent)
    }
}
