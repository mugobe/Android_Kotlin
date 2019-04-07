package com.example.louiville.mechat

import android.content.Intent
import android.os.Bundle

import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


//create an oncliick listener

        Login_Button.setOnClickListener {
            performLogin()

        }
        //            switching to register page

        Login_Register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java )
            startActivity(intent)
        }

    }

    private fun performLogin(){

        //collect and test inputs in our LoginIEW
        val login_email = Login_Email.text.toString()
        val login_password = Login_Password.text.toString()


//        outputting them
        Log.d("Login Activity", "login input email is :" + login_email)
        Log.d("login Activity", "login password inputed is :"+ login_password)

        FirebaseAuth.getInstance().signInWithEmailAndPassword(login_email, login_password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener
                //else if suuccessful
                Log.d("main", "successfully login")
                Toast.makeText(this, "login successful", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{
                Log.d("main", "Please enter corect credentials: ${it.message}")
                Toast.makeText(this, "login failed", Toast.LENGTH_SHORT).show()

            }
    }
}