package com.jimmystarling.providapesquisasatisfacao.ui.questions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.jimmystarling.providapesquisasatisfacao.R
import com.jimmystarling.providapesquisasatisfacao.data.model.QuestaoEntity
import java.text.SimpleDateFormat
import java.util.*

class PesquisaActivity : AppCompatActivity() {

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
        val simpleFormat = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = simpleFormat.format(Date())

        lateinit var mTitleQuestion : TextView
        lateinit var mTitleContent: TextView

        lateinit var activityPesquisa: Any

    }
}