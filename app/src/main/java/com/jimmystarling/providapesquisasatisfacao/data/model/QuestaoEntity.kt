package com.jimmystarling.providapesquisasatisfacao.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class QuestaoEntity(
    @SerializedName("id")
    var id: Int? = null,
    //@ColumnInfo(name = "title")
    @SerializedName("title")
    var title: String,
    //@ColumnInfo(name = "content")
    @SerializedName("content")
    var content: String,
    //@ColumnInfo(name = "resposta")
    @SerializedName("resposta")
    var resposta: String,
)/*{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var Id: Int? = null
}*/
