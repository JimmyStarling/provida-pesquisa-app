package com.jimmystarling.providapesquisasatisfacao.ui.questions

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.slider.Slider
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.jimmystarling.providapesquisasatisfacao.R
import com.jimmystarling.providapesquisasatisfacao.data.model.PacienteEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.PesquisaEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.PesquisadorEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.QuestaoEntity
import com.jimmystarling.providapesquisasatisfacao.ui.ActivityIniciarPesquisa
import com.jimmystarling.providapesquisasatisfacao.ui.FragmentsViewModel
import com.jimmystarling.providapesquisasatisfacao.ui.questions.PesquisaActivity.Companion.currentDate
import com.jimmystarling.providapesquisasatisfacao.ui.questions.PesquisaActivity.Companion.mTitleContent
import com.jimmystarling.providapesquisasatisfacao.ui.questions.PesquisaActivity.Companion.mTitleQuestion
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class QuestionFragmentCallcenter: Fragment() {

    private lateinit var lastFragment: Fragment
    private lateinit var nextFragment: Fragment

    lateinit var slider: Slider
    private lateinit var sliderValue: String

    private lateinit var mButtonContinuar: Button
    private lateinit var mButtonVoltar: Button
    private lateinit var mCheckbox: CheckBox

    // Entities and variables to modelview
    private lateinit var mPesquisa: PesquisaEntity
    private lateinit var mPesquisador: PesquisadorEntity
    private lateinit var mPaciente: PacienteEntity

    companion object {
        var gson = Gson()
    }

    private lateinit var viewModel: PesquisaViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.question_fragment_callcenter, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[PesquisaViewModel::class.java]

        var mQuestoes: MutableList<QuestaoEntity> = emptyList<QuestaoEntity>().toMutableList()

        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager

        slider = view?.findViewById(R.id.slider_quality_callcenter)!!
        mButtonContinuar = view?.findViewById(R.id.btn_continuar_callcenter)!!
        mButtonVoltar = view?.findViewById(R.id.btn_voltar_callcenter)!!
        mTitleQuestion = view?.findViewById(R.id.title_callcenter)!!
        mTitleContent = view?.findViewById(R.id.title_content_callcenter)!!
        mCheckbox = view?.findViewById(R.id.callcenter_uso)!!

        val intent = activity?.intent
        val mPesquisadorEntity = intent?.getStringExtra("PESQUISADOR")!!
        val mPacienteEntity = intent.getStringExtra("PACIENTE")!!
        mPaciente = Json.decodeFromString(mPacienteEntity)
        // Parsing to dataclass to be used by registerPesquisa()
        mPesquisador = Json.decodeFromString(mPesquisadorEntity)
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
                        mCheckbox.isActivated = true
                    }
                }
            }
        }
        val questoesID = 17
        mButtonContinuar.setOnClickListener {
            val titleQuestion: String = mTitleQuestion.text.toString()
            val titleContent: String = mTitleContent.text.toString()
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
            if (!mCheckbox.isActivated) {
                // Create question's PesquisaEntity tp be used by registerPesquisa()
                mQuestoes.add(
                    QuestaoEntity(
                        questoesID,
                        titleQuestion,
                        titleContent,
                        "0"
                    )
                )
            } else {
                mQuestoes.add(
                    QuestaoEntity(
                        questoesID,
                        titleQuestion,
                        getString(R.string.estrutura_title),
                        sliderValue
                    )
                )
                PesquisaViewModel().updatePesquisa(
                    context = activity?.application!!.applicationContext,
                    id = mPesquisa.id,
                    questoes = mQuestoes
                )
                Log.d("DEBUG", "The mPesquisa were updated: $mPesquisa")
                val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
                nextFragment = QuestionFragmentEstrutura()
                lastFragment = this
                fragmentTransaction.replace(R.id.pesquisa_activity, nextFragment, "FRAGMENT_AGILIDADE")
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        }

        mButtonVoltar.setOnClickListener {
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            nextFragment = QuestionFragmentAgilidade()
            fragmentTransaction.hide(lastFragment)
            fragmentTransaction.show(nextFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }
    inline fun <reified T> Gson.fromJson(json: String) = fromJson<T>(json, object: TypeToken<T>() {}.type)
}