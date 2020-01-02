package com.devaon.early_buddy_android.feature.initial_join

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.devaon.early_buddy_android.R
import com.devaon.early_buddy_android.data.user.UserResponse
import com.devaon.early_buddy_android.network.EarlyBuddyServiceImpl
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_set_nickname.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern


class SetNicknameActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_nickname)


        makeController()
    }

    private fun makeController() {

        val id = act_set_nickname_et_id.text.toString()
        postUserNicknameData(id)


        act_set_nickname_et_id.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(p0!!.length > 0){
                    act_set_nickname_cl_id.setBackgroundResource(R.drawable.act_place_round_rect_blue)
                    act_set_nickname_cl_join.setBackgroundResource(R.drawable.act_place_round_rect_blue_full)
                    act_set_nickname_bt_join.setTextColor(ContextCompat.getColor(this@SetNicknameActivity, R.color.white))
                    act_set_nickname_et_id.setTextColor(ContextCompat.getColor(this@SetNicknameActivity, R.color.black))
                }else {
                    //비활성화 - 초기 비활성화 상태와 똑같이 만들어줌
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
            val nickname = act_set_nickname_et_id.text.toString()

            if (patternNickName.matcher(nickname).matches() && nickname.length > 0) {
                val intent = Intent(this@SetNicknameActivity, PlaceFavoriteActivity::class.java)
                startActivity(intent)
                finish()
            }else if(nickname.length == 0){
                Toast.makeText(this@SetNicknameActivity, "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(this@SetNicknameActivity, "한글만 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }



    }

    private fun postUserNicknameData(userName : String) {

        var jsonObject = JSONObject()
        jsonObject.put("userName", userName)

        val body = JsonParser().parse(jsonObject.toString()) as JsonObject

        val callUserNicknameResponse: Call<UserResponse> = EarlyBuddyServiceImpl.service.postNicknameUser(
            body
        )

        callUserNicknameResponse.enqueue(object : Callback<UserResponse> {
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("error is ", t.toString())
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    Log.e("result is ", response.body().toString())
                    val NicknameUser = response.body()!!
                }
            }
        })

    }



}