package com.example.unscrambleapp.ui.app

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unscrambleapp.R
import com.example.unscrambleapp.ui.theme.UnscrambleAppTheme
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    gameViewModel: GameViewModel = viewModel()
) {
    val gameUiState by gameViewModel.uiState.collectAsState()
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        GameStatus(
            wordCount = gameUiState.currentWordCount,
            score = gameUiState.score
        )

        GameLayout(
            onUserGuessChanged = { gameViewModel.updateUserGuess(it) },
            userGuess = gameViewModel.userGuess,
            onKeyboardDone = { gameViewModel.checkUserGuess() },
            currentScrambleWord = gameUiState.currentScrambleWord,
            isGuessedWrong = gameUiState.isGuessedWordWrong
        )

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            OutlinedButton(
                onClick = { gameViewModel.skipWord() },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(stringResource(R.string.skip))
            }

            Button(
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(start = 16.dp),
                onClick = { gameViewModel.checkUserGuess() }
            ) {
                Text(stringResource(R.string.submit))
            }
        }
        if (gameUiState.isGameOver) {
            FinalScoreDialog(
                score = gameUiState.score,
                onPlayAgain = { gameViewModel.resetGame() }
            )
        }
    }
}

    @Composable
    fun GameStatus(
        wordCount: Int,
        score: Int,
        modifier: Modifier = Modifier
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
                .size(48.dp)
        ) {
            Text(
                text = stringResource(R.string.word_count, wordCount),
                fontSize = 18.sp
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.End),
                text = stringResource(R.string.score, score),
                fontSize = 18.sp
            )
        }
    }

    @Composable
    fun GameLayout(
        currentScrambleWord: String,
        isGuessedWrong: Boolean,
        userGuess: String,
        onUserGuessChanged: (String) -> Unit,
        onKeyboardDone: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = currentScrambleWord,
                fontSize = 45.sp,
                modifier = modifier.align(Alignment.CenterHorizontally
                )
            )

            OutlinedTextField(
                value = userGuess,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = onUserGuessChanged,
                label = {
                    if (isGuessedWrong) {
                        Text(stringResource(R.string.wrong_guess))
                    } else {
                        Text(stringResource(R.string.enter_your_word))
                    }
                },
                isError = isGuessedWrong,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { onKeyboardDone() }
                )
            )
        }
    }

    @Composable
    fun FinalScoreDialog(
        score: Int,
        onPlayAgain: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        val activity = (LocalContext.current as Activity)

        AlertDialog(
            onDismissRequest = { },
            title = { Text(stringResource(R.string.congratulations)) },
            text = { Text(stringResource(R.string.you_scored, score)) },
            modifier = modifier,
            dismissButton = {
                TextButton(onClick = { activity.finish() }) {
                    Text(stringResource(R.string.exit))
                }
            },

            confirmButton = {
                TextButton(onClick = onPlayAgain) {
                    Text(stringResource(R.string.play_again))
                }
            }
        )
    }

@Preview(showBackground = true)
@Composable
fun GamePreview() {
    UnscrambleAppTheme {
        GameScreen()
    }
}




















