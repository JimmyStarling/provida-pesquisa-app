package com.jimmystarling.providapesquisasatisfacao.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jimmystarling.providapesquisasatisfacao.data.model.QuestaoEntity

class FragmentsViewModel : ViewModel() {
    val questionsMessage = MutableLiveData<MutableList<QuestaoEntity>>()

    fun setQuestions(question:MutableList<QuestaoEntity>){
        questionsMessage.value = question
    }
}