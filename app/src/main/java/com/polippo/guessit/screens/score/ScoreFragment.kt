package com.polippo.guessit.screens.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.polippo.guessit.R
import com.polippo.guessit.databinding.ScoreFragmentBinding

class ScoreFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        //Inflate and obtain the instance of binding class
        val binding: ScoreFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.score_fragment, container, false
        )

        //Get args using by navArgs property delegate
        val scoreFragmentArgs by navArgs<ScoreFragmentArgs>()
        val viewModelFactory = ScoreViewModelFactory(scoreFragmentArgs.score)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(ScoreViewModel::class.java)

        binding.scoreViewModel = viewModel
        binding.lifecycleOwner = this


        viewModel.eventPlayAgain.observe(this.viewLifecycleOwner, Observer { playAgain ->
            if(playAgain){
                findNavController().navigate(ScoreFragmentDirections.actionRestart())
                viewModel.onPlayAgainComplete()
            }
        })

        return binding.root
    }



}

