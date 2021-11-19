package com.jimmystarling.providapesquisasatisfacao.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Pesquisador")
data class PesquisadorEntity (

    @ColumnInfo(name = "name")
    var Name: String,
    @ColumnInfo(name = "pesquisas")
    var Pesquisas: String,
    @ColumnInfo(name = "quantidade_pesquisas")
    var PesquisasContagem: Int,
    @ColumnInfo(name = "password")
    var Password: String

) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var Id: Int? = null

}