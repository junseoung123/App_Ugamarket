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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

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
        val editTextNameSignUp = view.findViewById<EditText>(R.id.editTextNameSignUp) // 이름 에딧 텍스트
        val editTextBirthSignUp =
            view.findViewById<EditText>(R.id.editTextBirthSignUp) // 생년월일 에딧 텍스트
        val buttonSignUp2 = view.findViewById<Button>(R.id.buttonSignUp2) // 회원가입 버튼


        buttonSignUp2.setOnClickListener {
            val email = editTextEmailSignUp.text.toString()
            val password = editTextPasswordSignUp.text.toString()
            val name = editTextNameSignUp.text.toString()
            val birth = editTextBirthSignUp.text.toString()


            if (email.equals("") || password.equals("") || name.equals("") || birth.equals("")) { // 입력 안한 경우
                Toast.makeText(mainActivity, "입력 하세요.", Toast.LENGTH_SHORT).show()
            } else {
                // 입력한 경우
                doSignUp(email, password, name, birth)   // 회원 가입
            }
        }
    }


    private fun doSignUp(userEmail: String, password: String, name: String, birth: String) {
        Firebase.auth.createUserWithEmailAndPassword(userEmail, password)
            .addOnCompleteListener() {
                if (it.isSuccessful) {
                    println("회원가입 성공")


                    Toast.makeText(mainActivity, "회원가입 성공", Toast.LENGTH_SHORT).show()

                    val uid = Firebase.auth.currentUser?.uid
                    if (uid != null) {
                        addUser(uid, userEmail, name, birth)
                    }// 데이터베이스 user 컬렉션에 데이터 추가


                    doSignIn(userEmail, password)   // 회원가입 성공후 로그인
                } else {
                    println("회원가입 실패")
                    Toast.makeText(mainActivity, "회원가입 실패", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun doSignIn(userEmail: String, password: String) {
        Firebase.auth.signInWithEmailAndPassword(userEmail, password)
            .addOnCompleteListener() {
                if (it.isSuccessful) {
                    println("로그인 성공")
                    Toast.makeText(mainActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
                } else {
                    println("로그인 실패")
                    Toast.makeText(mainActivity, "로그인 실패", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUser(uid: String, userEmail: String, name: String, birth: String) {
        val db: FirebaseFirestore = Firebase.firestore
        val usersCollectionRef = db.collection("users")
        val userMap = hashMapOf(
            "uid" to uid,
            "userEmail" to userEmail,
            "name" to name,
            "birth" to birth
        )
        usersCollectionRef.add(userMap).addOnSuccessListener {
            println("users 컬렉션에 추가함")
        }.addOnFailureListener {
            println("users 컬렉션에 추가 실패 함")
        }
    }
}
