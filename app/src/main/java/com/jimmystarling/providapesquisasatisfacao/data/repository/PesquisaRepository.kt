package com.jimmystarling.providapesquisasatisfacao.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.jimmystarling.providapesquisasatisfacao.data.database.ProvidaDatabase
import com.jimmystarling.providapesquisasatisfacao.data.model.PacienteEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.PesquisaEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.PesquisadorEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.QuestaoEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PesquisaRepository {

    companion object{

        var gson = Gson()

        var providaDatabase: ProvidaDatabase? = null

        var pesquisaEntity: LiveData<PesquisaEntity>? = null

        fun initializeDB(context: Context) : ProvidaDatabase {
            return ProvidaDatabase.getDataseClient(context)
        }

        fun createPesquisa(context: Context, pesquisador: PesquisadorEntity, questoes: List<QuestaoEntity>, paciente: PacienteEntity) {

            providaDatabase = initializeDB(context)

            CoroutineScope(Dispatchers.IO).launch {
                val pesquisaDetails = PesquisaEntity(gson.toJson(pesquisador), gson.toJson(questoes), gson.toJson(paciente))
                providaDatabase!!.databaseDao().createPesquisa(pesquisaDetails)
            }

        }

        // Here will pass the appended variable to questoes list and convert it to json
        fun updatePesquisa(context: Context, pesquisa: PesquisaEntity, pesquisador: PesquisadorEntity, questoes: List<QuestaoEntity>, paciente: PacienteEntity) {

            providaDatabase = initializeDB(context)

            CoroutineScope(Dispatchers.IO).launch {
                providaDatabase!!.databaseDao().updatePesquisa(pesquisa.Id, gson.toJson(pesquisador), gson.toJson(questoes), gson.toJson(paciente))
            }

        }

        fun getPesquisa(context: Context,  paciente: PacienteEntity): LiveData<PesquisaEntity>? {

            providaDatabase = initializeDB(context)

            pesquisaEntity = providaDatabase!!.databaseDao().getPesquisa(gson.toJson(paciente))

            return pesquisaEntity

        }

        // Here will pass the variable pesquisa appended to pesquisas converted to json to updatePesquisador
        fun updatePesquisador(context: Context, pesquisador: PesquisadorEntity, pesquisas: List<PesquisaEntity>, quantidade_pesquisa: Int){
            providaDatabase = initializeDB(context)

            val pesquisas_json = gson.toJson(pesquisas)
            val quantidade_pesquisas_json = gson.toJson(quantidade_pesquisa)

            CoroutineScope(Dispatchers.IO).launch {
                providaDatabase!!.databaseDao().updatePesquisador(pesquisador.Id, pesquisas_json, quantidade_pesquisas_json)
            }
        }
    }
}