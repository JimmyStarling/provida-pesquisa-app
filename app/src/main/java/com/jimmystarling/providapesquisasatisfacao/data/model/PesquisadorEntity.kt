package com.jimmystarling.providapesquisasatisfacao.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

@Entity(tableName = "Pesquisador")
@Serializable
data class PesquisadorEntity (

    @ColumnInfo(name = "name")
    @SerializedName("name")
    var name: String,
    @ColumnInfo(name = "pesquisas")
    @SerializedName("pesquisas")
    var pesquisas: String,
    @ColumnInfo(name = "pesquisas_quantidade")
    @SerializedName("pesquisas_quantidade")
    var pesquisas_quantidade: Int,
    @ColumnInfo(name = "password")
    @SerializedName("password")
    var password: String

) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @SerializedName("Id")
    var id: Int? = null

}