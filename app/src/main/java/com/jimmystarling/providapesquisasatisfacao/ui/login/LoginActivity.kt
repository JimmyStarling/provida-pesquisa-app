package com.jimmystarling.providapesquisasatisfacao.ui.login

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast

import com.google.gson.Gson
import com.jimmystarling.providapesquisasatisfacao.data.model.PesquisaEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.PesquisadorEntity
import kotlinx.serialization.json.Json
import com.jimmystarling.providapesquisasatisfacao.databinding.ActivityLoginBinding

import com.jimmystarling.providapesquisasatisfacao.ui.login.viewmodel.LoginViewModel
import com.jimmystarling.providapesquisasatisfacao.ui.questions.PesquisaActivity
import kotlinx.serialization.decodeFromString

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    lateinit var pesquisador: PesquisadorEntity

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
        val cadastrar = binding.cadastrar
        val loading = binding.loading

        lateinit var strName: String
        lateinit var strPassword: String

        login.setOnClickListener {
            loginViewModel.getPesquisador(
                context,
                username.text.toString().trim(),
                password.text.toString().trim()
            )!!.observe(this, {
                if (it == null){
                    Toast.makeText(
                        context,
                        "Usuario ou senha incorretos!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    pesquisador = it

                    strName = it.name
                    strPassword = it.password

                    val pesquisas = Json.decodeFromString<PesquisaEntity>(it.pesquisas)

                    Toast.makeText(
                        context,
                        "Bem vindo novamente! Sr(a) ${strName}, suas pesquisas são ${pesquisas}",
                        Toast.LENGTH_SHORT
                    ).show()

                    val intent = Intent(this, PesquisaActivity::class.java).apply {
                        putExtra(PESQUISADOR, gson.toJson(pesquisador))
                    }
                    startActivity(intent)
                }
            })
        }
        cadastrar.setOnClickListener{
            if(password.text.isEmpty() || username.text.isEmpty()){
                Toast.makeText(
                    context,
                    "Porfavor, preencha todos os campos.",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (it == null){
                Toast.makeText(
                    context,
                    "Você já está cadastrado(a)! Porfavor realize o login com sua senha.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val nomePaciente: String = username.text.toString().trim()
                val senhaPaciente: String = password.text.toString().trim()
                pesquisador = PesquisadorEntity(
                    nomePaciente,
                    pesquisas = "",
                    pesquisas_quantidade = 0,
                    senhaPaciente
                )

                loginViewModel.registerPesquisador(context, nomePaciente, senhaPaciente)
                Toast.makeText(context, "Você foi cadastrado com sucesso!", Toast.LENGTH_SHORT)
                    .show()

                val intent = Intent(this, PesquisaActivity::class.java).apply {
                    putExtra(Companion.PESQUISADOR, gson.toJson(pesquisador))
                }
                startActivity(intent)
            }
        }


    }

    companion object {

        var gson = Gson()
        const val PESQUISADOR = "PESQUISADOR"
    }
}
