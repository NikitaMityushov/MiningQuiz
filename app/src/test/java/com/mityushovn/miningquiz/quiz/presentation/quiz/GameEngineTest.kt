package com.mityushovn.miningquiz.quiz.presentation.quiz

import com.mityushovn.miningquiz.core_testing.unit.coroutines.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule


@OptIn(ExperimentalCoroutinesApi::class)
class GameEngineTest {


    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
    }
}