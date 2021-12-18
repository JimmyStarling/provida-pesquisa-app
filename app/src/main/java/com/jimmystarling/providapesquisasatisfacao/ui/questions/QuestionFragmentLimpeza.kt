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
import com.google.common.reflect.TypeToken
import com.jimmystarling.providapesquisasatisfacao.R.*
import com.jimmystarling.providapesquisasatisfacao.ui.ActivityIniciarPesquisa
import com.jimmystarling.providapesquisasatisfacao.ui.FragmentsViewModel
import com.jimmystarling.providapesquisasatisfacao.ui.questions.PesquisaActivity.Companion.currentDate
import com.jimmystarling.providapesquisasatisfacao.ui.questions.PesquisaActivity.Companion.mTitleContent
import com.jimmystarling.providapesquisasatisfacao.ui.questions.PesquisaActivity.Companion.mTitleQuestion
import java.text.SimpleDateFormat
import java.util.*


class QuestionFragmentLimpeza : Fragment() {

    lateinit var nextFragment: Fragment

    lateinit var slider: Slider

    lateinit var mButtonContinuar: Button
    lateinit var mButtonVoltar: Button

    // Entities and variables to modelview
    lateinit var mPesquisa: PesquisaEntity
    lateinit var mPesquisador: PesquisadorEntity
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
        return inflater.inflate(layout.question_fragment_limpeza, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[PesquisaViewModel::class.java]

        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager

        var mQuestoes: MutableList<QuestaoEntity> = emptyList<QuestaoEntity>().toMutableList()

        slider = view?.findViewById<Slider>(R.id.slider_quality)!!
        mButtonContinuar = view?.findViewById<Button>(R.id.btn_continuar_limpeza)!!
        mButtonVoltar = view?.findViewById<Button>(R.id.btn_voltar_limpeza)!!
        mTitleQuestion = view?.findViewById(R.id.title_limpeza)!!

        FragmentsViewModel().questionsMessage.observe(activity!!) { questions ->
            mQuestoes = questions as MutableList<QuestaoEntity>
        }

        val intent = activity?.intent
        val mPesquisadorEntity = intent?.getStringExtra("PESQUISADOR")!!
        val mPacienteEntity = intent.getStringExtra("PACIENTE")!!
        mPaciente = Json.decodeFromString<PacienteEntity>(mPacienteEntity)
        // Parsing to dataclass to be used by registerPesquisa()
        mPesquisador = Json.decodeFromString<PesquisadorEntity>(mPesquisadorEntity)
        lateinit var sliderValue: String
        val slideDictionary: Map<Int, String> = mapOf<Int, String>(
            1 to "Ruim",
            4 to "Regular",
            7 to "Bom",
            10 to "Ótimo"
        )
        // When the value of slide changes then set the values
        slider.addOnChangeListener { _, data, _ ->
            val sliderValueNumber: Float = data
            slideDictionary.forEach {
                when {
                    sliderValueNumber.toString() == it.key.toString() -> {
                        sliderValue = it.value
                    }
                }
            }
        }
        mButtonContinuar.setOnClickListener {
            val titleQuestion: String = PesquisaActivity.mTitleQuestion.text.toString()
            // Local variables to lastPesquisa's entity and questions.
            lateinit var lastPesquisa: PesquisaEntity
            lateinit var lastPesquisaQuestions: MutableList<QuestaoEntity>
            // Passing questoes to mQuestoes
            PesquisaViewModel().searchPesquisa(
                context = activity?.application!!.applicationContext,
                pesquisador = mPesquisador
            )!!.observe(requireActivity(), { pesquisas ->
                lastPesquisa = pesquisas.last()
                lastPesquisaQuestions =
                    Gson().fromJson<MutableList<QuestaoEntity>>(lastPesquisa.questoes)
                lastPesquisaQuestions.forEach { question ->
                    mQuestoes.add(question)
                }
            })
            mQuestoes.add(
                QuestaoEntity(
                    19,
                    titleQuestion,
                    getString(string.title_content_limpeza),
                    sliderValue
                )
            )
            mPesquisa = PesquisaEntity(
                QuestionFragmentEstrutura.gson.toJson(mPesquisador),
                QuestionFragmentEstrutura.gson.toJson(mQuestoes),
                QuestionFragmentEstrutura.gson.toJson(mPaciente),
                QuestionFragmentEstrutura.gson.toJson(currentDate)
            )
            PesquisaViewModel().updatePesquisa(
                context = activity?.application!!.applicationContext,
                id = mPesquisa.id,
                questoes = mQuestoes
            )
            Log.d("DEBUG", "The mPesquisa were updated: $mPesquisa")
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            nextFragment = QuestionFragmentGeral()
            lastFragment = this
            fragmentTransaction.replace(R.id.pesquisa_activity, nextFragment, "FRAGMENT_AGILIDADE")
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        mButtonVoltar.setOnClickListener {
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            nextFragment = QuestionFragmentEstrutura()
            fragmentTransaction.hide(lastFragment)
            fragmentTransaction.replace(R.id.pesquisa_activity, nextFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }
    inline fun <reified T> Gson.fromJson(json: String) = fromJson<T>(json, object: TypeToken<T>() {}.type)
}