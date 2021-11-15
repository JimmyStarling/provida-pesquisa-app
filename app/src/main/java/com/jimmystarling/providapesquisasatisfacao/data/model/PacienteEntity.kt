package com.jimmystarling.providapesquisasatisfacao.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "paciente")
data class PacienteEntity (
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "contato")
    var contato: String,
    @ColumnInfo(name = "date")
    var date: String,
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null
}
