package com.jimmystarling.providapesquisasatisfacao.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Pesquisa")
data class PesquisaEntity (
    @ColumnInfo(name = "questoes")
    var Questoes: List<QuestaoEntity>,
    @ColumnInfo(name = "paciente")
    var Paciente: PacienteEntity,
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var Id: Int? = null
}
