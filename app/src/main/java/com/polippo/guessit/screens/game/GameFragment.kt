package com.polippo.guessit.screens.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.polippo.guessit.R
import com.polippo.guessit.databinding.GameFragmentBinding

/**
 * Fragment where the game is played
 */
class GameFragment: Fragment() {

    //The current word
    private var word = ""

    //The current score
    private var score = 0

    //The list of the words - the front of the list is the next word to guess
    lateinit var wordList: MutableList<String>

    lateinit var binding: GameFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //Inflate view and obtain a instance of binding class
        binding = DataBindingUtil.inflate(inflater, R.layout.game_fragment, container, false)


        resetList()
        nextWord()

        binding.correctButton.setOnClickListener{ onCorrect() }
        binding.skipeButton.setOnClickListener { onSkip() }
        updateScoreText()
        updateWordText()
        return binding.root
    }


    /**
     * Reset the list of the words and randomize the order
     */
    private fun resetList(){
        wordList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
        )
        wordList.shuffle()
    }

    /**
     * Called when the game is finished
     */
    private fun gameFinished(){
        val action = GameFragmentDirections.actionGameToScore(score)
        findNavController().navigate(action)
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord(){
        //Select and remove a word from the list
        if(wordList.isEmpty()){
            gameFinished()
        } else{
            word = wordList.removeAt(0)
        }
        updateWordText()
        updateScoreText()
    }

    /** Methods for buttons pressed **/
    private fun onSkip(){
        score--
        nextWord()
    }

    private fun onCorrect(){
        score++
        nextWord()
    }

    /** Methods for update the UI **/
    private fun updateWordText(){
        binding.wordText.text = word
    }

    private fun updateScoreText(){
        binding.scoreText.text = score.toString()
    }


}