package com.example.myproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AuthActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auth_activity)

        val userLogin: EditText = findViewById(R.id.user_login)
        val userPassword: EditText = findViewById(R.id.user_password)
        val button: Button = findViewById(R.id.button)
        val linkToReg: TextView = findViewById(R.id.link_to_ref)

        linkToReg.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        button.setOnClickListener(){
            val login = userLogin.text.toString().trim()
            val password = userPassword.text.toString().trim()

            if (login == "" || password == "" )
                Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_LONG).show()
            if (password.length < 8) {
                Toast.makeText(this, "Пароль должен быть не менее 8 символов", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            else {
                val db = DBHelper(this, null)
                val isAuth = db.getUser(login, password)


            if(isAuth){
                Toast.makeText(this, "Добро пожаловать, $login!", Toast.LENGTH_LONG).show()
                val intent = Intent(this, ItemsActivity::class.java)
                intent.putExtra("user_login", login)
              startActivity(intent)
                userLogin.text.clear()
                userPassword.text.clear()
            }else{
                Toast.makeText(this, "Неверный логин или пароль ", Toast.LENGTH_LONG).show()
            }

        }
    }
}}