package com.jimmystarling.providapesquisasatisfacao.ui.questions

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.slider.Slider
import com.google.gson.Gson
import com.jimmystarling.providapesquisasatisfacao.R
import com.jimmystarling.providapesquisasatisfacao.data.model.PacienteEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.PesquisaEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.PesquisadorEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.QuestaoEntity
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class QuestionFragment : Fragment() {

    private lateinit var lastFragment: Fragment
    private lateinit var nextFragment: Fragment

    private lateinit var slider: Slider
    lateinit var sliderValue: String

    private lateinit var mButtonContinuar: Button
    private lateinit var mButtonVoltar: Button

    // Entities and variables to modelview
    private lateinit var mPesquisa: PesquisaEntity
    private lateinit var mPesquisador: PesquisadorEntity
    private lateinit var mQuestao: QuestaoEntity
    private lateinit var mPaciente: PacienteEntity
    
    private lateinit var mTitleQuestion: View
    private lateinit var mTitleContent: View

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
        viewModel = ViewModelProvider(this).get(PesquisaViewModel::class.java)

        val fragmentManager: FragmentManager = activity!!.supportFragmentManager
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
        // TODO: Update pesquisador with pesquisas and add a count
        Log.d("DEBUG", "mPaciente:${mPaciente}, mPesquisador:${mPesquisador}")
        // Slider listener to registerPesquisa
        slider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
                val value: Float = slider.value
                sliderValue = when {
                    value.toString() == "4" -> {
                        "Regular"
                    }
                    value.toString() == "7" -> {
                        "Bom"
                    }
                    value.toString() == "10" -> {
                        "??timo"
                    }
                    else -> {
                        "Ruim"
                    }
                }
            }
            override fun onStopTrackingTouch(slider: Slider) {
                val value: Float = slider.value
                sliderValue = when {
                    value.toString() == "4" -> {
                        "Regular"
                    }
                    value.toString() == "7" -> {
                        "Bom"
                    }
                    value.toString() == "10" -> {
                        "??timo"
                    }
                    else -> {
                        "Ruim"
                    }
                }
            }
        })
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
                    "??timo"
                }
                else -> {
                    "Ruim"
                }
            }
        }
        mButtonContinuar.setOnClickListener {
            val titleQuestion: String = getString(R.string.title_question_agilidade)
            val titleContent: String = getString(R.string.title_question_enf)

            // Create question's PesquisaEntity tp be used by registerPesquisa()
            mQuestao = QuestaoEntity(
                1,
                titleQuestion,
                titleContent,
                sliderValue
            )
            mPesquisa = PesquisaEntity(gson.toJson(mPesquisador), gson.toJson(mQuestao), gson.toJson(mPaciente))
            // Creating zero questao entity
            PesquisaViewModel().registerPesquisa(
                context = activity?.application!!.applicationContext,
                pesquisador = mPesquisador,
                questoes = listOf(mQuestao),
                paciente = mPaciente
            )

            nextFragment = QuestionFragmentAgilidade()
            lastFragment = this

            fragmentTransaction.replace(R.id.pesquisa_activity, nextFragment, "FRAGMENT_AGILIDADE")
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }

}
