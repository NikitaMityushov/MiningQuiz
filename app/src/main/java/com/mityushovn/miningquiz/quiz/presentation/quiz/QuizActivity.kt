package com.mityushovn.miningquiz.quiz.presentation.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import com.mityushovn.miningquiz.common.MiningQuizApplication
import com.mityushovn.miningquiz.R
import javax.inject.Inject

private const val EXAM_OR_TOPIC_ID = "exam_or_topic_id"
private const val GAME_MODE = "game_mode"
private const val GAME_STARTED = "game_started"

class QuizActivity : AppCompatActivity() {

    // essential to init because view model is shared with GameFragment
    @Inject
    lateinit var vmFactory: GameEngineFactory
    private val gameEngine by viewModels<GameEngine> {
        vmFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        // configure DI
        (this.application as MiningQuizApplication).appComponent.injectInQuizActivity(this)

        if (savedInstanceState == null) {
            // start game, you must pass id of topic or exam and game mode in the startGame method of GameEngine
            gameEngine.startGame(
                intent.extras?.get(EXAM_OR_TOPIC_ID) as Int, intent.extras?.get(
                    GAME_MODE
                ) as GameEngine.GameMode
            )
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putBoolean(GAME_STARTED, true)
    }

    override fun onBackPressed() {
        // disable
    }
}