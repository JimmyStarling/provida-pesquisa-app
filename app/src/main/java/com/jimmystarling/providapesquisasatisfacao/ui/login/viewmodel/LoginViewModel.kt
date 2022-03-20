package com.jimmystarling.providapesquisasatisfacao.ui.login.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jimmystarling.providapesquisasatisfacao.data.model.PesquisaEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.PesquisadorEntity
import com.jimmystarling.providapesquisasatisfacao.data.repository.PesquisaRepository
import com.jimmystarling.providapesquisasatisfacao.data.repository.PesquisadorRepository

class LoginViewModel : ViewModel() {

    var livePesquisa: LiveData<List<PesquisaEntity>>? = null
    var livePesquisador: LiveData<PesquisadorEntity>? = null

    fun registerPesquisador(context: Context, pesquisador: PesquisadorEntity) {
        PesquisadorRepository.registerPesquisador(context, pesquisador)
    }

    fun searchPesquisador(context: Context, name: String, password: String) : LiveData<PesquisadorEntity>? {
        livePesquisador = PesquisadorRepository.searchPesquisador(context, name, password)
        return livePesquisador
    }

    fun searchPesquisadorPesquisas(context: Context, pesquisador: PesquisadorEntity) : LiveData<List<PesquisaEntity>>? {
        livePesquisa = PesquisaRepository.searchPesquisa(context, pesquisador)
        return livePesquisa
    }

}