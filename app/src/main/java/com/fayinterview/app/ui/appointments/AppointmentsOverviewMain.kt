package com.fayinterview.app.ui.appointments

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.fayinterview.app.R
import com.fayinterview.app.ui.common.FayButton
import com.fayinterview.app.ui.common.FayLogo
import com.fayinterview.app.ui.welcome.WelcomeScreen
import com.fayinterview.app.util.DatePattern
import com.fayinterview.app.util.humanReadable
import kotlinx.serialization.Serializable
import java.util.Date
import java.util.TimeZone

val defaultHorizontalPadding = 24.dp

@Serializable
object AppointmentsOverviewScreen

@Composable
fun AppointmentsOverviewMain(
    navController: NavController,
    viewModel: AppointmentsOverviewViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.logOut) {
        if (uiState.logOut) {
            navController.navigate(
                WelcomeScreen,
                navOptions = NavOptions.Builder().setPopUpTo(WelcomeScreen, true).build()
            )
        }
    }

    val tabs = listOf(
        stringResource(R.string.upcoming),
        stringResource(R.string.past)
    )
    var selectedTabIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            ) {
                FayLogo(
                    size = 30.dp
                )
                Text(
                    text = stringResource(R.string.fay),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .padding(start = 8.dp)
                )
            }
            HorizontalDivider(modifier = Modifier.padding(top = 8.dp))
            Text(
                text = stringResource(R.string.appointments_overview_title),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Medium
                ),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(defaultHorizontalPadding)
            )
            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex,
                edgePadding = 0.dp,
                divider = {
                    // No-op
                    // A custom divider spanning the whole screen width is done below
                },
                modifier = Modifier
                    .padding(start = defaultHorizontalPadding)
            ) {
                tabs.forEachIndexed { index, title ->
                    AppointmentsTab(
                        title = title,
                        index = index,
                        selectedIndex = selectedTabIndex,
                        onClick = {
                            selectedTabIndex = index
                        }
                    )
                }
            }
            HorizontalDivider()
            AppointmentsList(
                appointments = if (selectedTabIndex == 0) {
                    uiState.upcomingAppointments
                } else {
                    uiState.pastAppointments
                },
                placeholderText = if (selectedTabIndex == 0) {
                    R.string.no_upcoming_appointments_found
                } else {
                    R.string.no_past_appointments_found
                },
                onRefresh = viewModel::refresh,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        }
        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
private fun AppointmentsTab(
    title: String,
    index: Int,
    selectedIndex: Int,
    onClick: () -> Unit
) {
    Tab(
        text = {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = if (index == selectedIndex) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onBackground
                }
            )
        },
        selected = index == selectedIndex,
        onClick = onClick
    )
}

@Composable
private fun AppointmentsList(
    appointments: List<AppointmentInfo>,
    @StringRes placeholderText: Int,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        state = rememberLazyListState(),
        modifier = modifier
    ) {
        if (appointments.isEmpty()) {
            item {
                Text(
                    text = stringResource(placeholderText),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                )
            }
            item {
                FayButton(
                    textRes = R.string.refresh,
                    isEnabled = true,
                    onClick = onRefresh,
                    modifier = Modifier
                        .padding(horizontal = defaultHorizontalPadding)
                )
            }
        }
        items(
            appointments,
            key = { it.appointment.appointmentId }
        ) { info ->
            var isExpanded by remember {
                mutableStateOf(false)
            }
            Column(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .padding(horizontal = defaultHorizontalPadding)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.surfaceDim,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clip(RoundedCornerShape(16.dp))
                    .clickable {
                        isExpanded = !isExpanded
                    }
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    CalendarBox(info.appointment.start)
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = defaultHorizontalPadding)
                    ) {
                        Text(
                            text = stringResource(
                                R.string.appointment_info_title,
                                info.appointment.appointmentType,
                                info.providerName
                            ),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = stringResource(
                                R.string.appointment_time,
                                info.appointment.start.humanReadable(DatePattern.TIME),
                                info.appointment.end.humanReadable(DatePattern.TIME),
                                TimeZone.getDefault().getDisplayName(true, TimeZone.SHORT)
                            ),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Note: Ideally use Icon() or Image() with a drawable res instead of
                            // Box with circle border for the repeat icon
                            Box(
                                modifier = Modifier
                                    .size(14.dp)
                                    .border(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        shape = CircleShape
                                    )
                            )
                            Text(
                                text = info.appointment.recurrenceType,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier
                                    .padding(4.dp)
                            )
                        }
                    }
                }
                if (isExpanded) {
                    FayButton(
                        textRes = R.string.join_meeting,
                        isEnabled = true,
                        onClick = {
                            // Out of scope for this project
                        },
                        leadingIcon = {
                            // Note: Ideally this would be an Icon() or Image() with a drawable res
                            Box(
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .size(15.dp)
                                    .border(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        shape = RoundedCornerShape(4.dp)
                                    )
                            )
                            Box(
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .size(5.dp)
                                    .border(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun CalendarBox(
    date: Date
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceDim)
    ) {
        Text(
            text = date.humanReadable(DatePattern.MONTH).uppercase(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(
                        topStart = 8.dp,
                        topEnd = 8.dp
                    )
                )
                .padding(horizontal = 8.dp)
        )
        Text(
            text = date.humanReadable(DatePattern.DAY),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
