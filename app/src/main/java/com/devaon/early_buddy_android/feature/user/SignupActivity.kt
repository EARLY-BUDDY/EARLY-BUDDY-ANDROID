package com.devaon.early_buddy_android.feature.user

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.devaon.early_buddy_android.R
import kotlinx.android.synthetic.main.activity_signup.*
import java.util.regex.Pattern


class SignupActivity : AppCompatActivity() {

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

            val id = act_signup_et_id.text.toString()
            val pw = act_signup_et_pw.text.toString()
            val pwCheck = act_signup_et_pw_check.text.toString()


            if (id.isEmpty() || pw.isEmpty() || pwCheck.isEmpty()) {
                Toast.makeText(this, "아이디와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                if (idFlag && pwFlag && pwCheckFlag) {
                    val intent = Intent(this@SignupActivity, SigninActivity::class.java)
                    startActivity(intent)
                }
            }

        }

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


