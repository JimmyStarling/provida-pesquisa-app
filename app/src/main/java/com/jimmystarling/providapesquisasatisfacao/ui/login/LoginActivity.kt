package com.jimmystarling.providapesquisasatisfacao.ui.login

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
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

    lateinit var pesquisa: PesquisaEntity

    lateinit var context: Context
    lateinit var activityIniciarPesquisa: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        context = this@LoginActivity

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        val name = binding.nameResearcher
        val password = binding.passwordResearcher
        val login = binding.login
        val signup = binding.cadastrar

        var mPesquisador: PesquisadorEntity?
        var pesquisasCount: Int? = 0

        val pesquisadorName = name.text
        val pesquisadorPassword = password.text

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
                    pesquisadorName.toString().trim(),
                    pesquisadorPassword.toString().trim()
                )!!.observe(this, { pesquisador ->
                    if (pesquisador == null) {
                        Toast.makeText(
                            context,
                            getString(R.string.wrong_user_inputs),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        loginViewModel.searchPesquisadorPesquisas(context, pesquisador)!!
                            .observe(this, { pesquisas ->
                                Toast.makeText(
                                    context,
                                    "Bem vindo novamente! Sr(a) ${pesquisador.name}, suas pesquisas são ${gson.toJson(pesquisas)}",
                                    Toast.LENGTH_SHORT
                                ).show()
                                Log.d("DEBUG", "Pesquisas do Pesquisador : ${gson.toJson(pesquisas)}")
                                pesquisas.forEach{
                                    pesquisasCount = pesquisasCount?.plus(1)
                                }
                            })
                        mPesquisador = PesquisadorEntity(
                            pesquisadorName.toString().trim(),
                            pesquisadorPassword.toString().trim(),
                            pesquisasCount!!
                        )
                        Log.d("DEBUG", "Pesquisador: ${gson.toJson(pesquisador)}")
                        activityIniciarPesquisa =
                            Intent(this, ActivityIniciarPesquisa::class.java).apply {
                                putExtra(RESEARCHER, gson.toJson(mPesquisador))
                            }
                        startActivity(activityIniciarPesquisa)
                    }
                })
            }
        }

        signup.setOnClickListener {
            if (name.text.isEmpty() || password.text.isEmpty()) {
                Toast.makeText(
                    context,
                    R.string.empty_fills_message,
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                loginViewModel.searchPesquisador(
                    context,
                    pesquisadorName.trim().toString(),
                    pesquisadorPassword.trim().toString()
                )!!.observe(this, { pesquisador ->
                    if (pesquisador != null) {
                        Toast.makeText(
                            context,
                            R.string.already_registered_message,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        signupPesquisador(pesquisadorName.trim().toString(), pesquisadorPassword.trim().toString())
                        activityIniciarPesquisa =
                            Intent(this, ActivityIniciarPesquisa::class.java).apply {
                                putExtra(RESEARCHER, gson.toJson(pesquisador))
                            }
                        startActivity(intent)
                    }
                })
            }
        }
    }
    private fun signupPesquisador(name: String, password: String) {
        val mPesquisadorEntity = PesquisadorEntity(
            name,
            password,
            0,
        )
        loginViewModel.registerPesquisador(context, mPesquisadorEntity)
        Toast.makeText(context, getString(R.string.sucessful_signup), Toast.LENGTH_SHORT)
            .show()

        activityIniciarPesquisa =
            Intent(this, ActivityIniciarPesquisa::class.java).apply {
                putExtra(RESEARCHER, gson.toJson(mPesquisadorEntity))
            }
        startActivity(activityIniciarPesquisa)
    }
    companion object {
        var gson = Gson()
        const val RESEARCHER = "RESEARCHER"
    }
}
