package com.jimmystarling.providapesquisasatisfacao.ui.questions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jimmystarling.providapesquisasatisfacao.R

class PesquisaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pesquisa_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, QuestionFragment.newInstance())
                .commitNow()
        }
    }
}