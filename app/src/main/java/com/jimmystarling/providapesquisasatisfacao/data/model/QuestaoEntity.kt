package com.jimmystarling.providapesquisasatisfacao.data.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class QuestaoEntity(
    var id: Int? = null,
    //@ColumnInfo(name = "title")
    var title: String,
    //@ColumnInfo(name = "content")
    var content: String,
    //@ColumnInfo(name = "resposta")
    var resposta: String,
)/*{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var Id: Int? = null
}*/
