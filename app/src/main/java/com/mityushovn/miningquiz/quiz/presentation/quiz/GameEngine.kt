package com.mityushovn.miningquiz.quiz.presentation.quiz

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mityushovn.miningquiz.common.MiningQuizApplication
import com.mityushovn.miningquiz.R
import com.mityushovn.miningquiz.quiz.presentation.quiz.GameEngine.GameMode
import com.mityushovn.miningquiz.quiz.presentation.quiz.GameEngine.GameMode.NONE
import com.mityushovn.miningquiz.common.domain.models.AttemptExam
import com.mityushovn.miningquiz.common.domain.models.AttemptTopic
import com.mityushovn.miningquiz.common.domain.repositories.AttemptsRepositoryAPI
import com.mityushovn.miningquiz.common.domain.repositories.QuestionsRepositoryAPI
import com.mityushovn.miningquiz.common.utils.Event
import com.mityushovn.miningquiz.common.utils.LiveEvent
import com.mityushovn.miningquiz.common.utils.postEvent
import com.mityushovn.miningquiz.common.utils.share
import com.mityushovn.miningquiz.quiz.domain.models.QuestionUIModel
import com.mityushovn.miningquiz.quiz.domain.models.toQuestionUIModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import java.util.*
import java.util.concurrent.ConcurrentLinkedDeque

