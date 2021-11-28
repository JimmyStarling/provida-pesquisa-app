package com.jimmystarling.providapesquisasatisfacao.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.jimmystarling.providapesquisasatisfacao.data.database.ProvidaDatabase
import com.jimmystarling.providapesquisasatisfacao.data.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class PesquisadorRepository {

    companion object {

        var gson = Gson()

        var providaDatabase: ProvidaDatabase? = null

        var pesquisadorEntity: LiveData<PesquisadorEntity>? = null

        // Initial data to insert to new Pesquisador entity
        val listaQuestao = gson.toJson(mutableListOf<QuestaoEntity>(
            QuestaoEntity(1, "Equipe da Enfermagem", "Qual a nota de 0 a 5", "0"),
            QuestaoEntity(2, "Equipe da Vacina", "Qual a nota de 0 a 5", "0"),
            QuestaoEntity(3, "Equipe da Administração", "Qual a nota de 0 a 5", "0"),
        ))
        val pacienteInicial = gson.toJson(PacienteEntity("Rodrigo Alves", "071983565628", "07/11/2021"))
        var pesquisasIniciais = gson.toJson(PesquisaEntity(
            "Nome do Pesquisador",
            listaQuestao,
            pacienteInicial
        ))

        fun initializeDB(context: Context) : ProvidaDatabase {
            return ProvidaDatabase.getDataseClient(context)
        }

        fun registerPesquisador(context: Context, pesquisador: PesquisadorEntity) {

            providaDatabase = initializeDB(context)

            //pesquisas = PesquisaEntity(pesquisador.name, "", "")

            //var pesquisas_dados = gson.toJson(pesquisas)
            //val quantidade_pesquisas: Array<String> = pesquisas_dados.split(",").toTypedArray()

            CoroutineScope(IO).launch {
                //val pesquisadorDados = PesquisadorEntity(pesquisador.name, pesquisas_dados, quantidade_pesquisas.size, pesquisador.password)
                providaDatabase!!.databaseDao().registerPesquisador(pesquisador)
            }

        }


        fun getPesquisador(context: Context, name: String, password: String) : LiveData<PesquisadorEntity>? {

            providaDatabase = initializeDB(context)

            pesquisadorEntity = providaDatabase!!.databaseDao().getPesquisador(name, password)

            return pesquisadorEntity
        }

    }
}