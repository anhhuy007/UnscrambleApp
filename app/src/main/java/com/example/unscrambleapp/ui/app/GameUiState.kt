package com.example.unscrambleapp.ui.app

data class GameUiState(
    val currentScrambleWord: String = "",
    val currentWordCount: Int = 0,
    val score: Int = 0,
    val isGuessedWordWrong: Boolean = false,
    val isGameOver: Boolean = false
)