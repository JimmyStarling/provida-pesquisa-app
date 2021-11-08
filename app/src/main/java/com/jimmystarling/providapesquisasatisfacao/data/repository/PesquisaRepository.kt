package com.jimmystarling.providapesquisasatisfacao.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.jimmystarling.providapesquisasatisfacao.data.database.ProvidaDatabase
import com.jimmystarling.providapesquisasatisfacao.data.model.PacienteEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.PesquisaEntity
import com.jimmystarling.providapesquisasatisfacao.data.model.QuestaoEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PesquisaRepository {

    companion object{

        var providaDatabase: ProvidaDatabase? = null

        var pesquisaEntity: LiveData<PesquisaEntity>? = null

        fun initializeDB(context: Context) : ProvidaDatabase {
            return ProvidaDatabase.getDataseClient(context)
        }

        fun registerPesquisa(context: Context, questoes: List<QuestaoEntity>, paciente: PacienteEntity) {

            LoginRepository.providaDatabase = initializeDB(context)

            CoroutineScope(Dispatchers.IO).launch {
                val pesquisaDetails = PesquisaEntity(questoes, paciente)
                providaDatabase!!.databaseDao().registerPesquisa(pesquisaDetails)
            }

        }

        fun updatePesquisa(context: Context, pesquisaEntity: PesquisaEntity, questoes: List<QuestaoEntity>, paciente: PacienteEntity) {

            LoginRepository.providaDatabase = initializeDB(context)

            CoroutineScope(Dispatchers.IO).launch {
                providaDatabase!!.databaseDao().updatePesquisa(pesquisaEntity.Id, questoes, paciente)
            }

        }

        fun getPesquisa(context: Context,  paciente: PacienteEntity): LiveData<PesquisaEntity>? {

            providaDatabase = initializeDB(context)

            pesquisaEntity = providaDatabase!!.databaseDao().getPesquisa(paciente)

            return pesquisaEntity

        }
    }
}