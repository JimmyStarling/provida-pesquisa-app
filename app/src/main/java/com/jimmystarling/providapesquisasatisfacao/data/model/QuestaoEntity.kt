package com.jimmystarling.providapesquisasatisfacao.data.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class QuestaoEntity(
    var Id: Int? = null,
    //@ColumnInfo(name = "title")
    var Title: String,
    //@ColumnInfo(name = "content")
    var Content: String,
    //@ColumnInfo(name = "resposta")
    var Resposta: String,
)/*{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var Id: Int? = null
}*/
