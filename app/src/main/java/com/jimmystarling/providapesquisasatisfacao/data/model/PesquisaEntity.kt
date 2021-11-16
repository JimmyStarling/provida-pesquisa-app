package com.jimmystarling.providapesquisasatisfacao.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Pesquisa")
data class PesquisaEntity(
    @ColumnInfo(name = "pesquisador")
    var pesquisador: String,
    @ColumnInfo(name = "questoes")
    var questoes: String,
    @ColumnInfo(name = "paciente")
    var paciente: String,
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var Id: Int? = null
}
