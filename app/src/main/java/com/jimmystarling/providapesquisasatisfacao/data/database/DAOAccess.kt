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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun registerPesquisa(pesquisaEntity: PesquisaEntity)

    @Query("SELECT * FROM Pesquisa WHERE paciente =:paciente")
    fun getPesquisa(paciente: PacienteEntity?) : LiveData<PesquisaEntity>

    @Query("UPDATE Pesquisa SET questoes = :questoes WHERE id = :id")
    fun updatePesquisa(id: Int?, questoes: MutableList<QuestaoEntity>, paciente: PacienteEntity?)

}