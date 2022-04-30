package com.mityushovn.miningquiz.screens.quiz.gamefragment

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.graphics.Color
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import com.mityushovn.miningquiz.DI.Navigators
import com.mityushovn.miningquiz.DI.Repositories
import com.mityushovn.miningquiz.R
import com.mityushovn.miningquiz.activities.quiz.GameEngineFactory
import com.mityushovn.miningquiz.activities.quiz.GameEngine
import com.mityushovn.miningquiz.databinding.FragmentGameBinding
import com.mityushovn.miningquiz.models.Question
import com.mityushovn.miningquiz.navigation.QuizNavigator
import com.mityushovn.miningquiz.utils.disable
import com.mityushovn.miningquiz.utils.toGone
import com.mityushovn.miningquiz.utils.toVisible
import timber.log.Timber
import kotlin.random.Random

private const val ILLEGAL_ITERATOR_STATE = "ListIterator<Question> is empty, smth wrong with logic"
private const val GAME_STARTED = "game_started"

class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private val navigator: QuizNavigator = Navigators.quizNavigator

    private val quizActivityViewModel by activityViewModels<GameEngine> {
        GameEngineFactory(
            Repositories.questionsRepository,
            Repositories.attemptsRepository,
            requireActivity().application
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
            viewModel = quizActivityViewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // if the game is not started
        if (savedInstanceState == null) {
            if (!quizActivityViewModel.nextQuestion()) {
                /*
                    Итератор должен быть с вопросами. Если их нет, значит список пуст, а такой вариант не предусмотрен.
                    В приложении нет ни одного запроса, которой возвращает нулевое количество вопросов.
                    В экзамене или топике всегда есть вопросы, тк логика завязана на паттерн Observer, и лист
                    запроса должен быть непустой.
                 */
                throw IllegalStateException(ILLEGAL_ITERATOR_STATE)
            }
        }


        quizActivityViewModel.nextQuestion.observe(viewLifecycleOwner) { question ->
            // ждем, пока придет вопрос
            // 1) передали его в startNewQuestion для отрисовки и обработали нажатия(ответ)
            startNewQuestion(question)

            // эта кнопка не видна, пока не появится список в Rx цепочке, и пока не будет дан ответ
            binding.gameFrContinueBtn.setOnClickListener {
                // 3) по нажатию на кнопку продолжить проверяем, есть ли следующий вопрос в итераторе,
                // если да - startNewQuestion, если нет endGame().
                if (quizActivityViewModel.nextQuestion()) {
                    with(binding) {
                        initViewsState(gameFrAnswer1, gameFrAnswer2, gameFrAnswer3, gameFrAnswer4)
                    }
                } else {
                    endGame()
                }
            }

            binding.gameFrExitGameBtn.setOnClickListener {
                showQuitDialog()
            }
        } // end of observing
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(GAME_STARTED, true)
    }

    /**
     * private methods with main logic
     */
    private fun checkIfRight(question: Question, text: CharSequence): Boolean {
        return text == question.rightAns
    }

    private fun enableAnotherButtons(question: Question, vararg buttons: Button) {
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

    private fun btnIsClickableFalseCheckIfRightSetBackgroundGreen(question: Question, btn: Button) {
        with(btn) {
            disable() // isClickable = false
            if (checkIfRight(question, text)) setBackgroundColor(Color.GREEN)
        }
    }

    private fun startNewQuestion(question: Question) {
        // logic
        // 1) random position of answers
//        val random = Random(System.currentTimeMillis())
        val array: Array<String> =
            arrayOf( // TODO: 23.04.2022 снести шафл в бэкграунд, скорее всего в Rx цепочку
                question.ans1,
                question.ans2,
                question.ans3,
                question.rightAns
            )
        array.shuffle(Random(System.currentTimeMillis())) // рандомайзер, варианты ответов должны быть на рандомных позициях

        // 1
        with(binding.gameFrAnswer1) {
            text = array[0]
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
            text = array[1]
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
            text = array[2]
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
            text = array[3]
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
        Timber.d("End Game!!!")
        if (quizActivityViewModel.endGame()) {
            navigator.toCongratsFragment(this)
        } else {
            navigator.toFailedFragment(this)
        }
    }

    /**
     * set background button color green if right answer is passed, else red.
     */
    private fun btnRedOrGreen(question: Question, button: Button) {
        if (checkIfRight(question, button.text)) {
            button.setBackgroundColor(Color.GREEN)
            quizActivityViewModel.rightAnswerGiven()
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