package com.example.louiville.mechat

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import java.util.*
import kotlin.jvm.internal.Ref

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

//    this code helps fetch the image from the fone memeroy
        var selectPhotoUri: Uri? = null

        override fun onActivityResult(requestCode:Int, resultCode: Int, data: Intent?){
             super.onActivityResult(requestCode, resultCode, data)

            if(requestCode==0 && resultCode == Activity.RESULT_OK && data != null){
//                    check if the image was seleted
                Log.d("Register", "The i mage was selectd")

//                show selected image in the round conner

                selectPhotoUri = data.data
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectPhotoUri)


                imageSelectCircle.setImageBitmap(bitmap)

                image_picker.alpha = 0f
//                val bitmapDrawable = BitmapDrawable(bitmap)
//                image_picker.setBackgroundDrawable(bitmapDrawable)

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
                Log.d("main", "successfully created user with uid: ${it}")
                Toast.makeText(this, "successfully created user", Toast.LENGTH_SHORT).show()

//              upload images to firebase
                uploadImageToFirebase()
            }
            .addOnFailureListener{
                Log.d("Register", "Please enter corect credentials: ${it.message}")
                Toast.makeText(this, "Failed to login because of wrong input", Toast.LENGTH_SHORT).show()
            }
    }
// we upload the photo duing registering
    private fun uploadImageToFirebase(){
        if (selectPhotoUri == null) return
    val filename = UUID.randomUUID().toString()
   val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

    ref.putFile(selectPhotoUri!!)
        .addOnSuccessListener {
            Log.d("Register", "Suscessfully uploaded images:  ${it.metadata?.path}")

            ref.downloadUrl.addOnSuccessListener {
                Log.d("Register", "file path : ${it}")

//                save this image to the database plus its user linked
                saveUserProfileToFirebaseDatabase(it.toString())

            }
                .addOnFailureListener{

                    Log.d("Register", "Fialed to save image :${it.cause}")
                }

        }

    }

    private fun saveUserProfileToFirebaseDatabase(profileImageUrl:String){
//       reference the user unique id generated during the firbease authentication protocol
        val uid = FirebaseAuth.getInstance().uid ?: ""

//        reference the firebase database to which all the user data will be stored
        val ref =FirebaseDatabase.getInstance().getReference("/users/$uid")
//create a user from the user class whic consis of the three user fields
        val user = User(uid, Username_Register.text.toString(), profileImageUrl )
//do the saving
        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("Register", "FINALLY WE SAVED THE USER TO DATABASE")

//          redireting to the list view containg mesages after registration WE DO THIS BY CREATING AN INTENT
                val intent = Intent(this, MessageListActivity::class.java)
//                 clear off block staff like forward pages and so
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)

            }
            .addOnFailureListener{

                Log.d("Register", "Failed to save user :${it.cause}")
            }
    }
}
//user class
class User(val uid:String, val username: String, val profileImageUrl: String)