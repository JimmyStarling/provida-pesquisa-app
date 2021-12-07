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

        private fun initializeDB(context: Context) : ProvidaDatabase {
            return ProvidaDatabase.getDataseClient(context)
        }

        fun registerPesquisador(context: Context, pesquisador: PesquisadorEntity) {

            providaDatabase = initializeDB(context)

            CoroutineScope(IO).launch {
                providaDatabase!!.databaseDao().registerPesquisador(pesquisador)
            }

        }


        fun searchPesquisador(context: Context, name: String, password: String) : LiveData<PesquisadorEntity>? {

            providaDatabase = initializeDB(context)

            pesquisadorEntity = providaDatabase!!.databaseDao().searchPesquisador(name, password)

            return pesquisadorEntity
        }

    }
}