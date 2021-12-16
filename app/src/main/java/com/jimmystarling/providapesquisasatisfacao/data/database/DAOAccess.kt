package com.jimmystarling.providapesquisasatisfacao.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jimmystarling.providapesquisasatisfacao.data.model.*

@Dao
interface DAOAccess {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun registerPesquisador(pesquisadorEntity: PesquisadorEntity)

    @Query("SELECT * FROM Pesquisador WHERE name =:name AND password =:password")
    fun searchPesquisador(name: String?, password: String?) : LiveData<PesquisadorEntity>

    @Query("UPDATE Pesquisador SET pesquisas_quantidade =:pesquisas_quantidade WHERE id =:id ")
    fun updatePesquisador(id: Int?, pesquisas_quantidade: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun registerPesquisa(pesquisaEntity: PesquisaEntity)

    @Query("SELECT * FROM Pesquisa WHERE pesquisador =:pesquisador")
    fun getPesquisas(pesquisador: String) : LiveData<List<PesquisaEntity>>

    @Query("SELECT * FROM Pesquisa WHERE pesquisador =:pesquisador")
    fun searchPesquisa(pesquisador: String) : LiveData<List<PesquisaEntity>>

    @Query("UPDATE Pesquisa SET questoes =:questoes WHERE id = :id")
    fun updatePesquisa(id: Int?, questoes: String)

}