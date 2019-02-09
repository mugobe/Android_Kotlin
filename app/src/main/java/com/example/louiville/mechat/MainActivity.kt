package com.example.louiville.mechat

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        we log these inputs into the debug terminal to see if we load them

//        Log.d("MainActivity", "the emial is:" + email)
//        Log.d("MainActivity", "the password is:" + password)


//        we create thehe button onclick, but we du that buy referancing it
        Register_Button.setOnClickListener {

            //      we declare variables that point to our input fields in the main activity xml

            val email = Email_Register.text.toString()
            val password = Password_Register.text.toString()


            Log.d("MainActivity", "the emial is:" + email)
            Log.d("MainActivity", "the password is:" + password)
        }

        Login_Link_Textview.setOnClickListener {
            Log.d("MainActivity", "Please you will be redireced soon")

//       we gona launch an intent
            val intent = Intent(this, LoginActivity::class.java )
            startActivity(intent)
        }

    }
}
