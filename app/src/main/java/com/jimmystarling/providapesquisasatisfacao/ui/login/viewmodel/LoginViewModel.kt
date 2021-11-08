package com.jimmystarling.providapesquisasatisfacao.ui.login.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jimmystarling.providapesquisasatisfacao.data.model.PesquisadorEntity
import com.jimmystarling.providapesquisasatisfacao.data.repository.LoginRepository

class LoginViewModel : ViewModel() {

    //var liveDataLogin: LiveData<LoginTableModel>? = null
    var livePesquisador: LiveData<PesquisadorEntity>? = null

    fun registerPesquisador(context: Context, username: String, password: String) {
        LoginRepository.registerPesquisador(context, username, password)
    }

    fun getPesquisador(context: Context, name: String, password: String) : LiveData<PesquisadorEntity>? {
        livePesquisador = LoginRepository.getPesquisador(context, name, password)
        return livePesquisador
    }

}