package com.jimmystarling.providapesquisasatisfacao.data.utils

import com.jimmystarling.providapesquisasatisfacao.R

class Constants {
    companion object {
        val SECOND_FRAGMENT: String = "FRAGMENT_AGILIDADE"
        val THIRD_FRAGMENT: String = "FRAGMENT_CALLCENTER"
        val FOURTH_FRAGMENT: String = "FRAGMENT_LIMPEZA"
        val FIVETH_FRAGMENT: String = "FRAGMENT_ESTRUTURA"
        val SIXTH_FRAGMENT: String = "FRAGMENT_GERAL"
        val LAST_FRAGMENT: String = "FRAGMENT_FINISH"

        val SLIDE_DICTIONARY: Map<Int, String> = mapOf<Int, String>(
            1 to "Ruim",
            4 to "Regular",
            7 to "Bom",
            10 to "Ótimo"
        )

        val RESEARCHER = "RESEARCHER"
        val CLIENT = "CLIENTE"
        val SURVEY = "PESQUISA"
    }
}