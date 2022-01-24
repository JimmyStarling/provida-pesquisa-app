package com.jimmystarling.providapesquisasatisfacao.ui.questions

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.jimmystarling.providapesquisasatisfacao.R
import com.jimmystarling.providapesquisasatisfacao.data.model.PacienteEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.PesquisaEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.PesquisadorEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.QuestaoEntity
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import androidx.fragment.app.FragmentManager
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import com.jimmystarling.providapesquisasatisfacao.R.*
import com.jimmystarling.providapesquisasatisfacao.data.utils.Constants
import com.jimmystarling.providapesquisasatisfacao.data.utils.fromJson
import com.jimmystarling.providapesquisasatisfacao.data.utils.observeOnce
import com.jimmystarling.providapesquisasatisfacao.ui.ActivityIniciarPesquisa
import com.jimmystarling.providapesquisasatisfacao.ui.ActivityIniciarPesquisa.Companion.activityIniciarPesquisa
import java.io.File
import java.util.*


class QuestionFragmentFinish : Fragment() {

    private lateinit var appContext: Context

    // Entities and variables to modelview
    private lateinit var mResearcher: PesquisadorEntity
    private lateinit var mPatient: PacienteEntity
    private lateinit var mQuestions: MutableList<QuestaoEntity>

    // UI components
    private lateinit var mButtonContinue: Button
    private lateinit var mButtonReturn: Button

    /*
    lateinit var nextFragment: Fragment

    lateinit var slider: Slider

    lateinit var mButtonContinuar: Button
    lateinit var mButtonVoltar: Button

    // Entities and variables to modelview
    lateinit var mPesquisa: PesquisaEntity
    lateinit var mPesquisador: PesquisadorEntity
    lateinit var mPaciente: PacienteEntity
*/
    companion object {
        var gson = Gson()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(layout.question_fragment_finish, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // From activity
        appContext = activity?.application!!.applicationContext
        val mResearcherEntity = activity?.intent!!.getStringExtra(Constants.RESEARCHER)!!
        val mPatientEntity = activity?.intent!!.getStringExtra(Constants.CLIENT)!!

        mButtonContinue = requireActivity().findViewById(R.id.btn_continuar_finish)!!
        mButtonReturn = requireActivity().findViewById(R.id.btn_voltar_finish)!!

        mPatient = Json.decodeFromString(mPatientEntity)
        mResearcher = Json.decodeFromString(mResearcherEntity)
        mQuestions = emptyList<QuestaoEntity>().toMutableList()

        Log.d(
            "DEBUG from QuestionFragment()",
            "mPatient is $mPatient, mResearcher is $mResearcher, mQuestions is $mQuestions"
        )

        mButtonContinue.setOnClickListener {
            // Local variables to lastSurvey's entity and questions.
            lateinit var lastSurvey: PesquisaEntity
            lateinit var lastSurveyQuestions: MutableList<QuestaoEntity>
            // Passing questoes to mQuestoes
            PesquisaViewModel().searchPesquisa(
                context = activity?.application!!.applicationContext,
                pesquisador = mResearcher
            )!!.observeOnce(requireActivity(), { surveys ->
                lastSurvey = surveys.last()
                lastSurveyQuestions =
                    Gson().fromJson<MutableList<QuestaoEntity>>(lastSurvey.questoes)
                lastSurveyQuestions.forEach { question ->
                    mQuestions.add(question)
                }
                Log.d("DEBUG", "The final question variable is: $mQuestions")
                finishQuestions(lastSurvey, mQuestions)
            })
        }// On click continuar listener
        mButtonReturn.setOnClickListener {
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            fragmentManager.popBackStack()
        }
    }

    private fun finishQuestions(lastSurvey: PesquisaEntity, questions: MutableList<QuestaoEntity>) {
        PesquisaViewModel().updatePesquisa(
            context = appContext,
            id = lastSurvey.id,
            questoes = questions
        ).run {
            activityIniciarPesquisa = Intent(activity, ActivityIniciarPesquisa::class.java).apply {
                putExtra(ActivityIniciarPesquisa.RESEARCHER, gson.toJson(mResearcher))
                putExtra(ActivityIniciarPesquisa.QUESTOES, gson.toJson(this@QuestionFragmentFinish.mQuestions))
            }
            startActivity(activityIniciarPesquisa as Intent)
        }
    }
}