package ru.itis.morefy.core.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import ru.itis.morefy.R
import ru.itis.morefy.databinding.FragmentGameBinding
import ru.itis.morefy.databinding.FragmentSettingsBinding

class GameFragment : Fragment(R.layout.fragment_game) {
    private lateinit var binding: FragmentGameBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGameBinding.bind(view)

        initBtnListeners()
    }

    private fun initBtnListeners(){
        with(binding){
            btQuiz.setOnClickListener{
                parentFragmentManager.beginTransaction()
                    .replace<QuizFragment>(R.id.container)
                    .addToBackStack(null)
                    .setReorderingAllowed(true)
                    .commit()
            }
        }

    }
}