private const val ILLEGAL_GAME_STATE = "Game mode must be chosen! Something with logic."

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
    private val backgroundDispatcher: CoroutineDispatcher = Dispatchers.Default,
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
    private var cacheGameMode: GameMode = NONE

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
     * @property _currentQuestion is a private current question of the game.
     */
    private var _currentQuestion: QuestionUIModel? = null

    /**
     * @property _numberOfQuestions is a questions number of the game.
     */
    private var _numberOfQuestions: Int = 0
        set(value) {
            synchronized(this) {
                field = value
            }
        }

    /**
     * @property loading is an observable property of loading questions from database.
     * Since loading of questions happens before onViewCreated, initial value of [_loading] is true.
     */
    private val _loading = MutableStateFlow<Boolean>(true)
    val loading: StateFlow<Boolean>
        get() = _loading

    // observable next question
    /**
     * @property nextQuestion is an observable property to get next question for drawing in the UI.
     */
    private val _nextQuestion = MutableLiveData<QuestionUIModel>()
    val nextQuestion: LiveData<QuestionUIModel>
        get() = _nextQuestion

    // prepared strings:
    // for PreviewFragment
    private val _stringForPreviewGameTextView = MutableStateFlow("")
    val stringForPreviewGameTextView: StateFlow<String>
        get() = _stringForPreviewGameTextView

    // for GameFragment
    private val _stringForGameFrQuestionContent = MutableStateFlow("")
    val stringForGameFrQuestionContent: StateFlow<String>
        get() = _stringForGameFrQuestionContent

    private val _stringForGameFrQuestionTopic = MutableStateFlow("")
    val stringForGameFrQuestionTopic: StateFlow<String>
        get() = _stringForGameFrQuestionTopic

    // for CongratsFragment
    private val _stringForCongratsFrTV = MutableStateFlow("")
    val stringForCongratsFrTV: StateFlow<String>
        get() = _stringForCongratsFrTV

    // for FailedFragment
    private val _stringForFailedFrTV = MutableStateFlow("")
    val stringForFailedFrTV: StateFlow<String>
        get() = _stringForFailedFrTV

    // A deque of questions
    private var _questions: Deque<QuestionUIModel> = ConcurrentLinkedDeque()

    private val _showIsAttemptAddedToast = MutableStateFlow<Event<Boolean>>(Event())
    val showIsAttemptAddedToast: LiveEvent<Boolean> = _showIsAttemptAddedToast.share()

    /**
     *
     */
    private fun sendSearchRequestExam(examId: Int) {
        viewModelScope.launch(backgroundDispatcher) {
            questionsRepository
                .getRandomQuestionsFromExamIdAndNumberOfQuestions(examId)
                .onEach {
                    clearGameInternalCounters() // clear all counters, set 0 to start new game
                    _numberOfQuestions = it.size
                    _stringForPreviewGameTextView.value =
                        String.format(
                            getApplication<MiningQuizApplication>().resources.getString(
                                R.string.question_contains_n_questions
                            ), it.size, it.size * 80 / 100
                        ) // "This exam contains %d questions. You need to answer at least %d questions to pass exam. Good luck!
                }.onStart {
                    _loading.value = true
                }
                .collect {
                    _questions = it.mapIndexed { index, question ->
                        question.toQuestionUIModel(index + 1)
                    }.toCollection(ConcurrentLinkedDeque())
                    _loading.value = false
                }
        }
    }

    /**
     *
     */
    private fun sendSearchRequestTopic(topicId: Int) {
        viewModelScope.launch(backgroundDispatcher) {
            questionsRepository
                .getAllQuestionsFromTopic(topicId)
                .onEach {
                    clearGameInternalCounters() // clear all counters, set 0 to start new game
                    _numberOfQuestions = it.size
                    _stringForPreviewGameTextView.value =
                        String.format(
                            getApplication<MiningQuizApplication>().resources.getString(
                                R.string.question_contains_n_questions
                            ), it.size, it.size * 80 / 100
                        ) // "This exam contains %d questions. You need to answer at least %d questions to pass exam. Good luck!
                }.onStart {
                    _loading.value = true
                }
                .collect {
                    _questions = it.mapIndexed { index, question ->
                        question.toQuestionUIModel(index + 1)
                    }.toCollection(ConcurrentLinkedDeque())

                    _loading.value = false

                }
        }
    }

    /**
     *
     */
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
    /**
     *
     */
    fun startGame(id: Int, mode: GameMode) {
        // caching ids for repeating the game
        cacheId = id
        cacheGameMode = mode
        startOrRepeatGameFromCache()
    }

    /**
     *
     */
    fun repeatGame() {
        startOrRepeatGameFromCache()
    }

    /**
     *
     */
    fun nextQuestion(): Boolean = runBlocking {
        if (_questions.isNullOrEmpty()) {
            return@runBlocking false
        }

        viewModelScope.launch(backgroundDispatcher) {
            // 1) get the question
            val question = _questions.poll()
            _currentQuestion = question
            // 2) format GameFragment strings {
            prepareGameFragmentStrings(question!!)
            // 3) emit question
            _nextQuestion.postValue(question)
        }.join()
        return@runBlocking true
    }

    /**
     * Recycles the current question in the deque and calls nextQuestion().
     * @see nextQuestion
     */
    fun postponeQuestion() {
        _questions.offer(_currentQuestion)
        nextQuestion()

    }

    /**
     *
     */
    fun endGame(): Boolean {
        val result = (_rightAns.toFloat() / _numberOfQuestions.toFloat()) >= 0.799

        if (result) {
            with(viewModelScope) {
                launch(backgroundDispatcher) {
                    _stringForCongratsFrTV.value =
                        String.format(
                            getApplication<MiningQuizApplication>().resources.getString(R.string.congrats_fr_tv_text),
                            _rightAns,
                            _numberOfQuestions
                        ) // Congratulations, you passed the exam with answered %d from %d questions
                }
                // send attempt to the storage
                launch {
                    createSuccessAttempt()
                }
            }
        } else {
            with(viewModelScope) {
                launch(backgroundDispatcher) {
                    _stringForFailedFrTV.value =
                        String.format(
                            getApplication<MiningQuizApplication>().resources.getString(R.string.failed_fr_tv_text),
                            _rightAns,
                            _numberOfQuestions
                        ) // Sorry, but you didn't pass the exam. You answered only %d from %d questions.
                }
                // send attempt to the storage
                launch {
                    createFailedAttempt()
                }
            }
        }

        return result
    }

    /**
     *
     */
    private fun prepareGameFragmentStrings(question: QuestionUIModel) {
        // 3) format strings
        _stringForGameFrQuestionContent.value =
            String.format(
                getApplication<MiningQuizApplication>().resources.getString(
                    R.string.question_from_content
                ), question.index, _numberOfQuestions, question.content

            ) //Question %d from %d:\n%s

        _stringForGameFrQuestionTopic.value =
            String.format(
                getApplication<MiningQuizApplication>().resources.getString(
                    R.string.topic_content
                ), question.nameTopic
            ) // Topic:\n%s
    }

    /**
     *
     */
    fun rightAnswerGiven() {
        viewModelScope.launch(backgroundDispatcher) {
            ++_rightAns
        }
    }

    fun wrongAnswerGiven() {
        // TODO: 26.04.2022 for mistakes mode
    }

    private fun clearGameInternalCounters() {
        _rightAns = 0
    }

    private suspend fun createFailedAttempt() {
        Timber.d(Thread.currentThread().name)
        Timber.d("createFailedAttempt, cacheGameMode is $cacheGameMode, id is $cacheId")
        viewModelScope.launch {
            when (cacheGameMode) {
                GameMode.EXAM -> {
                    attemptsRepository.insertAttemptExam(
                        AttemptExam(
                            examId = cacheId,
                            success = false
                        )
                    )
                        .collect {
                            _showIsAttemptAddedToast.postEvent(it)
                        }
                }
                GameMode.TOPIC -> {
                    attemptsRepository.insertAttemptTopic(
                        AttemptTopic(
                            topicId = cacheId,
                            success = false
                        )
                    ).collect {
                        _showIsAttemptAddedToast.postEvent(it)
                    }
                }
                else -> {
                    Timber.e(IllegalStateException(ILLEGAL_GAME_STATE))
                    throw IllegalStateException(ILLEGAL_GAME_STATE)
                }
            }
        }

    }

    private suspend fun createSuccessAttempt() {
        Timber.d("createSuccessAttempt, cacheGameMode is $cacheGameMode, id is $cacheId")
        viewModelScope.launch {
            when (cacheGameMode) {
                GameMode.EXAM -> {
                    attemptsRepository.insertAttemptExam(
                        AttemptExam(
                            examId = cacheId,
                            success = true
                        )
                    )
                        .collect {
                            _showIsAttemptAddedToast.postEvent(it)
                        }
                }
                GameMode.TOPIC -> {
                    attemptsRepository.insertAttemptTopic(
                        AttemptTopic(
                            topicId = cacheId,
                            success = true
                        )
                    ).collect {
                        _showIsAttemptAddedToast.postEvent(it)
                    }
                }
                else -> {
                    Timber.e(IllegalStateException(ILLEGAL_GAME_STATE))
                    throw IllegalStateException(ILLEGAL_GAME_STATE)
                }
            }
        }

    }

}