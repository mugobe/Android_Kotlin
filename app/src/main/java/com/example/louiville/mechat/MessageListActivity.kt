package com.example.louiville.mechat

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MessageListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_list)

//   check if user is regiseted

        varifyUser()
    }

//    function that hundles varify user, it checks if the user is registered in firebase if not redirectas to the register page activity
    private fun varifyUser(){

        val uuid = FirebaseAuth.getInstance().uid
        if (uuid ==null){

            val intent = Intent(this, RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
//        we write awitch statement that allows to trigger calls on the buttons
//            trigger fuction to create new message
            R.id.new_chat ->{


            }
            R.id.sign_out_menu ->{
//                redirect to the login page on press
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)

            }

        }
        return super.onContextItemSelected(item)
    }


//    overiding the activity message list to create buttons ontop

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_message_nav, menu)
        return super.onCreateOptionsMenu(menu)
    }


}
