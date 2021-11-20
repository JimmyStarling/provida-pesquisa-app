package com.jimmystarling.providapesquisasatisfacao.ui.questions

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.slider.Slider
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.jimmystarling.providapesquisasatisfacao.R
import com.jimmystarling.providapesquisasatisfacao.data.model.PacienteEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.PesquisaEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.PesquisadorEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.QuestaoEntity
import org.json.JSONObject

class QuestionFragment : Fragment() {
    lateinit var slider: Slider
    lateinit var slider_value: String

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
        fun newInstance() = QuestionFragment()
    }

    private lateinit var viewModel: PesquisaViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        slider = view?.findViewById<Slider>(R.id.slider_quality)!!
        mButtonContinuar = view?.findViewById<Button>(R.id.btn_continuar)!!
        mButtonVoltar = view?.findViewById<Button>(R.id.btn_voltar)!!
        mTitleQuestion = view?.findViewById(R.id.title_atendimento)!!
        mTitleContent = view?.findViewById(R.id.title_content)!!

        val intent = activity?.intent
        val nPesquisadorEntity = intent?.getStringExtra("PESQUISADOR")!!
        val mPesquisadorParser: JsonElement = JsonParser.parseString(nPesquisadorEntity)
        // Parsing to dataclass
        mPesquisador = gson.fromJson(mPesquisadorParser, PesquisadorEntity::class.java) as PesquisadorEntity

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
        mPesquisa = PesquisaEntity(mPesquisador.toString(), mQuestao.toString(), mPaciente.toString())
        mQuestao = QuestaoEntity(
            1,
            mTitleQuestion.toString(),
            mTitleContent.toString(),
            slider_value
        )
        mButtonContinuar.setOnClickListener {

            // Creating zero questao entity
            PesquisaViewModel().createPesquisa(
                    context = activity?.application!!.applicationContext,
                    pesquisador = mPesquisador,
                    questoes = listOf<QuestaoEntity>(mQuestao),
                    paciente = mPaciente
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}
