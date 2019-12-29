package com.devaon.early_buddy_android.feature

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.devaon.early_buddy_android.PlaceFavoriteActivity
import com.devaon.early_buddy_android.R
import kotlinx.android.synthetic.main.activity_set_nickname.*
import java.security.MessageDigest
import java.util.regex.Pattern


class SetNicknameActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_nickname)


        makeController()
    }

    private fun makeController() {

        act_set_nickname_et_id.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(p0!!.length > 0){
                    act_set_nickname_cl_id.setBackgroundResource(R.drawable.act_place_round_rect_blue)
                    act_set_nickname_cl_join.setBackgroundResource(R.drawable.act_place_round_rect_blue_full)
                    act_set_nickname_bt_join.setTextColor(ContextCompat.getColor(this@SetNicknameActivity, R.color.white))
                    act_set_nickname_et_id.setTextColor(ContextCompat.getColor(this@SetNicknameActivity, R.color.black))
                }else {
                    act_set_nickname_cl_id.setBackgroundResource(R.drawable.act_place_round_rect_gray)
                    act_set_nickname_cl_join.setBackgroundResource(R.drawable.act_place_round_rect_gray_full)
                    act_set_nickname_bt_join.setTextColor(ContextCompat.getColor(this@SetNicknameActivity, R.color.gray))
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })


        act_set_nickname_cl_join?.setOnClickListener {
            val patternNickName: Pattern = Pattern.compile("^[ㄱ-ㅣ가-힣]*$")

            if (patternNickName.matcher(act_set_nickname_et_id.text.toString()).matches()) {
                val intent = Intent(this@SetNicknameActivity, PlaceFavoriteActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this@SetNicknameActivity, "한글만 입력해주세요", Toast.LENGTH_SHORT).show()
            }


        }



    }







}