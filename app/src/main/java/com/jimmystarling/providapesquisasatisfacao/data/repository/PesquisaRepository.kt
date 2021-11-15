package com.jimmystarling.providapesquisasatisfacao.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.jimmystarling.providapesquisasatisfacao.data.database.ProvidaDatabase
import com.jimmystarling.providapesquisasatisfacao.data.model.PacienteEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.PesquisaEntity
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

        fun registerPesquisa(context: Context, questoes: List<QuestaoEntity>, paciente: PacienteEntity) {

            LoginRepository.providaDatabase = initializeDB(context)

            CoroutineScope(Dispatchers.IO).launch {
                val pesquisaDetails = PesquisaEntity(gson.toJson(questoes), gson.toJson(paciente))
                providaDatabase!!.databaseDao().registerPesquisa(pesquisaDetails)
            }

        }

        fun updatePesquisa(context: Context, pesquisaEntity: PesquisaEntity, questoes: List<QuestaoEntity>, paciente: PacienteEntity) {

            LoginRepository.providaDatabase = initializeDB(context)

            CoroutineScope(Dispatchers.IO).launch {
                providaDatabase!!.databaseDao().updatePesquisa(pesquisaEntity.Id, gson.toJson(questoes))
            }

        }

        fun getPesquisa(context: Context,  paciente: PacienteEntity): LiveData<PesquisaEntity>? {

            providaDatabase = initializeDB(context)

            pesquisaEntity = providaDatabase!!.databaseDao().getPesquisa(gson.toJson(paciente))

            return pesquisaEntity

        }
    }
}