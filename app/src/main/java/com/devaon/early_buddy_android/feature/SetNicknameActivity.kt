package com.devaon.early_buddy_android.feature

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Constraints
import androidx.core.content.ContextCompat
import com.devaon.early_buddy_android.R
import kotlinx.android.synthetic.main.activity_set_nickname.*

class SetNicknameActivity : AppCompatActivity() {


//    private var btSetNicknameNext: Constraints? = null
//    private var etSetNickname: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_nickname)

        makeController()
    }


    private fun makeController() {
//        btSetNicknameNext = findViewById(R.id.cl_set_nickname_next)
//        etSetNickname = findViewById(R.id.tv_set_nickname_write)

//        var btSetNicknameNext = cl_set_nickname_next

//        tv_set_nickname_write.onFocusChangeListener = object : View.OnFocusChangeListener {
//            override fun onFocusChange(p0: View?, p1: Boolean) {
//                if(p1) {
//                    cl_set_nickname_next.setBackgroundResource(R.drawable.selector_set_nickname_next_bg)
//                }
//            }
//        }
        tv_set_nickname_write.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(p0!!.length > 0){
                    cl_set_nickname_next.setBackgroundResource(R.drawable.round_rect_blue_full)
                    //bt_set_nickname_next.setTextColor(Color.GRAY)
                    bt_set_nickname_next.setTextColor(ContextCompat.getColor(this@SetNicknameActivity, R.color.white))
                }else {
                    cl_set_nickname_next.setBackgroundResource(R.drawable.round_rect_gray)
                    bt_set_nickname_next.setTextColor(ContextCompat.getColor(this@SetNicknameActivity, R.color.gray))
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })


        cl_set_nickname_next?.setOnClickListener {
            //val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
            //startActivity(intent)
        }

    }
}