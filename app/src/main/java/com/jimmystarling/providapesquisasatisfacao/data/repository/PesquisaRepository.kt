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
        var pesquisas: LiveData<List<PesquisaEntity>>? = null

        private fun initializeDB(context: Context) : ProvidaDatabase {
            return ProvidaDatabase.getDataseClient(context)
        }

        fun registerPesquisa(context: Context, pesquisa: PesquisaEntity) {

            providaDatabase = initializeDB(context)

            CoroutineScope(Dispatchers.IO).launch {
                val mPesquisa = PesquisaEntity(
                    pesquisa.pesquisador,
                    pesquisa.questoes,
                    pesquisa.paciente,
                    pesquisa.date
                )
                providaDatabase!!.databaseDao().registerPesquisa(
                    mPesquisa
                )
            }

        }

        // Here will pass the appended variable to questoes list and convert it to json
        fun updatePesquisa(
            context: Context,
            pesquisa: PesquisaEntity,
        ) {

            providaDatabase = initializeDB(context)

            CoroutineScope(Dispatchers.IO).launch {
                providaDatabase!!.databaseDao().updatePesquisa(pesquisa.id, pesquisa.pesquisador, pesquisa.questoes, pesquisa.paciente)
            }

        }

        fun searchPesquisa(context: Context,  pesquisador: PesquisadorEntity): LiveData<List<PesquisaEntity>>? {

            providaDatabase = initializeDB(context)

            pesquisas = providaDatabase!!.databaseDao().searchPesquisa(gson.toJson(pesquisador))

            return pesquisas

        }

        // Update Pesquisador
        // Here will pass the variable pesquisa appended to pesquisas converted to json to updatePesquisador
        fun updatePesquisador(context: Context, pesquisador: PesquisadorEntity, pesquisas_quanidade: Int){
            providaDatabase = initializeDB(context)

            CoroutineScope(Dispatchers.IO).launch {
                providaDatabase!!.databaseDao().updatePesquisador(pesquisador.id, pesquisas_quanidade)
            }
        }
    }
}