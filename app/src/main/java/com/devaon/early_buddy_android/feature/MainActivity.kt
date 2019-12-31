package com.devaon.early_buddy_android.feature

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.devaon.early_buddy_android.R
import com.devaon.early_buddy_android.feature.user.SignupActivity
import com.devaon.early_buddy_android.feature.home.HomeActivity
import com.devaon.early_buddy_android.feature.initial_join.SetNicknameActivity
import com.devaon.early_buddy_android.feature.user.SigninActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        home.setOnClickListener{
            val intent = Intent(this@MainActivity, HomeActivity::class.java)
            startActivity(intent)
        }

        setnickname.setOnClickListener{
            val intent = Intent(this@MainActivity, SetNicknameActivity::class.java)
            startActivity(intent)
        }

        test.setOnClickListener{
            val intent = Intent(this@MainActivity, SignupActivity::class.java)
            startActivity(intent)
        }

//        val animationView = findViewById<LottieAnimationView>(R.id.animationView)
//        animationView.setAnimation("testtt.json")
//        animationView.loop(true)
//        animationView.playAnimation()
//        animationView.addAnimatorListener(object : Animator.AnimatorListener {
//            override fun onAnimationStart(animation: Animator) {
//            }
//
//            override fun onAnimationEnd(animation: Animator) {
//            }
//
//            override fun onAnimationCancel(animation: Animator) {
//            }
//
//            override fun onAnimationRepeat(animation: Animator) {
//            }
//        })

    }
}
