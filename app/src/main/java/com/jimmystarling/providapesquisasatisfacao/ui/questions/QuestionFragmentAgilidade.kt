package com.jimmystarling.providapesquisasatisfacao.ui.questions

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


class QuestionFragmentAgilidade : Fragment() {

    lateinit var lastFragment: Fragment
    lateinit var nextFragment: Fragment

    lateinit var slider: Slider
    var slider_value: String? = null

    lateinit var mButtonContinuar: Button
    lateinit var mButtonVoltar: Button

    // Entities and variables to modelview
    lateinit var mPesquisa: PesquisaEntity
    lateinit var mPesquisador: PesquisadorEntity
    lateinit var mQuestao: QuestaoEntity
    lateinit var mPaciente: PacienteEntity

    lateinit var mTitleQuestion: View
    lateinit var mTitleContent: View

    companion object {
        var gson = Gson()
        fun newInstance() = QuestionFragmentAgilidade()
    }
    private lateinit var viewModel: PesquisaViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(layout.question_fragment_agilidade, container, false)
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
        // Slider listener to registerPesquisa
        slider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
                val value: Float = slider.value
                if(value.toString() == "3.0"){
                    slider_value = "Regular"
                } else if (value.toString() == "6"){
                    slider_value = "Bom"
                } else if (value.toString() == "9"){
                    slider_value = "Ótimo"
                } else {
                    slider_value = "Ruim"
                }
            }
            override fun onStopTrackingTouch(slider: Slider) {
                val value: Float = slider.value
                if(value.toString() == "3.0"){
                    slider_value = "Regular"
                } else if (value.toString() == "6"){
                    slider_value = "Bom"
                } else if (value.toString() == "9"){
                    slider_value = "Ótimo"
                } else {
                    slider_value = "Ruim"
                }
            }
        })
        // When the value of slide changes then set the values
        slider.addOnChangeListener { slider, value, fromUser ->
            val number_slider_value: Float = slider.value
            slider_value = when {
                number_slider_value.toString() == "3.0" -> {
                    "Regular"
                }
                number_slider_value.toString() == "6" -> {
                    "Bom"
                }
                number_slider_value.toString() == "9" -> {
                    "Ótimo"
                }
                else -> {
                    "Ruim"
                }
            }
        }

        mButtonContinuar.setOnClickListener {
            mQuestao = QuestaoEntity(
                2,
                mTitleQuestion.toString(),
                mTitleContent.toString(),
                slider_value!!
            )
            mPesquisa = PesquisaEntity(gson.toJson(mPesquisador), gson.toJson(mQuestao.toString()), gson.toJson(mPaciente.toString()))
            // Creating zero questao entity
            PesquisaViewModel().updatePesquisa(
                context = activity?.application!!.applicationContext,
                pesquisador = mPesquisador,
                pesquisa = mPesquisa,
                questoes = listOf<QuestaoEntity>(mQuestao),
                paciente = mPaciente
            )

            PesquisaViewModel().searchPesquisa(
                activity?.application!!.applicationContext,
                pesquisador = mPesquisador
            )!!.observe(activity!!, {
                Toast.makeText(
                    context,
                    "Atualizando a pesquisa ${it.toString()}",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d("DEBUG", "Pesquisa variable value is: ${it.toString()}")
            })

            nextFragment = QuestionFragmentCallcenter()
            lastFragment = this
            fragmentTransaction.hide(lastFragment)
            fragmentTransaction.add(R.id.pesquisa_activity, nextFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        mButtonVoltar.setOnClickListener {
            nextFragment = QuestionFragment()
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