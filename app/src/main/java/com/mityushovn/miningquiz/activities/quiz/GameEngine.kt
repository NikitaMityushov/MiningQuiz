package com.mityushovn.miningquiz.activities.quiz

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.mityushovn.miningquiz.MiningQuizApplication
import com.mityushovn.miningquiz.R
import com.mityushovn.miningquiz.models.AttemptExam
import com.mityushovn.miningquiz.models.AttemptTopic
import com.mityushovn.miningquiz.models.Question
import com.mityushovn.miningquiz.repository.attemptsRepository.AttemptsRepositoryAPI
import com.mityushovn.miningquiz.repository.questionsRepository.QuestionsRepositoryAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.IllegalStateException

private const val ILLEGAL_GAME_STATE = "Game mode must be chosen! Smth with logic."

/**
 * @author Nikita Mityushov 25.04.22
 * @since 1.0
 *
 * Class represents a quiz game.
 * To start a game you need to pass id of the exam or topic, and game mode to the startGame(id, mode) method.
 * @see GameMode
 * @see startGame
 */
class GameEngine(
    private val questionsRepository: QuestionsRepositoryAPI,
    private val attemptsRepository: AttemptsRepositoryAPI,
    app: Application
) : AndroidViewModel(app) {
    /**
     * Enum represents all possible game modes.
     * @property NONE is initial state.
     */
    enum class GameMode {
        NONE,
        EXAM,
        TOPIC,
//        MISTAKES
    }

    /**
     * @property cacheGameMode
     * @property cacheId this 2 properties cache game mode and exam or topic ids for the following repeating the game.
     */
    private var cacheId: Int = 0
    private var cacheGameMode: GameMode = GameMode.NONE

    /**
     * @property _rightAns is a private counter of given right answers of the game.
     */
    private var _rightAns = 0
        set(value) {
            synchronized(this) {
                field = value
            }
        }

    /**
     * @property _currentQuestion is a private pointer(counter) of current question of the game.
     */
    private var _currentQuestion: Int = 0
        set(value) {
            synchronized(this) {
                field = value
            }
        }

    /**
     * @property _numberOfQuestions is a questions number of the game.
     */
    private var _numberOfQuestions: Int = 0
        set(value) {
            synchronized(this) {
                field = value
            }
        }

    // observable next question
    /**
     * @property nextQuestion is an observable property to get next question for drawing in the UI.
     */
    private val _nextQuestion = MutableLiveData<Question>()
    val nextQuestion: LiveData<Question>
        get() = _nextQuestion

    // prepared strings:
    // for PreviewFragment
    private val _stringForPreviewGameTextView = MutableLiveData("")
    val stringForPreviewGameTextView: LiveData<String>
        get() = _stringForPreviewGameTextView

    // for GameFragment
    private val _stringForGameFrQuestionContent = MutableLiveData("")
    val stringForGameFrQuestionContent: LiveData<String>
        get() = _stringForGameFrQuestionContent

    private val _stringForGameFrQuestionTopic = MutableLiveData("")
    val stringForGameFrQuestionTopic: LiveData<String>
        get() = _stringForGameFrQuestionTopic

    // for CongratsFragment
    private val _stringForCongratsFrTV = MutableLiveData("")
    val stringForCongratsFrTV: LiveData<String>
        get() = _stringForCongratsFrTV

    // for FailedFragment
    private val _stringForFailedFrTV = MutableLiveData("")
    val stringForFailedFrTV: LiveData<String>
        get() = _stringForFailedFrTV

    // An iterator through list of questions
    private var _questions: ListIterator<Question> = emptyList<Question>().listIterator()

    private fun sendSearchRequestExam(examId: Int) {
        Timber.d("Id of exam or topic is: $examId")
        viewModelScope.launch(Dispatchers.Default) {
            questionsRepository
                .getRandomQuestionsFromExamIdAndNumberOfQuestions(examId)
                .onEach {
                    clearGameInternalCounters() // clear all counters, set 0 to start new game
                    _numberOfQuestions = it.size
                    _stringForPreviewGameTextView.postValue(
                        String.format(
                            getApplication<MiningQuizApplication>().resources.getString(
                                R.string.question_contains_n_questions
                            ), it.size, it.size * 80 / 100
                        )
                    ) // "This exam contains %d questions. You need to answer at least %d questions to pass exam. Good luck!
                    // debug
                    it.forEach { q ->
                        Timber.d(q.content)
                    }
                    // end debug
                }
                .map {
                    it.listIterator()
                }
//                .flowOn(Dispatchers.Default) // computation threads
                .collect {
                    _questions = it
                }
        }
    }

    private fun sendSearchRequestTopic(topicId: Int) {
        Timber.d("Topic game mode is chosen!")
        viewModelScope.launch(Dispatchers.Default) {
            questionsRepository
                .getAllQuestionsFromTopic(topicId)
                .onEach {
                    clearGameInternalCounters() // clear all counters, set 0 to start new game
                    _numberOfQuestions = it.size
                    _stringForPreviewGameTextView.postValue(
                        String.format(
                            getApplication<MiningQuizApplication>().resources.getString(
                                R.string.question_contains_n_questions
                            ), it.size, it.size * 80 / 100
                        )
                    ) // "This exam contains %d questions. You need to answer at least %d questions to pass exam. Good luck!
                    // debug
                    it.forEach { q ->
                        Timber.d(q.content)
                    }
                    // end debug
                }
                .map {
                    it.listIterator()
                }
//                .flowOn(Dispatchers.Default) // computation threads
                .collect {
                    _questions = it
                }
        }
    }

    private fun startOrRepeatGameFromCache() {
        when (cacheGameMode) {
            GameMode.EXAM -> sendSearchRequestExam(cacheId)
            GameMode.TOPIC -> sendSearchRequestTopic(cacheId)
//            GameMode.MISTAKES -> sendSearchRequestMistakes(id) todo
            else -> {
                Timber.e(IllegalStateException(ILLEGAL_GAME_STATE))
                throw IllegalStateException(ILLEGAL_GAME_STATE)
            }
        }
    }

    // public API for quiz
    fun startGame(id: Int, mode: GameMode) {
        // caching ids for repeating the game
        cacheId = id
        cacheGameMode = mode
        startOrRepeatGameFromCache()
    }

    fun repeatGame() {
        startOrRepeatGameFromCache()
    }

    fun nextQuestion(): Boolean {
        if (!_questions.hasNext()) {
            return false
        }
        viewModelScope.launch(Dispatchers.Default) {
            // 1) get the question
            val question = _questions.next()
            ++_currentQuestion // 2) increment counter of current question

            // 3) format strings
            _stringForGameFrQuestionContent.postValue(
                String.format(
                    getApplication<MiningQuizApplication>().resources.getString(
                        R.string.question_from_content
                    ), _currentQuestion, _numberOfQuestions, question.content
                )
            ) //Question %d from %d:\n%s

            _stringForGameFrQuestionTopic.postValue(
                String.format(
                    getApplication<MiningQuizApplication>().resources.getString(
                        R.string.topic_content
                    ), question.nameTopic
                )
            ) // Topic:\n%s

            // 4) emit question
            _nextQuestion.postValue(question)
        }
        return true
    }

    fun endGame(): Boolean {
        Timber.d("End game, statistics: right answers: $_rightAns, total questions: $_numberOfQuestions")
        val result = (_rightAns.toFloat() / _numberOfQuestions.toFloat()) >= 0.799

        if (result) {
            with(viewModelScope) {
                launch(Dispatchers.Default) {
                    _stringForCongratsFrTV.postValue(
                        String.format(
                            getApplication<MiningQuizApplication>().resources.getString(R.string.congrats_fr_tv_text),
                            _rightAns,
                            _numberOfQuestions
                        )
                    ) // Congratulations, you passed the exam with answered %d from %d questions
                }
                // send attempt to the storage
                launch {
                    createSuccessAttempt()
                }
            }
        } else {
            with(viewModelScope) {
                launch(Dispatchers.Default) {
                    _stringForFailedFrTV.postValue(
                        String.format(
                            getApplication<MiningQuizApplication>().resources.getString(R.string.failed_fr_tv_text),
                            _rightAns,
                            _numberOfQuestions
                        )
                    ) // Sorry, but you didn\'t\'t pass the exam. You answered only %d from %d questions.
                }
                // send attempt to the storage
                launch {
                    createFailedAttempt()
                }
            }
        }

        return result
    }

    fun rightAnswerGiven() {
        viewModelScope.launch(Dispatchers.Default) {
            ++_rightAns
        }
    }

    fun wrongAnswerGiven() {
        // TODO: 26.04.2022 for mistakes mode
    }

    private fun clearGameInternalCounters() {
        _currentQuestion = 0
        _rightAns = 0
    }

    private suspend fun createFailedAttempt() {
        Timber.d(Thread.currentThread().name)
        Timber.d("createFailedAttempt, cacheGameMode is $cacheGameMode, id is $cacheId")
        when (cacheGameMode) {
            GameMode.EXAM -> {
                attemptsRepository.insertAttemptExam(AttemptExam(examId = cacheId, success = false))
                    .collect {
                        showIsAttemptAddedToast(it)
                    }
            }
            GameMode.TOPIC -> {
                attemptsRepository.insertAttemptTopic(
                    AttemptTopic(
                        topicId = cacheId,
                        success = false
                    )
                ).collect {
                    showIsAttemptAddedToast(it)
                }
            }
            else -> {
                Timber.e(IllegalStateException(ILLEGAL_GAME_STATE))
                throw IllegalStateException(ILLEGAL_GAME_STATE)
            }
        }
    }

    private suspend fun createSuccessAttempt() {
        Timber.d("createSuccessAttempt, cacheGameMode is $cacheGameMode, id is $cacheId")
        when (cacheGameMode) {
            GameMode.EXAM -> {
                attemptsRepository.insertAttemptExam(AttemptExam(examId = cacheId, success = true))
                    .collect {
                        showIsAttemptAddedToast(it)
                    }
            }
            GameMode.TOPIC -> {
                attemptsRepository.insertAttemptTopic(
                    AttemptTopic(
                        topicId = cacheId,
                        success = true
                    )
                ).collect {
                    showIsAttemptAddedToast(it)
                }
            }
            else -> {
                Timber.e(IllegalStateException(ILLEGAL_GAME_STATE))
                throw IllegalStateException(ILLEGAL_GAME_STATE)
            }
        }
    }

    /**
     * Creates Toast depends on boolean argument of successfully added attempt.
     */
    private fun showIsAttemptAddedToast(it: Boolean) {
        if (it) {
            Toast.makeText(
                getApplication<MiningQuizApplication>(),
                getApplication<MiningQuizApplication>().resources.getString(R.string.attempt_succ_added),
                Toast.LENGTH_LONG
            ).show() // Attempt successfully added
        } else {
            Toast.makeText(
                getApplication<MiningQuizApplication>(),
                getApplication<MiningQuizApplication>().resources.getString(R.string.attempt_failed_saved),
                Toast.LENGTH_LONG
            ).show() // Attempt would not be saved
        }
    }
}