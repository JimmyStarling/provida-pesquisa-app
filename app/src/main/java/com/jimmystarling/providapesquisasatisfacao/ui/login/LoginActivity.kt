package com.jimmystarling.providapesquisasatisfacao.ui.login

import android.app.Activity
import android.content.Context
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import com.jimmystarling.providapesquisasatisfacao.databinding.ActivityLoginBinding

import com.jimmystarling.providapesquisasatisfacao.R
import com.jimmystarling.providapesquisasatisfacao.ui.login.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        context = this@LoginActivity

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        val username = binding.username
        val password = binding.password
        val login = binding.login
        //val loading = binding.loading

        lateinit var strName: String
        lateinit var strPassword: String

        login.setOnClickListener{
            loginViewModel.getPesquisador(context, username.text.toString().trim(), password.text.toString().trim())!!.observe(this, {
                strName = username.text.toString().trim()
                strPassword = password.text.toString().trim()
                when {
                    it == null -> {
                        when {
                            username.text.isEmpty() -> {
                                Toast.makeText(
                                    context,
                                    "Porfavor insira seu nome de usuario!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            password.text.isEmpty() -> {
                                Toast.makeText(
                                    context,
                                    "Porfavor insira sua senha!",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                            else -> {
                                loginViewModel.registerPesquisador(context, username.text.toString().trim(), password.text.toString().trim())
                                Toast.makeText(context, "Bem vindo!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    else -> {
                        strName = it.Name
                        strPassword = it.Password

                        Toast.makeText(context, "Bem vindo novamente!", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }


    }
}
