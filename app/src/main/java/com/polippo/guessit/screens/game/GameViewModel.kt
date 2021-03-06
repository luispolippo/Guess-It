package com.polippo.guessit.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

private val CORRECT_BUZZ_PATTERN = longArrayOf(100, 100, 100, 100, 100, 100)
private val PANIC_BUZZ_PATTERN = longArrayOf(0, 200)
private val GAME_OVER_BUZZ_PATTERN = longArrayOf(0, 2000)
private val NO_BUZZ_PATTERN = longArrayOf(0)


/**
 * View model contain all the logic to run the game
 */
class GameViewModel: ViewModel() {

    companion object{
        //This is when the game is over
        const val DONE = 0L
        //This is the number of millisconds in a second
        const val ONE_SECOND = 1000L
        //This is the total time of the game
        const val COUNTDOWN_TIME = 60000L
        //The last 10 seconds of the game
        const val COUNTDOWN_PANIC_SECONDS = 10L
    }

    enum class BuzzType(val pattern: LongArray){
        CORRECT(CORRECT_BUZZ_PATTERN),
        GAME_OVER(GAME_OVER_BUZZ_PATTERN),
        COUNTDOWN_PANIC(PANIC_BUZZ_PATTERN),
        NO_BUZZ(NO_BUZZ_PATTERN)
    }

    private val timer: CountDownTimer

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

    //Says the current time
    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime

    private val _eventBuzz = MutableLiveData<BuzzType>()
    val eventBuzz: LiveData<BuzzType>
        get() = _eventBuzz

    val currentTimeString = Transformations.map(currentTime){ time ->
        DateUtils.formatElapsedTime(time)
    }
    //The list of the words - the front of the list is the next word to guess
    lateinit var wordList: MutableList<String>



    init{
        Log.i("GameViewModel", "GameViewModel created")

        _score.value = 0
        _eventGameFinish.value = false

        resetList()
        nextWord()

        timer = object: CountDownTimer(COUNTDOWN_TIME, ONE_SECOND){

            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = (millisUntilFinished/ ONE_SECOND)
                if(millisUntilFinished/ ONE_SECOND <= COUNTDOWN_PANIC_SECONDS){
                    _eventBuzz.value = BuzzType.COUNTDOWN_PANIC
                }
            }

            override fun onFinish() {
                _currentTime.value = DONE
                _eventBuzz.value = BuzzType.GAME_OVER
               _eventGameFinish.value = true
            }
        }
        timer.start()
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
            "mudan??a",
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
            resetList()
        }
        _word.value = wordList.removeAt(0)
    }

    /** Methods for buttons pressed **/
    fun onSkip(){
        _score.value = (score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect(){
        _score.value = (score.value)?.plus(1)
        _eventBuzz.value = BuzzType.CORRECT
        nextWord()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed")
        timer.cancel()
    }

    fun onGameFinishComplete(){
        _eventGameFinish.value = false
    }

    fun onBuzzComplete(){
        _eventBuzz.value = BuzzType.NO_BUZZ
    }
}