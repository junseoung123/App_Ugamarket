package com.example.ugamarket

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class SignInFragment : Fragment(R.layout.sign_in_fragment) {
    lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editTextEmail = view.findViewById<EditText>(R.id.editTextEmail) //이메일 에딧 텍스트
        val editTextPassword = view.findViewById<EditText>(R.id.editTextPassword) //비밀번호 에딧 텍스트
        val buttonSignIn = view.findViewById<Button>(R.id.buttonSignIn) // 로그인 버튼
        val buttonSignUp = view.findViewById<Button>(R.id.buttonSignUp) // 회원가입 버튼


        buttonSignIn.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            if (email.equals("") || password.equals("")) { // 입력 안한 경우
                Toast.makeText(mainActivity, "입력 하세요.", Toast.LENGTH_SHORT).show()
            } else { // 입력한 경우
                doSignIn(email, password)
            }
        }


        buttonSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }
    }


    private fun doSignIn(userEmail: String, password: String) {
        Firebase.auth.signInWithEmailAndPassword(userEmail, password)
            .addOnCompleteListener() {
                if (it.isSuccessful) {
                    println("로그인 성공")
                    Toast.makeText(mainActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
                    goToPostList(mainActivity) //로그인 성공 후 판매 글 보기로 넘어가기
                } else {
                    println("로그인 실패")
                    Toast.makeText(mainActivity, "로그인 실패", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun goToPostList(context: Context) {
        val intent = Intent(context, PostListActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }
}