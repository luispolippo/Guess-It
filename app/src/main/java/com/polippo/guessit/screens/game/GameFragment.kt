package com.polippo.guessit.screens.game

import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        binding.gameViewModel = viewModel

        binding.lifecycleOwner = this


        viewModel.eventGameFinish.observe(this.viewLifecycleOwner, Observer { hasFinish ->
            if(hasFinish){
                gameFinished()
                viewModel.onGameFinishComplete()
            }
        })

        viewModel.currentTime.observe(this.viewLifecycleOwner, Observer { newTime ->
            binding.timerText.text = DateUtils.formatElapsedTime(newTime)
        })

        return binding.root
    }


    /**
     * Called when the game is finished
     */
    private fun gameFinished(){
        val currentScore = viewModel.score.value ?: 0
        val action = GameFragmentDirections.actionGameToScore(currentScore)
        findNavController().navigate(action)
    }

}