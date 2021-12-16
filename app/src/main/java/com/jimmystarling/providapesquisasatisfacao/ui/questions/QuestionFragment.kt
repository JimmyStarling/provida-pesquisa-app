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
import com.jimmystarling.providapesquisasatisfacao.ui.FragmentsViewModel
import com.jimmystarling.providapesquisasatisfacao.ui.questions.PesquisaActivity.Companion.activityPesquisa
import com.jimmystarling.providapesquisasatisfacao.ui.questions.PesquisaActivity.Companion.currentDate
import com.jimmystarling.providapesquisasatisfacao.ui.questions.PesquisaActivity.Companion.mTitleContent
import com.jimmystarling.providapesquisasatisfacao.ui.questions.PesquisaActivity.Companion.mTitleQuestion
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class QuestionFragment : Fragment() {

    private lateinit var lastFragment: Fragment
    private lateinit var nextFragment: Fragment

    private lateinit var slider: Slider

    private lateinit var mButtonContinuar: Button
    private lateinit var mButtonVoltar: Button

    // Entities and variables to modelview
    private lateinit var mPesquisa: PesquisaEntity
    private lateinit var mPesquisador: PesquisadorEntity
    private var mQuestoes: MutableList<QuestaoEntity> = emptyList<QuestaoEntity>().toMutableList()
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
        var sliderValue = "1"
        val slideDictionary: Map<Int, String> = mapOf<Int, String>(
            1 to "Ruim",
            4 to "Regular",
            7 to "Bom",
            10 to "Ótimo"
        )
        // When the value of slide changes then set the values
        slider.addOnChangeListener { _, data, _ ->
            val dataNumber: Int = data.toInt()
            sliderValue = slideDictionary[dataNumber].toString()
            Log.d("DEBUG", "The slide value is $sliderValue")
        }
        val phraseData = arrayListOf<String>(
            getString(R.string.title_question_enf),
            getString(R.string.title_question_farm),
            getString(R.string.title_question_marc),
            getString(R.string.title_question_recep),
            getString(R.string.title_question_adm),
            getString(R.string.title_question_vac),
            getString(R.string.title_question_lab),
            getString(R.string.title_question_med)
        )

        mButtonContinuar.setOnClickListener {
            val titleQuestion: String = mTitleQuestion.text.toString()
            val titleContent: String = mTitleContent.text.toString()
            // Set titles and when is the last title then replace fragment
            phraseData.forEachIndexed { index, phrase ->
                Log.d("DEBUG", "Actual titleContent is: $phrase at $index")
                if(titleContent == phraseData[index]){
                    // If is the first question then register else update PesquisaEntity
                    if (phraseData[0] == mTitleContent.text){
                        // Create question's PesquisaEntity tp be used by registerPesquisa()
                        mQuestoes = mutableListOf<QuestaoEntity>(
                            QuestaoEntity(
                                0,
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
                        // Set title question as the next phrase at phraseData
                        mTitleContent.text = phraseData[index+1]
                    } else if(phraseData[0] != mTitleContent.text && phraseData.getOrNull(index+1) != null){
                        // Create question's PesquisaEntity tp be used by registerPesquisa()
                        mQuestoes.add(
                            QuestaoEntity(
                                index,
                                titleQuestion,
                                titleContent,
                                sliderValue
                            )
                        )
                        // Set title question as the next phrase at phraseData
                        mTitleContent.text = phraseData[index+1]
                    } else {
                        Log.d("DEBUG", "The final question variable is: $mQuestoes")
                        // Setting questions to fragments
                        FragmentsViewModel().setQuestions(mQuestoes)
                        FragmentsViewModel().questionsMessage.observe(activity!!) { questions ->
                            Log.d("DEBUG", "The questions from modelview is: $questions")
                        }
                        // Updating pesquisa by pesquisador
                        PesquisaViewModel().searchPesquisa(
                            activity?.application!!.applicationContext,
                            mPesquisador
                        )?.observe(activity!!, {
                            val lastPesquisa = it.last()
                            Log.d("DEBUG", "The id of pesquisa is ${lastPesquisa?.id}")
                            PesquisaViewModel().updatePesquisa(
                                context = activity?.application!!.applicationContext,
                                id = lastPesquisa?.id,
                                questoes = mQuestoes
                            )
                        })

                        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
                        nextFragment = QuestionFragmentAgilidade()
                        lastFragment = this
                        fragmentTransaction.replace(R.id.pesquisa_activity, nextFragment, "FRAGMENT_AGILIDADE")
                        fragmentTransaction.addToBackStack(null)
                        fragmentTransaction.commit()
                    }
                }
            }
        }
    }
}
