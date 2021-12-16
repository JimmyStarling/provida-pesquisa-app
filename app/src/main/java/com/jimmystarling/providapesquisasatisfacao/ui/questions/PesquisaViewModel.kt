package com.jimmystarling.providapesquisasatisfacao.ui.questions

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jimmystarling.providapesquisasatisfacao.data.model.PacienteEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.PesquisaEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.PesquisadorEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.QuestaoEntity
import com.jimmystarling.providapesquisasatisfacao.data.repository.PesquisaRepository

class PesquisaViewModel : ViewModel() {
    fun registerPesquisa(context: Context, pesquisa: PesquisaEntity) {
        return PesquisaRepository.registerPesquisa(context, pesquisa)
    }

    fun searchPesquisa(context: Context, pesquisador: PesquisadorEntity): LiveData<List<PesquisaEntity>>? {
        return PesquisaRepository.searchPesquisa(context, pesquisador)
    }

    fun updatePesquisa(context: Context, id: Int?, questoes: MutableList<QuestaoEntity>){
        return PesquisaRepository.updatePesquisa(context, id, questoes)
    }
}
