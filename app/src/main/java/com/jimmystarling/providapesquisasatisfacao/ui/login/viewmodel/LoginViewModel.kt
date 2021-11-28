package com.jimmystarling.providapesquisasatisfacao.ui.login.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jimmystarling.providapesquisasatisfacao.data.model.PesquisaEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.PesquisadorEntity
import com.jimmystarling.providapesquisasatisfacao.data.repository.PesquisadorRepository

class LoginViewModel : ViewModel() {

    //var liveDataLogin: LiveData<LoginTableModel>? = null
    var livePesquisador: LiveData<PesquisadorEntity>? = null

    fun registerPesquisador(context: Context, pesquisador: PesquisadorEntity) {
        PesquisadorRepository.registerPesquisador(context, pesquisador)
    }

    fun getPesquisador(context: Context, name: String, password: String) : LiveData<PesquisadorEntity>? {
        livePesquisador = PesquisadorRepository.getPesquisador(context, name, password)
        return livePesquisador
    }

}