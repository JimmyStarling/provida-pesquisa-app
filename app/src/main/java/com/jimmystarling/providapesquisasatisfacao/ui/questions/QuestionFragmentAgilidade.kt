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
import com.jimmystarling.providapesquisasatisfacao.ui.ActivityIniciarPesquisa
import com.jimmystarling.providapesquisasatisfacao.ui.questions.PesquisaActivity.Companion.currentDate
import com.jimmystarling.providapesquisasatisfacao.ui.questions.PesquisaActivity.Companion.mTitleContent
import com.jimmystarling.providapesquisasatisfacao.ui.questions.PesquisaActivity.Companion.mTitleQuestion
import java.text.SimpleDateFormat
import java.util.*


class QuestionFragmentAgilidade : Fragment() {

    lateinit var nextFragment: Fragment

    lateinit var slider: Slider

    lateinit var mButtonContinuar: Button
    lateinit var mButtonVoltar: Button

    // Entities and variables to modelview
    lateinit var mPesquisa: PesquisaEntity
    lateinit var mPesquisador: PesquisadorEntity
    lateinit var mQuestoes: MutableList<QuestaoEntity>
    lateinit var mPaciente: PacienteEntity

    companion object {
        var gson = Gson()
        lateinit var lastFragment: Fragment
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
        lateinit var sliderValue: String
        // When the value of slide changes then set the values
        slider.addOnChangeListener { _, data, _ ->
            val sliderValueNumber: Float = data
            if (sliderValueNumber.toString() == "4") {
                sliderValue = "Regular"
            } else if (sliderValueNumber.toString() == "7") {
                sliderValue = "Bom"
            }
            else if (sliderValueNumber.toString() == "10") {
                sliderValue = "Ótimo"
            } else {
                sliderValue = "Ruim"
            }
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
            var questoesList: Array<String>?
            run {
                Intent(activity, PesquisaActivity::class.java).apply {
                    questoesList = getStringArrayExtra(PesquisaActivity.QUESTOES)!!
                }
            }
            /*mQuestoes = QuestaoEntity(
                2,
                titleQuestion,
                titleContent,
                sliderValue!!
            )
            questoesList?.plus(mQuestoes.toString())
                */
            /*mPesquisa = PesquisaEntity(
                gson.toJson(mPesquisador),
                gson.toJson(questoesList),
                gson.toJson(mPaciente),
                gson.toJson(currentDate)
            )
            // Creating zero questao entity
            PesquisaViewModel().updatePesquisa(
                context = activity?.application!!.applicationContext,
                pesquisa = mPesquisa
            )*/

            PesquisaViewModel().searchPesquisa(
                context = activity?.application!!.applicationContext,
                pesquisador = mPesquisador
            )!!.observe(requireActivity(), {
                Toast.makeText(
                    context,
                    "Atualizando a pesquisa ${it.toString()}",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d("DEBUG", "Pesquisa variable value is: ${it.toString()}")
            })
            // Set titles and when is the last title then replace fragment
            val lastPhrase: String = phraseData[phraseData.size - 1]
            phraseData.forEachIndexed { index, phrase ->
                Log.d("DEBUG", "Actual titleContent is: $phrase at $index")
                if (phraseData.getOrNull(index + 1) != null) {
                    // Create question's PesquisaEntity tp be used by registerPesquisa()
                    mQuestoes +=
                        QuestaoEntity(
                            index,// get from the index
                            titleQuestion,
                            titleContent,
                            sliderValue
                        )
                    mPesquisa = PesquisaEntity(
                        QuestionFragment.gson.toJson(mPesquisador),
                        QuestionFragment.gson.toJson(mQuestoes),
                        QuestionFragment.gson.toJson(mPaciente),
                        QuestionFragment.gson.toJson(currentDate)
                    )
                    PesquisaViewModel().updatePesquisa(
                        context = activity?.application!!.applicationContext,
                        pesquisa = mPesquisa
                    )
                    // Set title question as the next phrase at phraseData
                    mTitleContent.text = phraseData[index + 1]
                } else {
                    // Converting the mutable list to array list
                    questoesList = mQuestoes.map { it.toString() }.toTypedArray()
                    run {
                        Intent(activity, PesquisaActivity::class.java).apply {
                            putExtra(PesquisaActivity.QUESTOES, questoesList)
                        }
                    }
                    val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
                    nextFragment = QuestionFragmentCallcenter()
                    fragmentTransaction.hide(lastFragment)
                    fragmentTransaction.add(R.id.pesquisa_activity, nextFragment)
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
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