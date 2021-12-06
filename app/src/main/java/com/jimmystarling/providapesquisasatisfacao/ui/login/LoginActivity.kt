package com.jimmystarling.providapesquisasatisfacao.ui.login

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast

import com.google.gson.Gson
import com.jimmystarling.providapesquisasatisfacao.R
import com.jimmystarling.providapesquisasatisfacao.data.model.PesquisaEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.PesquisadorEntity
import com.jimmystarling.providapesquisasatisfacao.databinding.ActivityLoginBinding
import com.jimmystarling.providapesquisasatisfacao.ui.ActivityIniciarPesquisa

import com.jimmystarling.providapesquisasatisfacao.ui.login.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    lateinit var pesquisador: PesquisadorEntity
    lateinit var pesquisa: PesquisaEntity

    lateinit var context: Context
    lateinit var activityIniciarPesquisa: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        context = this@LoginActivity

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        val name = binding.username
        val password = binding.password
        val login = binding.login
        val cadastrar = binding.cadastrar

        val pesquisadorName = name.text.toString().trim()
        val pesquisadorPassword = password.text.toString().trim()

        login.setOnClickListener {
            if (name.text.isEmpty() || password.text.isEmpty()) {
                Toast.makeText(
                    context,
                    R.string.empty_fills_message,
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                loginViewModel.searchPesquisador(
                    context,
                    pesquisadorName,
                    pesquisadorPassword
                )!!.observe(this, { pesquisador ->
                    if (pesquisador == null) {
                        Toast.makeText(
                            context,
                            "Usuario ou senha incorretos!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        searchPesquisas(pesquisador)
                        activityIniciarPesquisa =
                            Intent(this, ActivityIniciarPesquisa::class.java).apply {
                                putExtra(PESQUISADOR, gson.toJson(pesquisador))
                            }
                        startActivity(intent)
                    }
                    this.pesquisador = pesquisador
                })
            }
        }

        cadastrar.setOnClickListener {
            if (name.text.isEmpty() || password.text.isEmpty()) {
                Toast.makeText(
                    context,
                    R.string.empty_fills_message,
                    Toast.LENGTH_SHORT
                ).show()
            } else if (it != null) {
                Toast.makeText(
                    context,
                    R.string.already_registered_message,
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                pesquisador = PesquisadorEntity(
                    pesquisadorName,
                    pesquisadorPassword,
                    0,
                )
                signupPesquisador(pesquisador)

                activityIniciarPesquisa =
                    Intent(this, ActivityIniciarPesquisa::class.java).apply {
                        putExtra(PESQUISADOR, gson.toJson(pesquisador))
                    }
                startActivity(activityIniciarPesquisa)
            }
        }
    }

    private fun signupPesquisador(pesquisador: PesquisadorEntity) {
        loginViewModel.registerPesquisador(context, pesquisador)
        Toast.makeText(context, "Você foi cadastrado com sucesso!", Toast.LENGTH_SHORT)
            .show()
    }

    private fun searchPesquisas(pesquisador: PesquisadorEntity) {
        loginViewModel.searchPesquisadorPesquisas(context, pesquisador)!!
            .observe(this, { pesquisas ->
                Toast.makeText(
                    context,
                    "Bem vindo novamente! Sr(a) ${this.pesquisador.name}, suas pesquisas são ${
                        gson.toJson(
                            pesquisas
                        )
                    }",
                    Toast.LENGTH_SHORT
                ).show()
            })
    }

    companion object {
        var gson = Gson()
        const val PESQUISADOR = "PESQUISADOR"
    }
}
