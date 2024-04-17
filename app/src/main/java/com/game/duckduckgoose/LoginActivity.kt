package com.game.duckduckgoose

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {
    private lateinit var myViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val intent = Intent(this, GameMusicService::class.java)
        startService(intent)

        myViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        val loginBtn = findViewById<Button>(R.id.login_button)
        val registerBtn = findViewById<Button>(R.id.register_button)

        val email = findViewById<EditText>(R.id.user_email)
        val password = findViewById<EditText>(R.id.user_password)

        email.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(email.text.isNotEmpty() && password.text.isNotEmpty()){
                    loginBtn.isEnabled = true;
                }else{
                    loginBtn.isEnabled = false;
                }
            }

            override fun afterTextChanged(s: Editable?) {}

        })

        password.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loginBtn.isEnabled = email.text.isNotEmpty() && password.text.isNotEmpty()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        myViewModel.userID.observe(this){
            if (it != null && myViewModel.isInitialized) {
                val toMain = Intent(this, ShopActivity::class.java)
                toMain.putExtra("uid", myViewModel.userID.value)

                startActivity(toMain)
            }
            else{
                if(myViewModel.isInitialized)
                    Snackbar.make(loginBtn, "Error: Could not log in", Snackbar.LENGTH_LONG)
                        .show()
            }
        }

        loginBtn.setOnClickListener{
            myViewModel.doLogin(email.text.toString(), password.text.toString())
        }

        registerBtn.setOnClickListener{
            // TODO: Uncomment code below when done testing shop
            val toShop = Intent(this, ShopActivity::class.java)
            startActivity(toShop)

            /* val toRegister = Intent(this, NewAccountActivity::class.java)
            startActivity(toRegister) */
        }
    }
}