package com.example.alexapp

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.alexapp.ui.theme.AlexAppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.absoluteValue
import kotlin.random.Random

data class Credentials(val host: String, val login: String, val token: String)

interface AuthorizationModel {
  val initials: Flow<Credentials?>
  suspend fun remember(credentials: Credentials)
}

typealias OnSuccess = Credentials.() -> Unit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Authorization(
  model: AuthorizationModel,
  authorize: suspend (Credentials) -> String?,
  onSuccess: OnSuccess
) {
  val initials by model.initials.collectAsState(initial = null)
  val scope = rememberCoroutineScope()

  var host by rememberSaveable { mutableStateOf(initials?.host) }
  var login by rememberSaveable { mutableStateOf(initials?.login) }
  var token by rememberSaveable { mutableStateOf(initials?.token) }
  var lastError by remember { mutableStateOf<String?>(null) }

  val current = makeCredentials(host, login, token)

  Surface(
    modifier = Modifier.fillMaxSize(),
    color = MaterialTheme.colorScheme.background,
  ) {
    Column(
      modifier = Modifier
        .padding(PaddingValues(30.dp, 0.dp))
        .fillMaxWidth(),
      verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      OutlinedTextField(
        value = host ?: "https://0.0.0.0:8080",
        onValueChange = { host = it },
        modifier = Modifier.fillMaxWidth(),
      )
      OutlinedTextField(
        value = login ?: "",
        onValueChange = { login = it },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Login") },
      )
      var isHidden by rememberSaveable { mutableStateOf(true) }
      OutlinedTextField(
        value = token ?: "",
        onValueChange = { },
        modifier = Modifier.fillMaxWidth(),
        readOnly = true,
        placeholder = {
          Row {
            Text(text = "Token (tap ")
            Icon(imageVector = Icons.Filled.GeneratingTokens, contentDescription = null)
            Text(text = " to generate)")
          }
        },
        trailingIcon = {
          Row {
            IconButton(
              onClick = { token = login!!.let(::generateToken) },
              enabled = login != null,
            ) {
              val imageVector =
                if (token != null) Icons.Filled.Refresh else Icons.Filled.GeneratingTokens
              Icon(imageVector = imageVector, contentDescription = null)
            }
            if (token != null) {
              IconButton(onClick = { isHidden = !isHidden }) {
                val imageVector =
                  if (isHidden) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
                Icon(imageVector = imageVector, contentDescription = null)
              }
            }
          }
        },
        visualTransformation =
        if (isHidden) PasswordVisualTransformation()
        else VisualTransformation.None,
      )
      Row(
        horizontalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterHorizontally)
      ) {
        Button(
          onClick = {
            host = initials!!.host
            login = initials!!.login
            token = initials!!.token
          },
          modifier = Modifier
            .weight(1f)
            .fillMaxWidth(),
          enabled = initials != null && current != initials,
        ) {
          Icon(imageVector = Icons.Filled.LockReset, contentDescription = null)
        }
        Button(
          onClick = {
            scope.launch {
              val credentials = current!!
              lastError = authorize(credentials)
              if (lastError == null) {
                model.remember(credentials)
                onSuccess(credentials)
              }
            }
          },
          modifier = Modifier
            .weight(1f)
            .fillMaxWidth(),
          enabled = current != null,
        ) {
          Icon(imageVector = Icons.Filled.Login, contentDescription = null)
        }
      }
      lastError?.let { Snackbar { Text(text = it) } }
    }
  }
}

fun generateToken(
  login: String,
  time: Date = Calendar.getInstance().time,
  salt: Int = Random.nextInt()
) = (login.hashCode() xor time.hashCode() xor salt).absoluteValue.toString(32)

fun makeCredentials(host: String?, login: String?, token: String?): Credentials? {
  return Credentials(host ?: return null, login ?: return null, token ?: return null)
}

@Composable
@Preview
fun AuthorizationPreview(onSuccess: OnSuccess = {}) {
  val login = "Android"
  AlexAppTheme {
    Authorization(
      object : AuthorizationModel {
        override val initials = flowOf(Credentials("https://example.com", login, "token"))
        override suspend fun remember(credentials: Credentials) = assert(credentials.login == login)
      },
      { if (it.login != login) "Expected login '$login'" else null },
      onSuccess,
    )
  }
}