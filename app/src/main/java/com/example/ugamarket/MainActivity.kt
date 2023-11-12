package com.example.ugamarket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //ahntd 주석달기 테스트
        //ㅎㅇ
        //ahntd 주석달기 테스트2

        val signInFragment = SignInFragment()
        val signUpFragment = SignUpFragment()
    }


}