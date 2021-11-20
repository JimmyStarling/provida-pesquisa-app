package com.jimmystarling.providapesquisasatisfacao.ui.questions

import android.content.Context
import androidx.lifecycle.ViewModel
import com.jimmystarling.providapesquisasatisfacao.data.model.PacienteEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.PesquisaEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.PesquisadorEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.QuestaoEntity
import com.jimmystarling.providapesquisasatisfacao.data.repository.PesquisaRepository

class PesquisaViewModel : ViewModel() {
    fun createPesquisa(context: Context, pesquisador: PesquisadorEntity, questoes: List<QuestaoEntity>, paciente: PacienteEntity){
        return PesquisaRepository.createPesquisa(context, pesquisador, questoes, paciente)
    }

    fun updatePesquisa(context: Context, pesquisa: PesquisaEntity, pesquisador: PesquisadorEntity, questoes: List<QuestaoEntity>, paciente: PacienteEntity){
        return PesquisaRepository.updatePesquisa(context, pesquisa, pesquisador, questoes, paciente)
    }
}
