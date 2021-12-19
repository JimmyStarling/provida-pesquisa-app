package com.jimmystarling.providapesquisasatisfacao.ui.questions

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.slider.Slider
import com.jimmystarling.providapesquisasatisfacao.R
import com.jimmystarling.providapesquisasatisfacao.data.model.PacienteEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.PesquisaEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.PesquisadorEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.QuestaoEntity
import java.text.SimpleDateFormat
import java.util.*

class PesquisaActivity : AppCompatActivity() {

    // From activity
    val appContext = this.application!!.applicationContext
    // Fragments
    lateinit var lastFragment: Fragment
    lateinit var nextFragment: Fragment
    // Entities and variables to modelview
    lateinit var mResearcher: PesquisadorEntity
    lateinit var mPatient: PacienteEntity
    lateinit var mQuestions: MutableList<QuestaoEntity>
    lateinit var mPesquisa: PesquisaEntity
    // Variables
    lateinit var slider: Slider
    lateinit var sliderValue: String
    lateinit var teamTitles: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pesquisa_activity)
        val fragment = QuestionFragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.pesquisa_activity, fragment.newInstance())
                .commitNow()
        }
    }
    companion object{
        var QUESTOES: String? = null
        lateinit var activityPesquisa: Any
        fun getCurrentDate(): String {
            @SuppressLint("SimpleDateFormat")
            val simpleFormat = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = simpleFormat.format(Date())
            return currentDate
        }
    }
}