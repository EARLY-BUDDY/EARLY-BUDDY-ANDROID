package com.devaon.early_buddy_android

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.devaon.early_buddy_android.data.login.Login
import kotlinx.android.synthetic.main.activity_set_nickname.*
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

            /*val patternPw: Pattern = Pattern.compile("^[a-zA-Z0-9]+$")*/

            val id = act_signup_et_id.text.toString()
            val pw = act_signup_et_pw.text.toString()
            val pwCheck = act_signup_et_pw_check.text.toString()


            if (id.isEmpty() || pw.isEmpty() || pwCheck.isEmpty()) {
                Toast.makeText(this, "아이디와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {

                //id, pw, pwcheck 모두 입력시에만 활성화 되도록 수정해야함
                if (idFlag && pwFlag && pwCheckFlag) {
                    act_signup_cl_join.setBackgroundResource(R.drawable.act_place_round_rect_blue_full)

                    Login.setUser(this, id)

                    val intent = Intent(this, SigninActivity::class.java)
                    intent.putExtra("login", id)

                    startActivity(intent)
                }
            }

        }

    }

    private fun idDuplicatedCheck() {
        act_signup_et_id.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (p0!!.length > 0) {
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
                if (p0!!.length > 0) {

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

                } else {
                    act_signup_cl_pw.setBackgroundResource(R.drawable.act_signup_round_rect_gray)
                    pwFlag = false
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })
    }

    private fun passwordCorretCheck() {
        val pw = act_signup_et_pw.text.toString()

        act_signup_et_pw_check.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (pw !== p0.toString()) {
                    act_signup_tv_pw_check_ment.showOrInvisible(true)
                    act_signup_cl_pw_check.setBackgroundResource(R.drawable.act_signup_round_rect_red)
                    pwCheckFlag = false
                }else {
                    act_signup_tv_pw_check_ment.showOrInvisible(false)
                    act_signup_cl_pw_check.setBackgroundResource(R.drawable.act_signup_round_rect_blue)
                    act_signup_et_pw_check.setTextColor(ContextCompat.getColor(this@SignupActivity, R.color.black))
                    pwCheckFlag = true
                }
            }
        })
    }


    fun View.showOrInvisible(show: Boolean) {
        visibility = if (show) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
    }

}


