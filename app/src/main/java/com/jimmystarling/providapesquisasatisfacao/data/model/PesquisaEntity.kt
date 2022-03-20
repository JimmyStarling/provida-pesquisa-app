package com.jimmystarling.providapesquisasatisfacao.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Entity(tableName = "Pesquisa")
@Serializable
data class PesquisaEntity(
    @ColumnInfo(name = "pesquisador")
    @SerializedName("pesquisador")
    var pesquisador: String,
    @ColumnInfo(name = "questoes")
    @SerializedName("questoes")
    var questoes: String,
    @ColumnInfo(name = "paciente")
    @SerializedName("paciente")
    var paciente: String,
    @ColumnInfo(name = "date")
    @SerializedName("date")
    var date: String,
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    var id: Int? = null
}
