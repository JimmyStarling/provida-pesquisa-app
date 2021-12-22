package com.jimmystarling.providapesquisasatisfacao.ui.questions

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.slider.Slider
import com.google.gson.Gson
import com.jimmystarling.providapesquisasatisfacao.R
import com.jimmystarling.providapesquisasatisfacao.data.utils.observeOnce
import com.jimmystarling.providapesquisasatisfacao.data.model.PacienteEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.PesquisaEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.PesquisadorEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.QuestaoEntity
import com.jimmystarling.providapesquisasatisfacao.data.utils.Constants.Companion.CLIENT
import com.jimmystarling.providapesquisasatisfacao.data.utils.Constants.Companion.RESEARCHER
import com.jimmystarling.providapesquisasatisfacao.data.utils.Constants.Companion.SECOND_FRAGMENT
import com.jimmystarling.providapesquisasatisfacao.data.utils.Constants.Companion.SLIDE_DICTIONARY
import com.jimmystarling.providapesquisasatisfacao.ui.questions.PesquisaActivity.Companion.getCurrentDate
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class QuestionFragment : Fragment() {
    private lateinit var appContext: Context
    private lateinit var nextFragment: Fragment
    // Entities and variables to modelview
    private lateinit var mResearcher: PesquisadorEntity
    private lateinit var mPatient: PacienteEntity
    private lateinit var mQuestions: MutableList<QuestaoEntity>
    private lateinit var mPesquisa: PesquisaEntity
    // Variables
    private lateinit var slider: Slider
    private var sliderValue: String = "0"
    private lateinit var teamTitles: ArrayList<String>
    // UI components
    private lateinit var mButtonContinue: Button
    private lateinit var mButtonReturn: Button
    private lateinit var mTitleQuestion: TextView
    private lateinit var mTitleContent: TextView
    private lateinit var mCheckbox: CheckBox

    companion object {
        private var gson = Gson()
        fun newInstance() = QuestionFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(R.layout.question_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // From activity
        appContext = activity?.application!!.applicationContext

        val mResearcherEntity = activity?.intent!!.getStringExtra(RESEARCHER)!!
        val mPatientEntity = activity?.intent!!.getStringExtra(CLIENT)!!

        slider = requireView().findViewById(R.id.slider_quality)!!
        mButtonContinue = requireView().findViewById(R.id.btn_continuar)!!
        mButtonReturn = requireView().findViewById(R.id.btn_voltar)!!
        mTitleQuestion =  requireView().findViewById(R.id.title_atendimento)!!
        mTitleContent =  requireView().findViewById(R.id.title_content)!!
        mCheckbox = requireView().findViewById(R.id.checkbox_atendimento)!!

        mPatient = Json.decodeFromString(mPatientEntity)
        mResearcher = Json.decodeFromString(mResearcherEntity)
        mQuestions = emptyList<QuestaoEntity>().toMutableList()

        Log.d("DEBUG from QuestionFragment()", "mPatient is $mPatient, mResearcher is $mResearcher, mQuestions is $mQuestions")

        sliderValue = setupSlider()
        mButtonContinue.setOnClickListener {
            val currentQuestion: String = mTitleQuestion.text.toString()
            val currentTeamName: String = mTitleContent.text.toString()
            // Checkbox
            val checkbox = mCheckbox.isActivated
            getTeamsQuestion().forEachIndexed { index, _ ->
                if(currentTeamName == teamTitles[index]){
                    // If is the first question then register else update PesquisaEntity
                    if (teamTitles[0] == mTitleContent.text){
                        when {
                            checkbox -> {
                                // Create question's PesquisaEntity tp be used by registerPesquisa()
                                mQuestions = mutableListOf(
                                    QuestaoEntity(
                                        0,
                                        currentQuestion,
                                        currentTeamName,
                                        sliderValue
                                    )
                                )
                            }
                            else -> {
                                // Create question's PesquisaEntity tp be used by registerPesquisa()
                                mQuestions = mutableListOf(
                                    QuestaoEntity(
                                        0,
                                        currentQuestion,
                                        currentTeamName,
                                        getString(R.string.answer_nulo)
                                    )
                                )
                            }
                        }
                        mPesquisa = PesquisaEntity(
                            gson.toJson(mResearcher),
                            gson.toJson(mQuestions),
                            gson.toJson(mPatient),
                            gson.toJson(getCurrentDate())
                        )
                        Log.d("DEBUG from QuestionFragment()", "mPesquisa were registered: ${gson.toJson(mPesquisa)}")
                        PesquisaViewModel().registerPesquisa(
                            context = appContext,
                            pesquisa = mPesquisa
                        )
                        // Set title question as the next phrase at teamTitles
                        mTitleContent.text = teamTitles[index+1]
                    } else if(teamTitles[0] != mTitleContent.text && teamTitles.getOrNull(index+1) != null){
                        // Create question's PesquisaEntity tp be used by registerPesquisa()
                        mQuestions.add(
                            QuestaoEntity(
                                index,
                                currentQuestion,
                                currentTeamName,
                                sliderValue
                            )
                        )
                        // Set title question as the next phrase at teamTitles
                        mTitleContent.text = teamTitles[index+1]
                    } else {
                        // Create question's PesquisaEntity to be used by registerPesquisa()
                        mQuestions.add(
                            QuestaoEntity(
                                index,// get from the index
                                currentQuestion,
                                currentTeamName,
                                sliderValue
                            )
                        )
                        Log.d("DEBUG", "The final question variable is: $mQuestions")
                        // Updating pesquisa by pesquisador
                        PesquisaViewModel().searchPesquisa(
                            appContext,
                            mResearcher
                        )?.observeOnce(requireActivity(), {
                            if(it != null) {
                                val lastSurvey = it.last()
                                finishQuestions(lastSurvey)
                            }
                        })
                    }// Else
                }// Current has team titles
            }// Foreach teams
        }// On click continuar listener
        mButtonReturn.setOnClickListener {
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            fragmentManager.popBackStack()
        }
    }
    private fun setupSlider(): String{
        // When the value of slide changes then set the values
        slider.addOnChangeListener { _, data, _ ->
            sliderValue = SLIDE_DICTIONARY[data.toInt()].toString()
            Log.d("DEBUG from QuestionFragment()", "The slider value is $sliderValue")
        }.run {
            return@setupSlider sliderValue
        }
    }
    private fun getTeamsQuestion(): ArrayList<String>{
        teamTitles = arrayListOf(
            getString(R.string.title_question_enf),
            getString(R.string.title_question_farm),
            getString(R.string.title_question_marc),
            getString(R.string.title_question_recep),
            getString(R.string.title_question_adm),
            getString(R.string.title_question_vac),
            getString(R.string.title_question_lab),
            getString(R.string.title_question_med)
        )
        return teamTitles
    }
    private fun finishQuestions(lastSurvey: PesquisaEntity) {
        PesquisaViewModel().updatePesquisa(
            context = appContext,
            id = lastSurvey.id,
            questoes = mQuestions
        ).run {
            startNewFragment(QuestionFragmentAgilidade())
        }
    }
    private fun startNewFragment(newFragment: Fragment){
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction =
            fragmentManager.beginTransaction()
        nextFragment = QuestionFragmentAgilidade()
        fragmentTransaction.replace(
            R.id.pesquisa_activity,
            newFragment,
            SECOND_FRAGMENT
        )
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}
