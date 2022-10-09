package com.mityushovn.miningquiz.quiz.presentation.gamefragment

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.mityushovn.miningquiz.common.MiningQuizApplication
import com.mityushovn.miningquiz.R
import com.mityushovn.miningquiz.quiz.presentation.quiz.GameEngine
import com.mityushovn.miningquiz.quiz.presentation.quiz.GameEngineFactory
import com.mityushovn.miningquiz.databinding.FragmentGameBinding
import com.mityushovn.miningquiz.common.navigation.QuizNavigator
import com.mityushovn.miningquiz.quiz.domain.models.QuestionUIModel
import com.mityushovn.miningquiz.utils.collectStateFlowOnLifecycle
import com.mityushovn.miningquiz.utils.disable
import com.mityushovn.miningquiz.utils.toGone
import com.mityushovn.miningquiz.utils.toVisible
import timber.log.Timber
import javax.inject.Inject

private const val ILLEGAL_ITERATOR_STATE = "ListIterator<Question> is empty, smth wrong with logic"
private const val GAME_STARTED = "game_started"

/**
 * Represents screen of game.
 */
class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding

    @Inject
    lateinit var vmFactory: GameEngineFactory
    private val gameEngine by activityViewModels<GameEngine> {
        vmFactory
    }

    @Inject
    lateinit var navigator: QuizNavigator

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MiningQuizApplication).appComponent.injectInGameFragment(
            this
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        // init data binding
        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = gameEngine
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // if the game is not started

        gameEngine.loading.collectStateFlowOnLifecycle(viewLifecycleOwner) { loadingStatus ->
            if (loadingStatus) {
                Timber.d("Loading the questions")
            } else {
                Timber.d("Ready to start")
                if (savedInstanceState == null) {

                    if (!gameEngine.nextQuestion()) {
                        /*
                            Итератор должен быть с вопросами. Если их нет, значит список пуст, а такой вариант не предусмотрен.
                            В приложении нет ни одного запроса, которой возвращает нулевое количество вопросов.
                            В экзамене или топике всегда есть вопросы, тк логика завязана на паттерн Observer, и лист
                            запроса должен быть непустой.
                         */
                        throw IllegalStateException(ILLEGAL_ITERATOR_STATE) // todo вылетает тк не успевает пройти sendSearchRequestExam или sendSearchRequestTopic до проверки
                    }
                }

                gameEngine.nextQuestion.observe(viewLifecycleOwner) { question ->
                    // ждем, пока придет вопрос
                    // 1) передали его в startNewQuestion для отрисовки и обработали нажатия(ответ)
                    startNewQuestion(question)

                    // эта кнопка не видна, пока не появится список в Rx цепочке, и пока не будет дан ответ
                    binding.gameFrContinueBtn.setOnClickListener {
                        // 3) по нажатию на кнопку продолжить проверяем, есть ли следующий вопрос в итераторе,
                        // если да - startNewQuestion, если нет endGame().
                        if (gameEngine.nextQuestion()) {
                            with(binding) {
                                initViewsState(
                                    gameFrAnswer1,
                                    gameFrAnswer2,
                                    gameFrAnswer3,
                                    gameFrAnswer4
                                )
                            }
                        } else {
                            endGame()
                        }
                    }

                    binding.gameFrExitGameBtn.setOnClickListener {
                        showQuitDialog()
                    }

                    binding.gameFrPostponeBtn.setOnClickListener {
                        gameEngine.postponeQuestion()
                    }
                } // end of observing
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(GAME_STARTED, true)
    }

    /**
     * private methods with main logic
     */
    private fun checkIfRight(question: QuestionUIModel, text: CharSequence): Boolean {
        return text == question.rightAns
    }

    private fun enableAnotherButtons(question: QuestionUIModel, vararg buttons: Button) {
        buttons.forEach {
            btnIsClickableFalseCheckIfRightSetBackgroundGreen(question, it)
        }
        with(binding) {
            gameFrPostponeBtn.toGone()
            gameFrContinueBtn.toVisible()
        }

    }

    private fun initViewsState(vararg buttons: Button) {
        buttons.forEach {
            it.isClickable = true
            it.setBackgroundColor(resources.getColor(R.color.primaryC))
        }
        binding.gameFrPostponeBtn.toVisible()
        binding.gameFrContinueBtn.toGone()
    }

    private fun btnIsClickableFalseCheckIfRightSetBackgroundGreen(
        question: QuestionUIModel,
        btn: Button
    ) {
        with(btn) {
            disable() // isClickable = false
            if (checkIfRight(question, text)) setBackgroundColor(Color.GREEN)
        }
    }

    private fun startNewQuestion(question: QuestionUIModel) {

        // 1
        with(binding.gameFrAnswer1) {
            text = question.ans1
            setOnClickListener {
                btnRedOrGreen(question, this)
                with(binding) {
                    enableAnotherButtons(
                        question,
                        gameFrAnswer1,
                        gameFrAnswer2,
                        gameFrAnswer3,
                        gameFrAnswer4
                    )
                }
            }
        }
        // 2
        with(binding.gameFrAnswer2) {
            text = question.ans2
            setOnClickListener {
                btnRedOrGreen(question, this)
                with(binding) {
                    enableAnotherButtons(
                        question,
                        gameFrAnswer1,
                        gameFrAnswer2,
                        gameFrAnswer3,
                        gameFrAnswer4
                    )
                }
            }
        }
        // 3
        with(binding.gameFrAnswer3) {
            text = question.ans3
            setOnClickListener {
                btnRedOrGreen(question, this)
                with(binding) {
                    enableAnotherButtons(
                        question,
                        gameFrAnswer1,
                        gameFrAnswer2,
                        gameFrAnswer3,
                        gameFrAnswer4
                    )
                }
            }
        }

        // 4
        with(binding.gameFrAnswer4) {
            text = question.ans4
            setOnClickListener {
                btnRedOrGreen(question, this)
                with(binding) {
                    enableAnotherButtons(
                        question,
                        gameFrAnswer1,
                        gameFrAnswer2,
                        gameFrAnswer3,
                        gameFrAnswer4
                    )
                }
            }
        }
    }

    private fun endGame() {
        if (gameEngine.endGame()) {
            navigator.toCongratsFragment(this)
        } else {
            navigator.toFailedFragment(this)
        }
    }

    /**
     * set background button color green if right answer is passed, else red.
     */
    private fun btnRedOrGreen(question: QuestionUIModel, button: Button) {
        if (checkIfRight(question, button.text)) {
            button.setBackgroundColor(Color.GREEN)
            gameEngine.rightAnswerGiven()
        } else {
            button.setBackgroundColor(Color.RED)
        }
    }

    /**
     * Shows the alert dialog if "Quit test" button was pressed.
     * @see AlertDialog
     */
    private fun showQuitDialog() {
        val listener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> navigator.quitTestFromGameFr(this)
                DialogInterface.BUTTON_NEGATIVE -> Timber.d("Cancel quit dialog")
            }
        }

        val dialog = AlertDialog.Builder(requireContext())
            .setCancelable(true)
            .setIcon(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_baseline_sd_card_alert_24,
                    null
                )
            )
            .setTitle(getString(R.string.are_you_sure_want_to_quit))
            .setMessage(getString(R.string.are_you_sure_alert_content))
            .setPositiveButton(getString(R.string.yes), listener)
            .setNegativeButton(getString(R.string.no), listener)
            .create()

        dialog.show()
    }
}