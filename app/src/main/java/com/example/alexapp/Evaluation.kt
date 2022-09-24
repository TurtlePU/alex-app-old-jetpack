package com.example.alexapp

import Participant
import Performance
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.ceil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Evaluation(
  performance: Performance,
  oldRating: Rating? = null,
  commit: (Rating) -> Unit = {}
) {
  var grade by rememberSaveable { mutableStateOf(oldRating?.grade) }
  var comment by rememberSaveable { mutableStateOf(oldRating?.comment) }
  val canCommit = grade != null
  val doCommit: () -> Unit = { commit(Rating(grade!!, comment)) }

  Surface(
    modifier = Modifier.padding(30.dp),
    color = MaterialTheme.colorScheme.background,
    shadowElevation = 10.dp,
  ) {
    Column(
      modifier = Modifier.padding(30.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Text(
        text = performance.repertoire,
        style = MaterialTheme.typography.headlineLarge,
      )
      Text(
        text = performance.participantName,
        style = MaterialTheme.typography.headlineMedium,
      )
      Spacer(modifier = Modifier.padding(20.dp))
      OutlinedTextField(
        value = grade?.toString() ?: "",
        onValueChange = { grade = it.toDoubleOrNull()?.toRatingRange() },
        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
        placeholder = {
          Text(
            text = "Оценка от 0 до 10",
            modifier = Modifier.fillMaxWidth(),
            style = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
          )
        },
        trailingIcon = {
          IconButton(onClick = doCommit, enabled = canCommit) {
            Icon(imageVector = Icons.Filled.StarRate, contentDescription = null)
          }
        },
        keyboardOptions = KeyboardOptions(
          keyboardType = KeyboardType.Decimal,
          imeAction = ImeAction.Next,
        ),
        singleLine = true,
      )
      Spacer(modifier = Modifier.padding(10.dp))
      OutlinedTextField(
        value = comment ?: "",
        onValueChange = { comment = it },
        placeholder = {
          Text(text = "Комментарий (необязательно)")
        },
        keyboardOptions = KeyboardOptions(
          capitalization = KeyboardCapitalization.Sentences,
          imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions {
          defaultKeyboardAction(ImeAction.Done)
          if (canCommit) doCommit()
        }
      )
    }
  }
}

fun Double.toRatingRange(): Double = ceil(coerceIn(0.0, 10.0) * 2) / 2

@Preview
@Composable
fun EvaluationPreview() {
  val whiteStripes = Participant("the White Stripes", "alternative rock", "25")
  val sevenNationArmy = Performance(0, whiteStripes, "Seven Nation Army")
  Surface(modifier = Modifier.fillMaxSize()) {
    Evaluation(performance = sevenNationArmy)
  }
}