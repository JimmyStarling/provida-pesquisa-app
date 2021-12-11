package com.jimmystarling.providapesquisasatisfacao.ui.questions

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

class QuestionFragment : Fragment() {

    private lateinit var lastFragment: Fragment
    private lateinit var nextFragment: Fragment

    private lateinit var slider: Slider
    private lateinit var sliderValue: String

    private lateinit var mButtonContinuar: Button
    private lateinit var mButtonVoltar: Button

    // Entities and variables to modelview
    private lateinit var mPesquisa: PesquisaEntity
    private lateinit var mPesquisador: PesquisadorEntity
    private lateinit var mQuestoes: MutableList<QuestaoEntity>
    private lateinit var mPaciente: PacienteEntity

    companion object {
        var gson = Gson()
        fun newInstance() = QuestionFragment()
    }

    private lateinit var viewModel: PesquisaViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.question_fragment, container, false)
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
        slider.addOnChangeListener { _, data, _ ->
            val sliderValueNumber: Float = data
            sliderValue = when {
                sliderValueNumber.toString() == "4" -> {
                    "Regular"
                }
                sliderValueNumber.toString() == "7" -> {
                    "Bom"
                }
                sliderValueNumber.toString() == "10" -> {
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
            val questoesList: Array<String>?
            // Create question's PesquisaEntity tp be used by registerPesquisa()
            mQuestoes = mutableListOf<QuestaoEntity>(
                QuestaoEntity(
                    1,
                    titleQuestion,
                    titleContent,
                    sliderValue
                )
            )
            mPesquisa = PesquisaEntity(
                gson.toJson(mPesquisador),
                gson.toJson(mQuestoes),
                gson.toJson(mPaciente),
                gson.toJson(currentDate)
            )
            Log.d("DEBUG", "Pesquisa registrada: ${gson.toJson(mPesquisa)}")
            PesquisaViewModel().registerPesquisa(
                context = activity?.application!!.applicationContext,
                pesquisa = mPesquisa
            )
            // Converting the mutable list to array list
            questoesList = mQuestoes.map { it.toString() }.toTypedArray()
            run {
                Intent(activity, PesquisaActivity::class.java).apply {
                    putExtra(ActivityIniciarPesquisa.QUESTOES, questoesList)
                }
            }
            nextFragment = QuestionFragmentAgilidade()
            lastFragment = this
            fragmentTransaction.replace(R.id.pesquisa_activity, nextFragment, "FRAGMENT_AGILIDADE")
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }
}
