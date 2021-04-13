package com.polippo.guessit.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel


/**
 * View model contain all the logic to run the game
 */
class GameViewModel: ViewModel() {

    //The current word
    private val _word = MutableLiveData<String>()
    val word: LiveData<String>
        get() = _word


    //The current score
    private val _score =  MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    //Says if the game is finished or not
    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish

    //The list of the words - the front of the list is the next word to guess
    lateinit var wordList: MutableList<String>

    init{
        Log.i("GameViewModel", "GameViewModel created")

        _score.value = 0
        _eventGameFinish.value = false

        resetList()
        nextWord()
    }

    /**
     * Reset the list of the words and randomize the order
     */
    private fun resetList(){
        wordList = mutableListOf(
            "rainha",
            "hospital",
            "basquete",
            "gato",
            "mudança",
            "lesma",
            "sopa",
            "calendario",
            "triste",
            "mesa",
            "guitarra",
            "casa",
            "trilho",
            "zebra",
            "geleia",
            "carro",
            "corvo",
            "troca",
            "sacola",
            "rolar",
            "bolha"
        )
        wordList.shuffle()
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord(){
        //Select and remove a word from the list
        if(wordList.isEmpty()){
            _eventGameFinish.value = true
        } else{
            _word.value = wordList.removeAt(0)
        }
    }

    /** Methods for buttons pressed **/
    fun onSkip(){
        _score.value = (score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect(){
        _score.value = (score.value)?.plus(1)
        nextWord()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed")
    }

    fun onGameFinishComplete(){
        _eventGameFinish.value = false
    }
}