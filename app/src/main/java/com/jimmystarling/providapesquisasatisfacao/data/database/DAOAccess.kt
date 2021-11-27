package com.jimmystarling.providapesquisasatisfacao.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jimmystarling.providapesquisasatisfacao.data.model.*

@Dao
interface DAOAccess {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun registerPesquisador(pesquisadorEntity: PesquisadorEntity)

    @Query("SELECT * FROM Pesquisador WHERE name =:name AND password =:password")
    fun getPesquisador(name: String?, password: String?) : LiveData<PesquisadorEntity>

    @Query("UPDATE Pesquisador SET pesquisas =:pesquisas AND pesquisas_quantidade =:pesquisas_quantidade WHERE id =:id ")
    fun updatePesquisador(id: Int?, pesquisas: String, pesquisas_quantidade: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createPesquisa(pesquisaEntity: PesquisaEntity)

    @Query("SELECT * FROM Pesquisa WHERE paciente =:paciente")
    fun getPesquisa(paciente: String) : LiveData<PesquisaEntity>

    @Query("UPDATE Pesquisa SET pesquisador =:pesquisador AND questoes =:questoes AND paciente = :paciente WHERE id = :id")
    fun updatePesquisa(id: Int?, pesquisador: String, questoes: String, paciente: String)

}