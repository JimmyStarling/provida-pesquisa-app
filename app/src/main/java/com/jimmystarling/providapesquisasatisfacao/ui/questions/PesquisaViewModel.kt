package com.jimmystarling.providapesquisasatisfacao.ui.questions

import android.content.Context
import androidx.lifecycle.ViewModel
import com.jimmystarling.providapesquisasatisfacao.data.model.PacienteEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.PesquisadorEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.QuestaoEntity
import com.jimmystarling.providapesquisasatisfacao.data.repository.PesquisaRepository

class PesquisaViewModel : ViewModel() {
    fun createPesquisa(context: Context, pesquisador: PesquisadorEntity, questoes: List<QuestaoEntity>, paciente: PacienteEntity){
        PesquisaRepository.createPesquisa(context, pesquisador, questoes, paciente)
    }

    fun updatePesquisa(paciente: String, question_title: String, question_content: String){

    }
}