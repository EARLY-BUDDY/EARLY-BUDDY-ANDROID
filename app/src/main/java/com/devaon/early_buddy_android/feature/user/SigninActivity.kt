package com.devaon.early_buddy_android.feature.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.devaon.early_buddy_android.R
import com.devaon.early_buddy_android.data.db.Information
import com.devaon.early_buddy_android.data.login.Login
import com.devaon.early_buddy_android.data.user.UserSigninResponse
import com.devaon.early_buddy_android.feature.home.HomeActivity
import com.devaon.early_buddy_android.feature.initial_join.SetNicknameActivity
import com.devaon.early_buddy_android.network.EarlyBuddyServiceImpl
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_signin.*
import kotlinx.android.synthetic.main.activity_signup.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SigninActivity : AppCompatActivity() {

    private var idFlag: Boolean = false
    private var pwFlag: Boolean = false
    private var flag: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        //자동로그인
        /*val id = Login.getUser(this)
        if (id.isNotEmpty()) { // 만약 nickname 설정 안되어 있다면, goToNicknameActivity
            goToHomeActivity(id)
            finish()
        }*/
        makeController()
        Log.d("testset", "0")
    }


    private fun makeController() {

        idBntActive()
        pwBntActive()

        act_signin_cl_login?.setOnClickListener{
            val id = act_signin_et_id?.text.toString()
            val pw = act_signin_et_pw?.text.toString()
            val deviceToken = "fyG5BOMVqFM:APA91bGGIEdk21i6sgXWTRNepyf-1f4Znmv1qOMxPwuYLlsz02ux7l7SKMdYjGRYDIRVhdITjDA8ZsjNmWrB-tYwuyA-kNgP6O0SqTwTat0dXR-vygExOfpHaxSg8Xcs3OJS25B8GGXS"
            if (id.isEmpty() || pw.isEmpty()) {
                Toast.makeText(this, "아이디와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val response = requestLogin(id, pw)
            if (response) {
                postUserData(id, pw, deviceToken)

                if(flag) {
                    Log.d("testset", "flag true")
                    Login.setUser(this, id)
                    val intent =
                        Intent(this@SigninActivity, SetNicknameActivity::class.java).apply {
                            Log.d("testset", "2")
                            setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            finish()
                           /* startActivity(intent)*/
                        }
                    Log.d("testset", "3")
                    startActivity(intent)
                }
            } else {
                Toast.makeText(this, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
                act_signin_et_id?.requestFocus()
            }
        }

        act_signin_bt_signup?.setOnClickListener {
            val intent = Intent(this@SigninActivity, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun requestLogin(id: String, pw: String): Boolean {
        return true
    }

    private fun postUserData(id : String, pw : String, deviceToken : String) {

/*
        data class UserSigninResponse(
            @SerializedName("jwt")
            val jwt: String,
            @SerializedName("userIdx")
            val Idx: Int,
            @SerializedName("userName")
            val userName: String
        )*/

        var jsonObject = JSONObject()
        jsonObject.put("userId", id)
        jsonObject.put("userPw", pw)
        jsonObject.put("deviceToken", deviceToken)

        val body = JsonParser().parse(jsonObject.toString()) as JsonObject

        val callSigninResponse: Call<UserSigninResponse> = EarlyBuddyServiceImpl.service.postSigninUser(
            body
        )

        callSigninResponse.enqueue(object : Callback<UserSigninResponse> {
            override fun onFailure(call: Call<UserSigninResponse>, t: Throwable) {
                Log.e("error is ", t.toString())
            }

            override fun onResponse(call: Call<UserSigninResponse>, response: Response<UserSigninResponse>) {
                if (response.isSuccessful) {
                    flag = true
                    Log.e("result is ", response.body().toString())
                    val signinUser = response.body()!!

                    if(signinUser.userName != null)
                        Information.idx = signinUser.Idx
                        Information.nickName = signinUser.userName
                }
            }
        })

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


    private fun idBntActive() {
        act_signin_et_id.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(p0!!.length > 0){
                    act_signin_cl_id.setBackgroundResource(R.drawable.act_place_round_rect_blue)
                    act_signin_et_id.setTextColor(ContextCompat.getColor(this@SigninActivity, R.color.black))
                    idFlag = true
                }else {
                    act_signin_cl_id.setBackgroundResource(R.drawable.act_place_round_rect_gray)
                    act_signin_et_id.setTextColor(ContextCompat.getColor(this@SigninActivity, R.color.gray))
                    idFlag = false
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })
    }

    private fun pwBntActive() {
        act_signin_et_pw.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (p0!!.length > 0) {
                    act_signin_cl_pw.setBackgroundResource(R.drawable.act_place_round_rect_blue)
                    act_signin_et_pw.setTextColor(
                        ContextCompat.getColor(
                            this@SigninActivity,
                            R.color.black
                        )
                    )
                    pwFlag = true

                    if (idFlag && pwFlag) {
                        act_signin_cl_login.setBackgroundResource(R.drawable.act_place_round_rect_blue_full)
                    }
                } else {
                    act_signin_cl_pw.setBackgroundResource(R.drawable.act_place_round_rect_gray)
                    act_signin_et_pw.setTextColor(
                        ContextCompat.getColor(
                            this@SigninActivity,
                            R.color.gray
                        )
                    )
                    pwFlag = false
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })
    }

}
