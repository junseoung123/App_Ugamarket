package com.example.ugamarket

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class SignUpFragment : Fragment(R.layout.sign_up_fragment) {
    lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editTextEmailSignUp = view.findViewById<EditText>(R.id.editTextEmailSignUp) //이메일 에딧 텍스트
        val editTextPasswordSignUp =
            view.findViewById<EditText>(R.id.editTextPasswordSignUp) // 비밀번호 에딧 텍스트
        val editTextBirthSignUp =
            view.findViewById<EditText>(R.id.editTextBirthSignUp) // 생년월일 에딧 텍스트
        val buttonSignUp2 = view.findViewById<Button>(R.id.buttonSignUp2) // 회원가입 버튼


        buttonSignUp2.setOnClickListener {
            val email = editTextEmailSignUp.text.toString()
            val password = editTextPasswordSignUp.text.toString()
            val birth = editTextBirthSignUp.text.toString()

            if (email.equals("") || password.equals("") || birth.equals("")) { // 입력 안한 경우
                Toast.makeText(mainActivity, "입력 하세요.", Toast.LENGTH_SHORT).show()
            } else {
                // 입력한 경우
                doSignUp(email, password)
            }
        }
    }


    private fun doSignUp(userEmail: String, password: String) {
        Firebase.auth.createUserWithEmailAndPassword(userEmail, password)
            .addOnCompleteListener() {
                if (it.isSuccessful) {
                    println("회원가입 성공")
                    Toast.makeText(mainActivity, "회원가입 성공", Toast.LENGTH_SHORT).show()
                } else {
                    println("회원가입 실패")
                    Toast.makeText(mainActivity, "회원가입 실패", Toast.LENGTH_SHORT).show()
                }
            }
    }
}