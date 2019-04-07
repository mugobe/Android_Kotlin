package com.example.louiville.mechat

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        we log these inputs into the debug terminal to see if we load them

//        Log.d("RegisterActivity", "the emial is:" + email)
//        Log.d("RegisterActivity", "the password is:" + password)


//        we create thehe button onclick, but we du that buy referancing it
        Register_Button.setOnClickListener {
           performRegistration()
        }

        Login_Link_Textview.setOnClickListener {
            Log.d("RegisterActivity", "Please you will be redireced soon")
//       we gona launch an intent
            val intent = Intent(this, LoginActivity::class.java )
            startActivity(intent)
        }

//        pick an image from the storage
        image_picker.setOnClickListener {
            Log.d("RegisterActivity", "You are selecting apicture")

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
         }
        }
        override fun onActivityResult(requestCode:Int, resultCode: Int, data: Intent?){
             super.onActivityResult(requestCode, resultCode, data)

            if(requestCode==0 && resultCode == Activity.RESULT_OK && data != null){
//                    check if the image was seleted
                Log.d("Register", "The i mage was selectd")

            }

    }

    private fun performRegistration(){

        //      we declare variables that point to our input fields in the main activity xml
        val email = Email_Register.text.toString()
        val password = Password_Register.text.toString()

//          if user enters blank spces in the email or pasword prevent crash by this check up
        if (email.isBlank() || password.isBlank()){

            Toast.makeText(this, "please enter text in both email and password fields", Toast.LENGTH_SHORT).show()
            return
        }
        Log.d("RegisterActivity", "the emial is:" + email)
        Log.d("RegisterActivity", "the password is:" + password)

//            firebase Auntehntication
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener
                //else if suuccessful
                Log.d("main", "successfully created uer with uid:")
                Toast.makeText(this, "successfully created user", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{
                Log.d("main", "Please enter corect credentials: ${it.message}")
                Toast.makeText(this, "Failed to login because of wrong input", Toast.LENGTH_SHORT).show()
            }
    }
}
