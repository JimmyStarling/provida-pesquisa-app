package com.jimmystarling.providapesquisasatisfacao.ui.questions

import android.content.Context
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
import com.jimmystarling.providapesquisasatisfacao.data.repository.PesquisadorRepository
import com.jimmystarling.providapesquisasatisfacao.ui.ActivityIniciarPesquisa
import com.jimmystarling.providapesquisasatisfacao.ui.ActivityIniciarPesquisa.Companion.activityIniciarPesquisa
import com.jimmystarling.providapesquisasatisfacao.ui.FragmentsViewModel
import com.jimmystarling.providapesquisasatisfacao.ui.login.viewmodel.LoginViewModel
import com.jimmystarling.providapesquisasatisfacao.ui.questions.PesquisaActivity.Companion.activityPesquisa
import com.jimmystarling.providapesquisasatisfacao.ui.questions.PesquisaActivity.Companion.currentDate
import com.jimmystarling.providapesquisasatisfacao.ui.questions.PesquisaActivity.Companion.mTitleContent
import com.jimmystarling.providapesquisasatisfacao.ui.questions.PesquisaActivity.Companion.mTitleQuestion
import java.text.SimpleDateFormat
import java.util.*


class QuestionFragmentFinish : Fragment() {
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
        var lastFragment: Fragment = QuestionFragmentFinish()
    }
    private lateinit var viewModel: PesquisaViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(layout.question_fragment_finish, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PesquisaViewModel::class.java)

        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        var mQuestoes: MutableList<QuestaoEntity> = emptyList<QuestaoEntity>().toMutableList()

        mButtonContinuar = view?.findViewById<Button>(R.id.btn_continuar_finish)!!
        mButtonVoltar = view?.findViewById<Button>(R.id.btn_voltar_finish)!!
        mTitleQuestion = view?.findViewById(R.id.title_finish)!!

        FragmentsViewModel().questionsMessage.observe(requireActivity()) { questions ->
            mQuestoes = questions as MutableList<QuestaoEntity>
        }

        val intent = activity?.intent
        val mPesquisadorEntity = intent?.getStringExtra("PESQUISADOR")!!
        val mPacienteEntity = intent.getStringExtra("PACIENTE")!!
        mPaciente = Json.decodeFromString<PacienteEntity>(mPacienteEntity)
        // Parsing to dataclass to be used by registerPesquisa()
        mPesquisador = Json.decodeFromString<PesquisadorEntity>(mPesquisadorEntity)

        mButtonContinuar.setOnClickListener {

            mPesquisa = PesquisaEntity(
                gson.toJson(mPesquisador),
                gson.toJson(mQuestoes),
                gson.toJson(mPaciente),
                gson.toJson(currentDate)
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
    }
}