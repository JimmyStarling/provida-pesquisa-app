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
import com.jimmystarling.providapesquisasatisfacao.R.*
import com.jimmystarling.providapesquisasatisfacao.ui.FragmentsViewModel
import com.jimmystarling.providapesquisasatisfacao.ui.questions.PesquisaActivity.Companion.currentDate
import com.jimmystarling.providapesquisasatisfacao.ui.questions.PesquisaActivity.Companion.mTitleContent
import com.jimmystarling.providapesquisasatisfacao.ui.questions.PesquisaActivity.Companion.mTitleQuestion

class QuestionFragmentAgilidade : Fragment() {

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
        var lastFragment: Fragment = QuestionFragmentAgilidade()
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

        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager

        var mQuestoes: MutableList<QuestaoEntity> = emptyList<QuestaoEntity>().toMutableList()

        slider = view?.findViewById<Slider>(R.id.slider_quality_agilidade)!!
        mButtonContinuar = view?.findViewById<Button>(R.id.btn_continuar_agilidade)!!
        mButtonVoltar = view?.findViewById<Button>(R.id.btn_voltar_agilidade)!!
        mTitleQuestion = view?.findViewById(R.id.title_atendimento_agilidade)!!
        mTitleContent = view?.findViewById(R.id.title_content_agilidade)!!

        val intent = activity?.intent
        val mPesquisadorEntity = intent?.getStringExtra("PESQUISADOR")!!
        val mPacienteEntity = intent.getStringExtra("PACIENTE")!!
        mPaciente = Json.decodeFromString<PacienteEntity>(mPacienteEntity)
        // Parsing to dataclass to be used by registerPesquisa()
        mPesquisador = Json.decodeFromString<PesquisadorEntity>(mPesquisadorEntity)
        Log.d("DEBUG", "Pesquisador from FragmentAgilidade() is: $mPesquisador")
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

        // Updating pesquisa by pesquisador
        PesquisaViewModel().searchPesquisa(
            activity?.application!!.applicationContext,
            mPesquisador
        )?.observe(activity!!, {
            val lastPesquisa = it.last()
            mPesquisa = lastPesquisa
        })

        var questoesID = 9
        mButtonContinuar.setOnClickListener {

            val titleQuestion: String = mTitleQuestion.text.toString()
            val titleContent: String = mTitleContent.text.toString()
            // Set titles and when is the last title then replace fragment
            phraseData.forEachIndexed { index, phrase ->
                if(titleContent == phraseData[index]){
                    // If is the first question then register else update PesquisaEntity
                    if (phraseData[0] == mTitleContent.text){
                        // Passing questoes to mQuestoes
                        var questoes: MutableList<QuestaoEntity> = emptyList<QuestaoEntity>().toMutableList()
                        PesquisaViewModel().searchPesquisa(
                            context = activity?.application!!.applicationContext,
                            pesquisador = mPesquisador
                        )!!.observe(requireActivity(), {
                            val lastPesquisa = it.last()
                            mPesquisa = lastPesquisa
                            it.forEach { pesquisa ->
                                questoes = Json.decodeFromString<MutableList<QuestaoEntity>>(pesquisa.questoes)
                                    .toMutableList()
                            }
                        })
                        mQuestoes = questoes
                        // Create question's PesquisaEntity to be used by registerPesquisa()
                        mQuestoes.add(
                            QuestaoEntity(
                                index+questoesID,
                                titleQuestion,
                                titleContent,
                                sliderValue
                            )
                        )
                        Log.d("DEBUG", "Question from agilidade added: ${mQuestoes.last()}")
                        // Set title question as the next phrase at phraseData
                        mTitleContent.text = phraseData[index+1]
                    } else if(phraseData[0] != mTitleContent.text && phraseData.getOrNull(index+1) != null){
                        questoesID++
                        // Create question's PesquisaEntity tp be used by registerPesquisa()
                        mQuestoes.add(
                            QuestaoEntity(
                                questoesID,
                                titleQuestion,
                                titleContent,
                                sliderValue
                            )
                        )
                        // Set title question as the next phrase at phraseData
                        mTitleContent.text = phraseData[index+1]
                        Log.d("DEBUG", "Question from agilidade added: ${mQuestoes.last()}")
                    } else {
                        // Create question's PesquisaEntity to be used by registerPesquisa()
                        mQuestoes.add(
                            QuestaoEntity(
                                questoesID++,// get from the index
                                titleQuestion,
                                titleContent,
                                sliderValue
                            )
                        )
                        Log.d("DEBUG", "The pesquisador variable: $mPesquisador")
                        PesquisaViewModel().updatePesquisa(
                            context = activity?.application!!.applicationContext,
                            id = mPesquisa.id,
                            questoes = mQuestoes
                        )

                        Log.d("DEBUG", "The final question variable: $mQuestoes")

                        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
                        nextFragment = QuestionFragmentCallcenter()
                        lastFragment = this
                        fragmentTransaction.replace(R.id.pesquisa_activity, nextFragment, "FRAGMENT_AGILIDADE")
                        fragmentTransaction.addToBackStack(null)
                        fragmentTransaction.commit()
                    }
                }
            }
        }
        mButtonVoltar.setOnClickListener {
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            nextFragment = QuestionFragment()
            fragmentTransaction.hide(lastFragment)
            fragmentTransaction.show(nextFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }
}