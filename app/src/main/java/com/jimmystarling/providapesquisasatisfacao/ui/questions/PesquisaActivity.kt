package com.jimmystarling.providapesquisasatisfacao.ui.questions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jimmystarling.providapesquisasatisfacao.R

class PesquisaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pesquisa_activity)
        val fragment = QuestionFragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment.newInstance())
                .commitNow()
        }
    }
}