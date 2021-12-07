package com.jimmystarling.providapesquisasatisfacao.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.jimmystarling.providapesquisasatisfacao.R
import com.jimmystarling.providapesquisasatisfacao.data.model.PacienteEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.PesquisadorEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.QuestaoEntity
import com.jimmystarling.providapesquisasatisfacao.data.repository.PesquisaRepository
import com.jimmystarling.providapesquisasatisfacao.databinding.ActivityIniciarPesquisaBinding
import com.jimmystarling.providapesquisasatisfacao.databinding.ActivityIniciarPesquisaBinding.*
import com.jimmystarling.providapesquisasatisfacao.databinding.ActivityLoginBinding
import com.jimmystarling.providapesquisasatisfacao.ui.login.LoginActivity
import com.jimmystarling.providapesquisasatisfacao.ui.questions.PesquisaActivity
import com.jimmystarling.providapesquisasatisfacao.ui.questions.QuestionFragment
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.text.SimpleDateFormat
import java.util.*

class ActivityIniciarPesquisa : AppCompatActivity() {

    private lateinit var binding: ActivityIniciarPesquisaBinding
    lateinit var context: Context
    lateinit var activityPesquisa: Intent

    lateinit var mPesquisadorEntity: String

    lateinit var pesquisador: PesquisadorEntity
    lateinit var paciente: PacienteEntity

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iniciar_pesquisa)

        context = this@ActivityIniciarPesquisa

        binding = inflate(layoutInflater)
        setContentView(binding.root)

        val nome_paciente = binding.nomePaciente
        val numero_paciente = binding.numeroPaciente
        val button_iniciar = binding.btnComecar


        val pacienteName: String? = nome_paciente.text.toString().trim()
        val pacienteContact: String? = numero_paciente.text.toString().trim()

        button_iniciar.setOnClickListener {
            if (pacienteName == null || pacienteContact == null){
                Toast.makeText(
                    context,
                    "Por favor, preencha todos os campos.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                paciente = PacienteEntity(
                    name = pacienteName,
                    contato = pacienteContact
                )
                Log.d("DEBUG", "PACIENTE IS: ${gson.toJson(paciente)}")
                pesquisador = getPesquisador(this.intent)
                activityPesquisa = Intent(this, PesquisaActivity::class.java).apply {
                    putExtra(PACIENTE, gson.toJson(paciente))
                    putExtra(PESQUISADOR, gson.toJson(pesquisador))
                }
                startActivity(activityPesquisa)
            }
        }
    }

    private fun getPesquisador(intent: Intent): PesquisadorEntity {
        mPesquisadorEntity = intent.getStringExtra("PESQUISADOR").toString()
        pesquisador = Json.decodeFromString<PesquisadorEntity>(mPesquisadorEntity)
        return pesquisador
    }

    companion object{

        var gson = Gson()
        const val PESQUISADOR = "PESQUISADOR"
        const val PACIENTE = "PACIENTE"
    }
}