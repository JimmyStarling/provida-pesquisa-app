package com.jimmystarling.providapesquisasatisfacao.ui.questions

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.slider.Slider
import com.google.gson.Gson
import com.jimmystarling.providapesquisasatisfacao.R
import com.jimmystarling.providapesquisasatisfacao.data.model.PacienteEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.PesquisaEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.PesquisadorEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.QuestaoEntity
import com.jimmystarling.providapesquisasatisfacao.ui.ActivityIniciarPesquisa
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

    // Entities and variables to modelview
    private lateinit var mPesquisa: PesquisaEntity
    private lateinit var mPesquisador: PesquisadorEntity
    private lateinit var mQuestoes: QuestaoEntity
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

        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        slider = view?.findViewById(R.id.slider_quality)!!
        mButtonContinuar = view?.findViewById(R.id.btn_continuar)!!
        mButtonVoltar = view?.findViewById(R.id.btn_voltar)!!
        mTitleQuestion = view?.findViewById(R.id.title_atendimento)!!
        mTitleContent = view?.findViewById(R.id.title_content)!!

        val intent = activity?.intent
        val mPesquisadorEntity = intent?.getStringExtra("PESQUISADOR")!!
        val mPacienteEntity = intent.getStringExtra("PACIENTE")!!
        mPaciente = Json.decodeFromString(mPacienteEntity)
        // Parsing to dataclass to be used by registerPesquisa()
        mPesquisador = Json.decodeFromString(mPesquisadorEntity)
        // When the value of slide changes then set the values
        slider.addOnChangeListener { slider, _, _ ->
            val slideNumberValue: Float = slider.value
            sliderValue = when {
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
            val titleQuestion: String = mTitleQuestion.text.toString()
            val titleContent: String = mTitleContent.text.toString()
            var listQuestoes: Array<String>?
            run {
                Intent(activity, PesquisaActivity::class.java).apply {
                    listQuestoes = getStringArrayExtra(ActivityIniciarPesquisa.QUESTOES)!!
                }
            }
            // Create question's PesquisaEntity tp be used by registerPesquisa()
            mQuestoes = QuestaoEntity(
                        3,
                        titleQuestion,
                        titleContent,
                        sliderValue
                    )
            listQuestoes?.plus(mQuestoes.toString())

            mPesquisa = PesquisaEntity(
                gson.toJson(mPesquisador),
                gson.toJson(listQuestoes),
                gson.toJson(mPaciente),
                gson.toJson(currentDate)
            )
            // Creating zero questao entity
            PesquisaViewModel().updatePesquisa(
                activity?.application!!.applicationContext,
                mPesquisa
            )
            run {
                Intent(activity, ActivityIniciarPesquisa::class.java).apply {
                    putExtra(ActivityIniciarPesquisa.QUESTOES, gson.toJson(listQuestoes))
                }
            }
            nextFragment = QuestionFragmentEstrutura()
            lastFragment = this
            fragmentTransaction.hide(lastFragment)
            fragmentTransaction.show(nextFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        mButtonVoltar.setOnClickListener {
            nextFragment = QuestionFragmentAgilidade()
            fragmentTransaction.hide(lastFragment)
            fragmentTransaction.show(nextFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }

}