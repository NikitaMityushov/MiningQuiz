package com.mityushovn.miningquiz.feature_game.internal.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mityushovn.miningquiz.core_domain.domain.models.AttemptExam
import com.mityushovn.miningquiz.core_domain.domain.models.AttemptTopic
import com.mityushovn.miningquiz.core_domain.domain.repositories.AttemptsRepositoryAPI
import com.mityushovn.miningquiz.core_domain.domain.repositories.QuestionsRepositoryAPI
import com.mityushovn.miningquiz.core_domain.domain.repositories.SettingsRepositoryAPI
import com.mityushovn.miningquiz.feature_game.R
import com.mityushovn.miningquiz.feature_game.api.GameMode
import com.mityushovn.miningquiz.feature_game.internal.domain.models.QuestionUIModel
import com.mityushovn.miningquiz.feature_game.internal.domain.models.toQuestionUIModel
import com.mityushovn.miningquiz.core_utils.Event
import com.mityushovn.miningquiz.core_utils.LiveEvent
import com.mityushovn.miningquiz.core_utils.postEvent
import com.mityushovn.miningquiz.core_utils.share
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
import kotlin.math.roundToInt

/**
 * @author Nikita Mityushov 25.04.22
 * @since 1.0
 *
 * Class represents a quiz game.
 * To start a game you need to pass id of the exam or topic, and game mode to the startGame(id, mode) method.
 * @see GameMode
 * @see startGame
 */
internal class GameEngine(
    private val questionsRepository: QuestionsRepositoryAPI,
    private val attemptsRepository: AttemptsRepositoryAPI,
    private val settingsRepository: SettingsRepositoryAPI,
    private val backgroundDispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val gameMode: GameMode,
    private val examOrTopicId: Int,
    app: Application
) : AndroidViewModel(app) {

    companion object {
        private const val DEFAULT_QUESTIONS_NUMBER = 10
        private const val DEFAULT_PERCENT_OF_RIGHT_ANSWERS = 0.799f
        private const val ILLEGAL_GAME_STATE = "Game mode must be chosen! Something with logic."
    }

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
    private var _numberOfQuestions: Int = DEFAULT_QUESTIONS_NUMBER
        set(value) {
            synchronized(this) {
                field = value
            }
        }

    /**
     * @property _percentOfRightAnswers represents percent of right answers you should done.
     */
    private var _percentOfRightAnswers: Float = DEFAULT_PERCENT_OF_RIGHT_ANSWERS

    /**
     * @property loading is an observable property of loading questions from database.
     * Since loading of questions happens before onViewCreated, initial value of [_loading] is true.
     */
    private val _loading = MutableStateFlow(true)
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

    init {
        initQuestionsNumber()
        initPercentOfRightAnswers()
    }

    /**
     * Initialize the [_numberOfQuestions] value
     */
    private fun initQuestionsNumber() {
        viewModelScope.launch(backgroundDispatcher) {
            _numberOfQuestions = try {
                when (gameMode) {
                    GameMode.EXAM -> {
                        settingsRepository.gameSettings.numberOfExamsQuestions.toInt()
                    }
                    else -> {
                        DEFAULT_QUESTIONS_NUMBER
                    }
                }
            } catch (e: Exception) {
                Timber.e("Init _numberOfQuestions exception", e)
                DEFAULT_QUESTIONS_NUMBER
            }
        }
    }

    private fun initPercentOfRightAnswers() {
        viewModelScope.launch(backgroundDispatcher) {
            _percentOfRightAnswers = try {
                when (gameMode) {
                    GameMode.EXAM -> {
                        settingsRepository.gameSettings.percentOfRightAnswers / 100
                    }
                    else -> {
                        DEFAULT_PERCENT_OF_RIGHT_ANSWERS
                    }
                }
            } catch (e: Exception) {
                Timber.e("Init _numberOfQuestions exception", e)
                DEFAULT_PERCENT_OF_RIGHT_ANSWERS
            }
        }
    }

    /**
     *
     */
    private fun sendSearchRequestExam() {
        viewModelScope.launch(backgroundDispatcher) {
            questionsRepository
                .getRandomQuestionsFromExamIdAndNumberOfQuestions(examOrTopicId, _numberOfQuestions)
                .onEach {
                    val numberOfQuestions = it.size
                    val percentOfRightAnswers =
                        when (val percent = (it.size * _percentOfRightAnswers).roundToInt()) {
                            0 -> 1
                            else -> {
                                percent
                            }
                        }
                    clearGameInternalCounters() // clear all counters, set 0 to start new game
                    _stringForPreviewGameTextView.value =
                        String.format(
                            getApplication<Application>().resources.getString(
                                R.string.question_contains_n_questions
                            ), numberOfQuestions, percentOfRightAnswers
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
    private fun sendSearchRequestTopic() {
        viewModelScope.launch(backgroundDispatcher) {
            questionsRepository
                .getAllQuestionsFromTopic(examOrTopicId)
                .onEach {
                    clearGameInternalCounters() // clear all counters, set 0 to start new game
                    _numberOfQuestions = it.size
                    _stringForPreviewGameTextView.value =
                        String.format(
                            getApplication<Application>().resources.getString(
                                R.string.question_contains_n_questions
                            ), it.size, it.size * _percentOfRightAnswers
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
        when (gameMode) {
            GameMode.EXAM -> sendSearchRequestExam()
            GameMode.TOPIC -> sendSearchRequestTopic()
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
    fun startGame() {
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
        val result = (_rightAns.toFloat() / _numberOfQuestions.toFloat()) >= _percentOfRightAnswers

        if (result) {
            with(viewModelScope) {
                launch(backgroundDispatcher) {
                    _stringForCongratsFrTV.value =
                        String.format(
                            getApplication<Application>().resources.getString(R.string.congrats_fr_tv_text),
                            _rightAns,
                            _numberOfQuestions
                        ) // Congratulations, you passed the exam with answered %d from %d questions
                }
                // send attempt to the storage
                launch(backgroundDispatcher) {
                    createSuccessAttempt()
                }
            }
        } else {
            with(viewModelScope) {
                launch(backgroundDispatcher) {
                    _stringForFailedFrTV.value =
                        String.format(
                            getApplication<Application>().resources.getString(R.string.failed_fr_tv_text),
                            _rightAns,
                            _numberOfQuestions
                        ) // Sorry, but you didn't pass the exam. You answered only %d from %d questions.
                }
                // send attempt to the storage
                launch(backgroundDispatcher) {
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
                getApplication<Application>().resources.getString(
                    R.string.question_from_content
                ), question.index, _numberOfQuestions, question.content

            ) //Question %d from %d:\n%s

        _stringForGameFrQuestionTopic.value =
            String.format(
                getApplication<Application>().resources.getString(
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
        when (gameMode) {
            GameMode.EXAM -> {
                attemptsRepository.insertAttemptExam(
                    AttemptExam(
                        examId = examOrTopicId,
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
                        topicId = examOrTopicId,
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

    private suspend fun createSuccessAttempt() {
        when (gameMode) {
            GameMode.EXAM -> {
                attemptsRepository.insertAttemptExam(
                    AttemptExam(
                        examId = examOrTopicId,
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
                        topicId = examOrTopicId,
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