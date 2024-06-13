package com.example.dogbreeds

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var etUserName: EditText
    private lateinit var etPass: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

        etUserName = findViewById(R.id.etUserName)
        etPass = findViewById(R.id.etPass)
        btnLogin = findViewById(R.id.btnLogin)
        btnRegister = findViewById(R.id.btnRegister)

        btnLogin.setOnClickListener {
            if (etUserName.text.isNullOrEmpty() || etPass.text.isNullOrEmpty()) {
                Toast.makeText(this, "Faltan Datos", Toast.LENGTH_SHORT).show()
            }
            else {
                val sharedPref = this.getSharedPreferences(
                    "credenciales", Context.MODE_PRIVATE)
                val userName = sharedPref.getString("userName", "sinRegistrar")
                val passWord = sharedPref.getString("pass", "sinRegistrar")

                if (userName.equals("sinRegistrar")) {
                    startActivity(Intent(this, RegisterActivity::class.java))
                }

                if (userName.equals(etUserName.text.toString()) && passWord.equals(etPass.text.toString())) {
                    startActivity(Intent(this, MainActivity::class.java))
                }
                else {
                    Toast.makeText(this, "Datos No Validos", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}