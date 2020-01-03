package com.devaon.early_buddy_android.feature.user

import android.content.Intent
import android.icu.text.IDNA
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.devaon.early_buddy_android.R
import com.devaon.early_buddy_android.data.db.Information
import com.devaon.early_buddy_android.data.user.UserResponse
import com.devaon.early_buddy_android.feature.initial_join.SetNicknameActivity
import com.devaon.early_buddy_android.network.EarlyBuddyServiceImpl
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_signup.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern


class SignupActivity : AppCompatActivity() {

    lateinit var signinDialog : SigninDialogFragment
    val pwdPattern = Pattern.compile("^[a-zA-Z0-9]+$", Pattern.CASE_INSENSITIVE)
    lateinit var id: String
    lateinit var pw: String
    lateinit var pwCheck: String

    var idFlag: Boolean = false
    var pwFlag: Boolean = false
    var pwCheckFlag: Boolean = false
    var duplicatedFlag: Boolean = true  //true : 중복 아님, flase :  중복

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        Log.d("test", "signup activity : " )

        makeController()


    }

    private fun makeController() {
        idDuplicatedCheck()
        passwordCheck()
        passwordCorretCheck()



        act_signup_cl_join.setOnClickListener {
            Log.d("test", "눌려따ㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏ")
            id = act_signup_et_id.text.toString()
            pw = act_signup_et_pw.text.toString()
            pwCheck = act_signup_et_pw_check.text.toString()

            signinDialog = SigninDialogFragment()
            signinDialog.setOnDialogDismissedListener(signInDialogFragmentDismissListener)


            Log.d("test", "id : " + id)
            Log.d("test", "pw : " + pw)
            Log.d("test", "pwCheck : " + pwCheck)
            Log.d("test", "idFlag : " + idFlag)
            Log.d("test", "pwFlag : " + pwFlag)
            Log.d("test", "pwCheckFlag : " + pwCheckFlag)

            if (id.isEmpty() || pw.isEmpty() || pwCheck.isEmpty()) {
                Toast.makeText(this, "아이디와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                if (idFlag && pwFlag && pwCheckFlag) {
                    postUserData(id, pw)

                }
            }

        }

    }

    var signInDialogFragmentDismissListener = object : SigninDialogFragment.OnDialogDismissedListener {
        override fun onDialogDismissed() {
            val intent = Intent(this@SignupActivity, SigninActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun postUserData(id : String, pw : String) {

        //body
        var jsonObject = JSONObject()
        jsonObject.put("userId", id)
        jsonObject.put("userPw", pw)

        Log.d("test", "postUserData id : " + id)
        Log.d("test", "postUserData pw : " + pw)

        val body = JsonParser().parse(jsonObject.toString()) as JsonObject

        val callSignUpResponse: Call<UserResponse> = EarlyBuddyServiceImpl.service.postSignupUser(
            body
        )

        callSignUpResponse.enqueue(object : Callback<UserResponse> {
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("error is ", t.toString())
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    Log.e("result is ", response.body().toString())
                    val signupUser = response.body()!!
                    signinDialog.show(supportFragmentManager,"signin_fagment")
                    idFlag = true
                }else{
                    Log.e("fail message ", response.message())
                }
            }
        })
    }

    private fun getDuplicatedCheck(id : String) {

        //body
        var jsonObject = JSONObject()
        jsonObject.put("userId", id)

        val body = JsonParser().parse(jsonObject.toString()) as JsonObject

        val callSignUpResponse: Call<UserResponse> = EarlyBuddyServiceImpl.service.postSignupUser(
            body
        )

        callSignUpResponse.enqueue(object : Callback<UserResponse> {
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("error is ", t.toString())
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    Log.e("result is ", response.body().toString())
                    val signupUser = response.body()!!
                    signupUser.message
                    if(signupUser.message.equals("이미 사용중인 아이디입니다.")){
                        Log.d("testtest", "id duplicated")
                        duplicatedFlag = false
                    }else {
                        duplicatedFlag = true
                    }
                }
            }
        })
    }




    private fun idDuplicatedCheck() {
        /* act_signup_et_id.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (p0!!.length > 0) {
                    Log.d("testtest", "전")
                    postUserData(id,pw)

                    if(duplicatedFlag){ //성공
                        Log.d("testtest", "성공")
                        act_signup_tv_id_ment.showOrInvisible(false)
                        act_signup_cl_id.setBackgroundResource(R.drawable.act_signup_round_rect_blue)
                        act_signup_et_id.setTextColor(
                            ContextCompat.getColor(
                                this@SignupActivity,
                                R.color.black
                            )
                        )
                    }else{ //중복된 아이디
                        Log.d("testtest", "아이디 중복")
                        act_signup_tv_id_ment.showOrInvisible(true) //중복된 아이디입니다. 경고메시지 보여줌
                        act_signup_cl_id.setBackgroundResource(R.drawable.act_signup_round_rect_red)
                        act_signup_et_id.setTextColor(ContextCompat.getColor(this@SignupActivity, R.color.black))
                    }
                    *//*화면 터치시 화면내려감
                    이때 서버와 통신
                    postUserData(id,pw)
                        통신했는데 중복된 아이디일 경우
                                act_signup_tv_id_ment.showOrInvisible(true) //중복된 아이디입니다. 경고메시지 보여줌
                                act_signup_cl_id.setBackgroundResource(R.drawable.act_signup_round_rect_red)
                                act_signup_et_id.setTextColor(ContextCompat.getColor(this@SignupActivity, R.color.black))*//*

                    *//*act_signup_tv_id_ment.showOrInvisible(false)
                    act_signup_cl_id.setBackgroundResource(R.drawable.act_signup_round_rect_blue)
                    act_signup_et_id.setTextColor(
                        ContextCompat.getColor(
                            this@SignupActivity,
                            R.color.black
                        )
                    )*//*
                    //idFlag = true
                } else {
                    act_signup_cl_id.setBackgroundResource(R.drawable.act_signup_round_rect_gray)
                    act_signup_et_id.setTextColor(
                        ContextCompat.getColor(
                            this@SignupActivity,
                            R.color.light_gray
                        )
                    )
                    idFlag = false
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })*/



        act_signup_et_id.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                id = act_signup_et_id.text.toString()
                pw = act_signup_et_pw.text.toString()

                //통신
                if (p0!!.length > 0) {
                    //getDuplicatedCheck(id)
                    Log.d("testtest", "duplicated")

                    /*if (duplicatedFlag) { //중복아님. 즉 성공
                        Log.d("testtest", "성공")
                        act_signup_tv_id_ment.showOrInvisible(false)
                        act_signup_cl_id.setBackgroundResource(R.drawable.act_signup_round_rect_blue)
                        act_signup_et_id.setTextColor(
                            ContextCompat.getColor(
                                this@SignupActivity,
                                R.color.black
                            )
                        )
                    } else { //false 중복된 아이디
                        Log.d("testtest", "아이디 중복")
                        act_signup_tv_id_ment.showOrInvisible(true) //중복된 아이디입니다. 경고메시지 보여줌
                        act_signup_cl_id.setBackgroundResource(R.drawable.act_signup_round_rect_red)
                        act_signup_et_id.setTextColor(
                            ContextCompat.getColor(
                                this@SignupActivity,
                                R.color.black
                            )
                        )
                    }*/
                    idFlag = true
                } else {
                    act_signup_cl_join.setBackgroundResource(R.drawable.act_place_round_rect_gray_full)
                    idFlag = false
                }
               /* if (idFlag && pwFlag && pwCheckFlag) {
                    act_signup_cl_join.setBackgroundResource(R.drawable.act_place_round_rect_blue_full)
                }else{
                    act_signup_cl_join.setBackgroundResource(R.drawable.act_place_round_rect_gray_full)
                }*/
            }

        })
    }
    private fun passwordCheck() {
        act_signup_et_pw.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

                if ((p0!!.length < 6) || !(pwdPattern.matcher(act_signup_et_pw.text.toString()).matches())) {
                    act_signup_tv_pw_ment.showOrInvisible(true)

                    act_signup_cl_pw.setBackgroundResource(R.drawable.act_signup_round_rect_red)
                    act_signup_et_pw.setTextColor(
                        ContextCompat.getColor(
                            this@SignupActivity,
                            R.color.black
                        )
                    )
                    act_signup_cl_join.setBackgroundResource(R.drawable.act_place_round_rect_gray_full)
                    pwFlag = false
                } else {
                    act_signup_tv_pw_ment.showOrInvisible(false)
                    act_signup_cl_pw.setBackgroundResource(R.drawable.act_signup_round_rect_blue)
                    act_signup_et_pw.setTextColor(
                        ContextCompat.getColor(
                            this@SignupActivity,
                            R.color.black
                        )
                    )

                    if(!act_signup_et_pw.text.toString().equals(act_signup_et_pw_check.text.toString())) {
                        act_signup_tv_pw_check_ment.showOrInvisible(true)
                        act_signup_cl_pw_check.setBackgroundResource(R.drawable.act_signup_round_rect_red)
                        act_signup_cl_join.setBackgroundResource(R.drawable.act_place_round_rect_gray_full)
                        pwCheckFlag = false
                    }
                    pwFlag = true
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })
    }

    private fun passwordCorretCheck() {
        act_signup_et_pw_check.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(act_signup_et_pw.text.toString().equals(act_signup_et_pw_check.text.toString())){
                    act_signup_tv_pw_check_ment.showOrInvisible(false)
                    act_signup_cl_pw_check.setBackgroundResource(R.drawable.act_signup_round_rect_blue)
                    act_signup_et_pw_check.setTextColor(
                        ContextCompat.getColor(
                            this@SignupActivity,
                            R.color.black
                        )
                    )
                    pwCheckFlag = true


                }else{
                    act_signup_tv_pw_check_ment.showOrInvisible(true)
                    act_signup_cl_pw_check.setBackgroundResource(R.drawable.act_signup_round_rect_red)
                    pwCheckFlag = false
                    act_signup_cl_join.setBackgroundResource(R.drawable.act_place_round_rect_gray_full)

                }
                /*if (idFlag && pwFlag && pwCheckFlag) {
                    act_signup_cl_join.setBackgroundResource(R.drawable.act_place_round_rect_blue_full)
                }else{
                    act_signup_cl_join.setBackgroundResource(R.drawable.act_place_round_rect_gray_full)
                }*/

            }
        })
    }

    private fun View.showOrInvisible(show: Boolean) {
        visibility = if (show) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
    }



}


