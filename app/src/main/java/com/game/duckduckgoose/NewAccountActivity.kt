package com.game.duckduckgoose

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar

class NewAccountActivity : AppCompatActivity() {
    private lateinit var myViewModel: NewAccountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_new_account)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        myViewModel = ViewModelProvider(this)[NewAccountViewModel::class.java]

        val cancelBtn = findViewById<Button>(R.id.cancel_button)
        val createBtn = findViewById<Button>(R.id.create_button)

        val username = findViewById<EditText>(R.id.username)
        val email = findViewById<EditText>(R.id.email_address)
        val actualPassword = findViewById<EditText>(R.id.password)
        val password = findViewById<EditText>(R.id.verified_password)

        myViewModel.isCreated.observe(this){
            if (myViewModel.isCreated.value!!) {
                val toMain = Intent(this, MainActivity::class.java)
                toMain.putExtra("uid", myViewModel.auth.uid.toString())

                finish()
                startActivity(toMain)
            } else
                if(!myViewModel.isCreated.value!!)
                    Snackbar.make(
                        username, "Error: A server error has occurred.",
                        Snackbar.LENGTH_LONG
                    ).show()
        }

        myViewModel.account.observe(this){
            if (it && myViewModel.isInitialized) {
                myViewModel.createNewUserDoc(username.text.toString())
            } else {
                if(myViewModel.isInitialized)
                    Snackbar.make(
                        username, "Error: Account could not be created.",
                        Snackbar.LENGTH_LONG
                    ).show()
            }
        }

        createBtn.setOnClickListener{
            if(actualPassword.text.toString() != password.text.toString())
                Snackbar.make(
                    password, "Error: Passwords do not match.",
                    Snackbar.LENGTH_LONG
                ).show()
            else if(actualPassword.text.toString().isEmpty() &&
                password.text.toString().isEmpty())
                Snackbar.make(
                    password, "Error: Please enter a password.",
                    Snackbar.LENGTH_LONG
                ).show()
            else if(email.text.toString().isEmpty())
                Snackbar.make(
                    password, "Error: Please enter an email address.",
                    Snackbar.LENGTH_LONG
                ).show()
            else if(username.text.toString().isEmpty())
                Snackbar.make(username, "Error: Please enter a nickname.",
                    Snackbar.LENGTH_LONG).show()
            else
                myViewModel.doSignUp(email.text.toString(), password.text.toString())
        }

        cancelBtn.setOnClickListener {
            finish()
        }
    }

}