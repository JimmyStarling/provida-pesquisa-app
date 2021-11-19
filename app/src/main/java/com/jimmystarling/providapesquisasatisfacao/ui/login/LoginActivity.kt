package com.jimmystarling.providapesquisasatisfacao.ui.login

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.google.gson.Gson
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
                                var nome_paciente: String = username.text.toString().trim()
                                var senha_paciente: String = password.text.toString().trim()
                                pesquisador = PesquisadorEntity(nome_paciente, Pesquisas = "", PesquisasContagem = 0, senha_paciente)

                                loginViewModel.registerPesquisador(context, nome_paciente, senha_paciente)
                                Toast.makeText(context, "Bem vindo!", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, PesquisaActivity::class.java).apply {
                                    putExtra(Companion.PESQUISADOR, gson.toJson(pesquisador))
                                }
                                startActivity(intent)
                            }
                        }
                    }
                    else -> {
                        strName = it.Name
                        strPassword = it.Password
                        var pesquisas = it.Pesquisas
                        pesquisas = Json.decodeFromString(pesquisas)

                        Toast.makeText(context, "Bem vindo novamente! Sr(a) ${strName}, suas pesquisas são ${pesquisas}", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, PesquisaActivity::class.java)
                        startActivity(intent)

                    }
                }
            })
        }


    }

    companion object {

        var gson = Gson()
        const val PESQUISADOR = "PESQUISADOR"
    }
}
