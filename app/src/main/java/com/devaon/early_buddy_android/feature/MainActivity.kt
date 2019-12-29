package com.devaon.early_buddy_android.feature

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.devaon.early_buddy_android.PlaceFavoriteActivity
import com.devaon.early_buddy_android.R
import com.devaon.early_buddy_android.SignupActivity
import com.devaon.early_buddy_android.TestActivity
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

<<<<<<< HEAD
=======

        test.setOnClickListener{
            val intent = Intent(this@MainActivity, SignupActivity::class.java)
            startActivity(intent)
        }


>>>>>>> 116e6349524aa86423af0e11f3f444ada3013d6b
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
