package com.jimmystarling.providapesquisasatisfacao.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Paciente")
data class PacienteEntity (
    @ColumnInfo(name = "name")
    var Name: String,
    @ColumnInfo(name = "contato")
    var Contato: String,
    @ColumnInfo(name = "date")
    var Date: String,
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var Id: Int? = null
}
