package ru.itis.morefy.core.presentation.fragments

import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.itis.morefy.R
import ru.itis.morefy.databinding.FragmentGameBinding
import ru.itis.morefy.databinding.FragmentHomeBinding
import ru.itis.morefy.databinding.FragmentQuizBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [QuizFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QuizFragment : Fragment(R.layout.fragment_quiz) {

    private lateinit var rocketAnimation: AnimatedVectorDrawable
    private lateinit var binding: FragmentQuizBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentQuizBinding.bind(view)

        with(binding){
            val d: Drawable = textView.background
            rocketAnimation = d as AnimatedVectorDrawable
            rocketAnimation.start()
            }
    }
}