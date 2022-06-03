package ru.itis.morefy.game

import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import ru.itis.morefy.R
import ru.itis.morefy.databinding.FragmentQuizBinding

class QuizFragment : Fragment(R.layout.fragment_quiz) {

    private lateinit var rocketAnimation: AnimatedVectorDrawable
    private lateinit var binding: FragmentQuizBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentQuizBinding.bind(view)

        with(binding) {
            val d: Drawable = textView.background
            rocketAnimation = d as AnimatedVectorDrawable
            rocketAnimation.start()
        }
    }
}
