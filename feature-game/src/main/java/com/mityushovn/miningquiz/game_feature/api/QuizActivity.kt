package com.mityushovn.miningquiz.game_feature.api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.activity.viewModels
import com.mityushovn.miningquiz.game_feature.R
import com.mityushovn.miningquiz.game_feature.internal.di.components.DaggerGameComponent
import com.mityushovn.miningquiz.game_feature.internal.di.components.GameComponent
import com.mityushovn.miningquiz.game_feature.internal.presentation.GameEngine
import com.mityushovn.miningquiz.game_feature.internal.presentation.GameEngineFactory
import com.mityushovn.miningquiz.module_injector.extensions.findDependencies
import com.mityushovn.miningquiz.utils.collectEventOnLifecycle
import javax.inject.Inject

private const val EXAM_OR_TOPIC_ID = "exam_or_topic_id"
private const val GAME_MODE = "game_mode"
private const val GAME_STARTED = "game_started"

class QuizActivity : AppCompatActivity() {

    private var _component: GameComponent? = null
    internal val component: GameComponent
        get() = _component!!

    // essential to init because view model is shared with GameFragment
    @Inject
    internal lateinit var vmFactory: GameEngineFactory
    private val gameEngine by viewModels<GameEngine> {
        vmFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _component = DaggerGameComponent.factory().create(findDependencies(), application)
        _component?.inject(this)
        setContentView(R.layout.activity_quiz)
        // configure DI

        if (savedInstanceState == null) {
            // start game, you must pass id of topic or exam and game mode in the startGame method of GameEngine
            gameEngine.startGame(
                intent.extras?.get(EXAM_OR_TOPIC_ID) as Int, intent.extras?.get(
                    GAME_MODE
                ) as GameMode
            )
        }

        gameEngine.showIsAttemptAddedToast.collectEventOnLifecycle(this) {
            showIsAttemptAddedToast(it)
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putBoolean(GAME_STARTED, true)
    }

    override fun onBackPressed() {
        // disable
    }

    /**
     * Creates Toast depends on boolean argument of successfully added attempt.
     */
    private fun showIsAttemptAddedToast(it: Boolean) {
        if (it) {
            Toast.makeText(
                this,
                application.resources.getString(R.string.attempt_succ_added),
                Toast.LENGTH_SHORT
            ).show() // Attempt successfully added
        } else {
            Toast.makeText(
                this,
                application.resources.getString(R.string.attempt_failed_saved),
                Toast.LENGTH_SHORT
            ).show() // Attempt would not be saved
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _component = null
    }
}