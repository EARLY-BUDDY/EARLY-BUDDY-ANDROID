package com.devaon.early_buddy_android.feature.user

import android.content.Intent
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
import com.devaon.early_buddy_android.data.user.UserResponse
import com.devaon.early_buddy_android.feature.initial_join.SetNicknameActivity
import com.devaon.early_buddy_android.network.EarlyBuddyServiceImpl
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_signup.*
import org.json.JSONObject
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern


class SignupActivity : AppCompatActivity() {

    lateinit var signinDialog : SigninDialogFragment
    val pwdPattern = Pattern.compile("^[a-zA-Z0-9]+$", Pattern.CASE_INSENSITIVE)

    var idFlag: Boolean = false
    var pwFlag: Boolean = false
    var pwCheckFlag: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        makeController()
    }

    private fun makeController() {
        idDuplicatedCheck()
        passwordCheck()
        passwordCorretCheck()

        act_signup_cl_join.setOnClickListener {

            signinDialog = SigninDialogFragment()
            signinDialog.setOnDialogDismissedListener(signInDialogFragmentDismissListener)


            val id = act_signup_et_id.text.toString()
            val pw = act_signup_et_pw.text.toString()
            val pwCheck = act_signup_et_pw_check.text.toString()


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
        }
    }

    private fun postUserData(id : String, pw : String) {

        var jsonObject = JSONObject()
        jsonObject.put("userId", id)
        jsonObject.put("userPw", pw)

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
                }
            }
        })

    }


    private fun idDuplicatedCheck() {
        act_signup_et_id.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (p0!!.length > 0) {
                    /*화면 터치시 화면내려감
                    이때 서버와 통신
                        통신했는데 중복된 아이디일 경우
                                act_signup_tv_id_ment.showOrInvisible(true) //중복된 아이디입니다. 경고메시지 보여줌
                                act_signup_cl_id.setBackgroundResource(R.drawable.act_signup_round_rect_red)
                                act_signup_et_id.setTextColor(ContextCompat.getColor(this@SignupActivity, R.color.black))*/

                    act_signup_tv_id_ment.showOrInvisible(false)
                    act_signup_cl_id.setBackgroundResource(R.drawable.act_signup_round_rect_blue)
                    act_signup_et_id.setTextColor(
                        ContextCompat.getColor(
                            this@SignupActivity,
                            R.color.black
                        )
                    )
                    idFlag = true
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
        })
    }

    private fun passwordCheck() {
        act_signup_et_pw.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                //if (p0!!.length > 0) {

                if ((p0!!.length < 6) || !(pwdPattern.matcher(act_signup_et_pw.text.toString()).matches())) {
                    //6글자 이상 입력해주세요 -예외처리 메시지 띄움
                    act_signup_tv_pw_ment.showOrInvisible(true)

                    act_signup_cl_pw.setBackgroundResource(R.drawable.act_signup_round_rect_red)
                    act_signup_et_pw.setTextColor(
                        ContextCompat.getColor(
                            this@SignupActivity,
                            R.color.black
                        )
                    )
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
                    if (idFlag && pwFlag && pwCheckFlag) {
                        act_signup_cl_join.setBackgroundResource(R.drawable.act_place_round_rect_blue_full)
                    }

                }else{
                    act_signup_tv_pw_check_ment.showOrInvisible(true)
                    act_signup_cl_pw_check.setBackgroundResource(R.drawable.act_signup_round_rect_red)
                    pwCheckFlag = false

                }
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


