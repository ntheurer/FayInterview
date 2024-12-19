package com.fayinterview.app.ui.welcome

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.fayinterview.app.ui.common.FayLogo
import com.fayinterview.app.R
import com.fayinterview.app.ui.appointments.AppointmentsOverviewScreen
import com.fayinterview.app.ui.common.FayButton
import com.fayinterview.app.ui.webview.WebViewScreen
import kotlinx.serialization.Serializable

@Serializable
object WelcomeScreen

@Composable
fun WelcomeMain(
    navController: NavController,
    viewModel: WelcomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.navForward) {
        if (uiState.navForward) {
            navController.navigate(
                AppointmentsOverviewScreen,
                navOptions = NavOptions.Builder().setPopUpTo(WelcomeScreen, true).build()
            )
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 48.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.welcome_title),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                FayLogo(
                    modifier = Modifier
                        .padding(start = 4.dp)
                )
            }
            SignInSection(
                onSignInClick = viewModel::signIn,
                onSignUpClick = {
                    navController.navigate(
                        WebViewScreen(
                            url = "https://www.faynutrition.com/log-in"
                        )
                    )
                },
                isLoading = uiState.isLoading,
                errorMessage = uiState.errorMessage,
                modifier = Modifier
                    .padding(top = 48.dp)
            )
        }
        if (uiState.isLoading) {
            CircularProgressIndicator()
        }
    }
}

@Composable
private fun SignInSection(
    onSignInClick: (String, String) -> Unit,
    onSignUpClick: () -> Unit,
    isLoading: Boolean,
    @StringRes errorMessage: Int?,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    var username by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    var showPassword by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        TextField(
            value = username,
            onValueChange = {
                username = it
            },
            singleLine = true,
            label = {
                Text(
                    text = stringResource(R.string.username_prompt),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            keyboardOptions = KeyboardOptions(
                autoCorrectEnabled = false,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .fillMaxWidth()
        )
        TextField( // Note: BasicSecureTextField could be used but then a label couldn't be used
            value = password,
            onValueChange = {
                password = it
            },
            singleLine = true,
            label = {
                Text(
                    text = stringResource(R.string.password_prompt),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            keyboardOptions = KeyboardOptions(
                autoCorrectEnabled = false,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    // Dismiss keyboard
                    focusManager.clearFocus()
                }
            ),
            visualTransformation = if (showPassword) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                IconButton(
                    onClick = { showPassword = !showPassword }
                ) {
                    Icon(
                        if (showPassword) {
                            Icons.Default.Visibility
                        } else {
                            Icons.Default.VisibilityOff
                        },
                        contentDescription = stringResource(R.string.password_visibility),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            },
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
        )
        errorMessage?.let {
            Text(
                text = stringResource(errorMessage),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )
        }
        FayButton(
            textRes = R.string.sign_in,
            isEnabled = !isLoading && username.isNotBlank() && password.isNotBlank(),
            onClick = {
                focusManager.clearFocus()
                if (username.isNotBlank() && password.isNotBlank()) {
                    onSignInClick(username, password)
                }
            }
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.dont_have_account),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = stringResource(R.string.sign_up),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clickable(onClick = onSignUpClick)
                    .padding(4.dp)
            )
        }
    }
}
