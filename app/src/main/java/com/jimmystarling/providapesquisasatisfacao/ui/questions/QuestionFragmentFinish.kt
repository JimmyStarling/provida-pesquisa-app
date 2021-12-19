package com.jimmystarling.providapesquisasatisfacao.ui.questions

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
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
import androidx.lifecycle.lifecycleScope
import com.jimmystarling.providapesquisasatisfacao.R.*
import com.jimmystarling.providapesquisasatisfacao.data.utils.Constants
import com.jimmystarling.providapesquisasatisfacao.data.utils.fromJson
import com.jimmystarling.providapesquisasatisfacao.data.utils.observeOnce
import com.jimmystarling.providapesquisasatisfacao.ui.ActivityIniciarPesquisa
import com.jimmystarling.providapesquisasatisfacao.ui.ActivityIniciarPesquisa.Companion.activityIniciarPesquisa
import java.util.*


class QuestionFragmentFinish : Fragment() {

    // From activity
    private val appContext = activity?.application!!.applicationContext
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
    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.lifecycleScope?.launchWhenCreated {

            val mResearcherEntity = activity?.intent!!.getStringExtra(Constants.RESEARCHER)!!
            val mPatientEntity = activity?.intent!!.getStringExtra(Constants.CLIENT)!!

            mButtonContinue = view?.findViewById(R.id.btn_continuar_limpeza)!!
            mButtonReturn = view?.findViewById(R.id.btn_voltar_limpeza)!!

            mPatient = Json.decodeFromString(mPatientEntity)
            mResearcher = Json.decodeFromString(mResearcherEntity)
            mQuestions = emptyList<QuestaoEntity>().toMutableList()

            Log.d("DEBUG from QuestionFragment()", "mPatient is $mPatient, mResearcher is $mResearcher, mQuestions is $mQuestions")

            val questionId = 17
            mButtonContinue.setOnClickListener {
                // Local variables to lastSurvey's entity and questions.
                lateinit var lastSurvey: PesquisaEntity
                lateinit var lastSurveyQuestions: MutableList<QuestaoEntity>
                // Passing questoes to mQuestoes
                PesquisaViewModel().searchPesquisa(
                    context = activity?.application!!.applicationContext,
                    pesquisador = mResearcher
                )!!.observe(requireActivity(), { surveys ->
                    lastSurvey = surveys.last()
                    lastSurveyQuestions =
                        Gson().fromJson<MutableList<QuestaoEntity>>(lastSurvey.questoes)
                    lastSurveyQuestions.forEach { question ->
                        mQuestions.add(question)
                    }
                })
                Log.d("DEBUG", "The final question variable is: $mQuestions")
                // Updating pesquisa by pesquisador
                PesquisaViewModel().searchPesquisa(
                    appContext,
                    mResearcher
                )?.observeOnce(requireActivity(), {
                    if(it != null) {
                        finishQuestions(it.last())
                    }
                })
            }// On click continuar listener
            mButtonReturn.setOnClickListener {
                val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
                fragmentManager.popBackStack()
            }
        }
    }
    private fun finishQuestions(lastSurvey: PesquisaEntity) {
        PesquisaViewModel().updatePesquisa(
            context = appContext,
            id = lastSurvey.id,
            questoes = mQuestions
        ).run {
            activityIniciarPesquisa = Intent(activity, ActivityIniciarPesquisa::class.java).apply {
                putExtra(ActivityIniciarPesquisa.QUESTOES, gson.toJson(mQuestions))
            }
            startActivity(activityIniciarPesquisa as Intent)
        }
    }


 /*   override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PesquisaViewModel::class.java)

        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        val mQuestoes: MutableList<QuestaoEntity> = emptyList<QuestaoEntity>().toMutableList()

        mButtonContinuar = view?.findViewById<Button>(R.id.btn_continuar_finish)!!
        mButtonVoltar = view?.findViewById<Button>(R.id.btn_voltar_finish)!!

        val intent = activity?.intent
        val mPesquisadorEntity = intent?.getStringExtra("PESQUISADOR")!!
        val mPacienteEntity = intent.getStringExtra("CLIENT")!!
        mPaciente = Json.decodeFromString<PacienteEntity>(mPacienteEntity)
        // Parsing to dataclass to be used by registerPesquisa()
        mPesquisador = Json.decodeFromString<PesquisadorEntity>(mPesquisadorEntity)

        mButtonContinuar.setOnClickListener {

            mPesquisa = PesquisaEntity(
                gson.toJson(mPesquisador),
                gson.toJson(mQuestoes),
                gson.toJson(mPaciente),
                gson.toJson(getCurrentDate())
            )
            // Creating zero questao entity
            PesquisaViewModel().updatePesquisa(
                context = activity?.application!!.applicationContext,
                id = mPesquisa.id,
                questoes = mQuestoes
            )

            PesquisaViewModel().searchPesquisa(
                context = activity?.application!!.applicationContext,
                pesquisador = mPesquisador
            )!!.observe(requireActivity(), {
                val pesquisasQuantidade = it.size
                PesquisadorRepository.updatePesquisador(requireActivity(), mPesquisador.name, pesquisasQuantidade)
            })

            PesquisadorRepository.searchPesquisador(
                activity?.application!!.applicationContext,
                mPesquisador.name,
                mPesquisador.password
            )!!.observe(requireActivity(), {
                Log.d("DEBUG", "A quantidade de pesquisa final é ${it.pesquisas_quantidade}")
            })

            run {
                activityIniciarPesquisa = Intent(activity, ActivityIniciarPesquisa::class.java).apply {
                    putExtra(ActivityIniciarPesquisa.QUESTOES, gson.toJson(mQuestoes))
                }
                startActivity(activityIniciarPesquisa as Intent)
            }
        }
        mButtonVoltar.setOnClickListener {
            nextFragment = QuestionFragmentGeral()
            fragmentTransaction.hide(lastFragment)
            fragmentTransaction.show(nextFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }*/
}