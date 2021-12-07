package com.jimmystarling.providapesquisasatisfacao.ui.questions

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
import com.jimmystarling.providapesquisasatisfacao.ui.questions.PesquisaActivity.Companion.currentDate
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class QuestionFragmentCallcenter: Fragment() {

    lateinit var lastFragment: Fragment
    lateinit var nextFragment: Fragment

    lateinit var slider: Slider
    lateinit var sliderValue: String

    lateinit var mButtonContinuar: Button
    lateinit var mButtonVoltar: Button

    // Entities and variables to modelview
    lateinit var mPesquisa: PesquisaEntity
    lateinit var mPesquisador: PesquisadorEntity
    lateinit var mQuestoes: MutableList<QuestaoEntity>
    lateinit var mPaciente: PacienteEntity

    lateinit var mTitleQuestion: View
    lateinit var mTitleContent: View

    companion object {
        var gson = Gson()
        fun newInstance() = QuestionFragmentCallcenter()
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
            // Create question's PesquisaEntity tp be used by registerPesquisa()
            mQuestoes +=
                    QuestaoEntity(
                        3,
                        mTitleQuestion.toString(),
                        mTitleContent.toString(),
                        sliderValue
                    )
            mPesquisa = PesquisaEntity(
                gson.toJson(mPesquisador),
                gson.toJson(mQuestoes),
                gson.toJson(mPaciente),
                gson.toJson(currentDate)
            )
            // Creating zero questao entity
            PesquisaViewModel().updatePesquisa(
                activity?.application!!.applicationContext,
                mPesquisa
            )

            nextFragment = QuestionFragmentAgilidade()
            lastFragment = this
            fragmentTransaction.hide(lastFragment)
            fragmentTransaction.show(nextFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}