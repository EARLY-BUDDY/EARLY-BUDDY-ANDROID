package com.devaon.early_buddy_android.feature

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.Spanned
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.devaon.early_buddy_android.PlaceFavoriteActivity
import com.devaon.early_buddy_android.R
import kotlinx.android.synthetic.main.activity_set_nickname.*
import java.util.regex.Pattern


class SetNicknameActivity : AppCompatActivity() {


    private fun makeController() {

        tv_set_nickname_write.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(p0!!.length > 0){
                    cl_set_nickname_next.setBackgroundResource(R.drawable.act_place_round_rect_blue_full)
                    bt_set_nickname_next.setTextColor(ContextCompat.getColor(this@SetNicknameActivity, R.color.white))
                    tv_set_nickname_write.setTextColor(ContextCompat.getColor(this@SetNicknameActivity, R.color.black))
                }else {
                    cl_set_nickname_next.setBackgroundResource(R.drawable.act_place_round_rect_gray_full)
                    bt_set_nickname_next.setTextColor(ContextCompat.getColor(this@SetNicknameActivity, R.color.gray))
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })


        cl_set_nickname_next?.setOnClickListener {
            val patternNickName: Pattern = Pattern.compile("^[ㄱ-ㅣ가-힣]*$")

            if (patternNickName.matcher(tv_set_nickname_write.text.toString()).matches()) {
                val intent = Intent(this@SetNicknameActivity, PlaceFavoriteActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this@SetNicknameActivity, "한글만 입력해주세요", Toast.LENGTH_SHORT).show()
            }


        }

    }






}