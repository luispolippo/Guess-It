package com.polippo.guessit.screens.game

import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.getSystemService
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

        viewModel.eventBuzz.observe(this.viewLifecycleOwner, Observer { buzzType ->
            if(buzzType != GameViewModel.BuzzType.NO_BUZZ){
                buzz(buzzType.pattern)
                viewModel.onBuzzComplete()
            }
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

    private fun buzz(pattern: LongArray){
        val buzzer = activity?.getSystemService<Vibrator>()

        buzzer?.let{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                buzzer.vibrate(VibrationEffect.createWaveform(pattern, -1))
            } else {
                buzzer.vibrate(pattern, -1)
            }
        }
    }

}