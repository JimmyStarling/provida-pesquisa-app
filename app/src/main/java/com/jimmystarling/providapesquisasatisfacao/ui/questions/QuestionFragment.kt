package com.jimmystarling.providapesquisasatisfacao.ui.questions

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jimmystarling.providapesquisasatisfacao.R
import com.jimmystarling.providapesquisasatisfacao.ui.questions.R

class QuestionFragment : Fragment() {

    companion object {
        fun newInstance() = QuestionFragment()
    }

    private lateinit var viewModel: QuestionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.question_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(QuestionViewModel::class.java)
        // TODO: Use the ViewModel
    }

}