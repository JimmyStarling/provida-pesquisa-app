package com.jimmystarling.providapesquisasatisfacao.ui.questions

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil.setContentView
import com.google.android.material.slider.Slider
import com.jimmystarling.providapesquisasatisfacao.R
import com.jimmystarling.providapesquisasatisfacao.data.model.PacienteEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.PesquisaEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.PesquisadorEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.QuestaoEntity

class QuestionFragment : Fragment() {
    lateinit var slider: Slider
    lateinit var slider_value: String

    lateinit var btn_continuar: Button
    lateinit var btn_volter: Button

    lateinit var pesquisa: PesquisaEntity
    lateinit var pesquisador: PesquisadorEntity
    lateinit var questoes: QuestaoEntity
    lateinit var paciente: PacienteEntity
    lateinit var matricula: String

    companion object {
        fun newInstance() = QuestionFragment()
    }

    private lateinit var viewModel: PesquisaViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        slider = view?.findViewById<Slider>(R.id.slider_quality)!!
        btn_continuar = view?.findViewById<Button>(R.id.btn_continuar)!!
        btn_volter = view?.findViewById<Button>(R.id.btn_voltar)!!
        return inflater.inflate(R.layout.question_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PesquisaViewModel::class.java)
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

        slider.addOnChangeListener { slider, value, fromUser ->
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
        // Passing to pesquisa
        pesquisa = PesquisaEntity(pesquisador, questoes, paciente)
        pesquisador = PesquisadorEntity()
        btn_continuar.setOnClickListener {
            PesquisaViewModel().createPesquisa(pesquisa, pesquisador, paciente)
        }
//        Toast.makeText(context, "Atenção, você precisa marcar a caixa não responder para continuar!", Toast.LENGTH_LONG)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}
