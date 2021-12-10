package com.jimmystarling.providapesquisasatisfacao.ui.questions

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.slider.Slider
import com.google.gson.Gson
import com.jimmystarling.providapesquisasatisfacao.R
import com.jimmystarling.providapesquisasatisfacao.data.model.PacienteEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.PesquisaEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.PesquisadorEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.QuestaoEntity
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.jimmystarling.providapesquisasatisfacao.R.*
import com.jimmystarling.providapesquisasatisfacao.ui.ActivityIniciarPesquisa
import com.jimmystarling.providapesquisasatisfacao.ui.questions.PesquisaActivity.Companion.currentDate
import com.jimmystarling.providapesquisasatisfacao.ui.questions.PesquisaActivity.Companion.mTitleContent
import com.jimmystarling.providapesquisasatisfacao.ui.questions.PesquisaActivity.Companion.mTitleQuestion
import java.text.SimpleDateFormat
import java.util.*


class QuestionFragmentGeral : Fragment() {

    lateinit var nextFragment: Fragment

    lateinit var slider: Slider
    var slider_value: String? = null

    lateinit var mButtonContinuar: Button
    lateinit var mButtonVoltar: Button

    // Entities and variables to modelview
    lateinit var mPesquisa: PesquisaEntity
    lateinit var mPesquisador: PesquisadorEntity
    lateinit var mQuestoes: MutableList<QuestaoEntity>
    lateinit var mPaciente: PacienteEntity

    companion object {
        var gson = Gson()
        lateinit var lastFragment: Fragment
    }
    private lateinit var viewModel: PesquisaViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(layout.question_fragment_geral, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PesquisaViewModel::class.java)

        val fragmentManager: FragmentManager = activity!!.supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        slider = view?.findViewById<Slider>(R.id.slider_quality)!!
        mButtonContinuar = view?.findViewById<Button>(R.id.btn_continuar)!!
        mButtonVoltar = view?.findViewById<Button>(R.id.btn_voltar)!!
        mTitleQuestion = view?.findViewById(R.id.title_atendimento)!!
        mTitleContent = view?.findViewById(R.id.title_content)!!

        val intent = activity?.intent
        val mPesquisadorEntity = intent?.getStringExtra("PESQUISADOR")!!
        val mPacienteEntity = intent.getStringExtra("PACIENTE")!!
        mPaciente = Json.decodeFromString<PacienteEntity>(mPacienteEntity)
        // Parsing to dataclass to be used by registerPesquisa()
        mPesquisador = Json.decodeFromString<PesquisadorEntity>(mPesquisadorEntity)
        // When the value of slide changes then set the values
        slider.addOnChangeListener { slider, _, _ ->
            val slideNumberValue: Float = slider.value
            slider_value = when {
                slideNumberValue.toString() == "3.0" -> {
                    "Regular"
                }
                slideNumberValue.toString() == "6" -> {
                    "Bom"
                }
                slideNumberValue.toString() == "9" -> {
                    "Ótimo"
                }
                else -> {
                    "Ruim"
                }
            }
        }

        mButtonContinuar.setOnClickListener {
            val titleQuestion: String = PesquisaActivity.mTitleQuestion.text.toString()
            val titleContent: String = PesquisaActivity.mTitleContent.text.toString()
            mQuestoes +=
                QuestaoEntity(
                    6,
                    titleQuestion,
                    titleContent,
                    slider_value!!
                )

            mPesquisa = PesquisaEntity(
                gson.toJson(mPesquisador),
                gson.toJson(mQuestoes),
                gson.toJson(mPaciente),
                gson.toJson(currentDate)
            )
            // Creating zero questao entity
            PesquisaViewModel().updatePesquisa(
                context = activity?.application!!.applicationContext,
                pesquisa = mPesquisa
            )

            PesquisaViewModel().searchPesquisa(
                context = activity?.application!!.applicationContext,
                pesquisador = mPesquisador
            )!!.observe(activity!!, {
                Toast.makeText(
                    context,
                    "Atualizando a pesquisa ${it.toString()}",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d("DEBUG", "Pesquisa variable value is: ${it.toString()}")
            })

            run {
                Intent(activity, ActivityIniciarPesquisa::class.java).apply {
                    putExtra(ActivityIniciarPesquisa.QUESTOES, gson.toJson(mQuestoes))
                }
            }

            nextFragment = QuestionFragmentFinish()
            fragmentTransaction.hide(lastFragment)
            fragmentTransaction.add(R.id.pesquisa_activity, nextFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        mButtonVoltar.setOnClickListener {
            nextFragment = QuestionFragmentLimpeza()
            fragmentTransaction.hide(lastFragment)
            fragmentTransaction.show(nextFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }
}