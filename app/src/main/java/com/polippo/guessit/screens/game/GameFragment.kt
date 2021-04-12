package com.polippo.guessit.screens.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.polippo.guessit.R
import com.polippo.guessit.databinding.GameFragmentBinding

/**
 * Fragment where the game is played
 */
class GameFragment: Fragment() {

    lateinit var viewModel: GameViewModel

    lateinit var binding: GameFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //Inflate view and obtain a instance of binding class
        binding = DataBindingUtil.inflate(inflater, R.layout.game_fragment, container, false)

        Log.i("GameFragment", "Called ViewModelProviders.of")
        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)



        binding.correctButton.setOnClickListener{
            viewModel.onCorrect()
            updateWordText()
            updateScoreText()
        }
        binding.skipeButton.setOnClickListener {
            viewModel.onSkip()
            updateWordText()
            updateScoreText()
        }
        updateScoreText()
        updateWordText()
        return binding.root
    }


    /**
     * Called when the game is finished
     */
    private fun gameFinished(){
        val action = GameFragmentDirections.actionGameToScore(viewModel.score)
        findNavController().navigate(action)
    }





    /** Methods for update the UI **/
    private fun updateWordText(){
        binding.wordText.text = viewModel.word
    }

    private fun updateScoreText(){
        binding.scoreText.text = viewModel.score.toString()
    }


}