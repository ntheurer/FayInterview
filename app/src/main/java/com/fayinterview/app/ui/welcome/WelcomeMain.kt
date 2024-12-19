package com.fayinterview.app.ui.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fayinterview.app.ui.common.FayLogo
import com.fayinterview.app.R
import com.fayinterview.app.ui.appointments.AppointmentsOverviewScreen
import com.fayinterview.app.ui.common.FayButton
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
            navController.navigate(AppointmentsOverviewScreen)
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
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
                isLoading = uiState.isLoading,
                modifier = Modifier
                    .padding(top = 32.dp)
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
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    var username by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
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
            modifier = Modifier
                .fillMaxWidth()
        )
        TextField( // TODO: Nice to have - BasicSecureTextField instead
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
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
        )
        FayButton(
            textRes = R.string.sign_in,
            isEnabled = !isLoading && username.isNotBlank() && password.isNotBlank(),
            onClick = {
                if (username.isNotBlank() && password.isNotBlank()) {
                    onSignInClick(username, password)
                }
            }
        )
    }
}
