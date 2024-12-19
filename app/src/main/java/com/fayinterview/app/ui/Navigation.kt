package com.fayinterview.app.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fayinterview.app.ui.appointments.AppointmentsOverviewMain
import com.fayinterview.app.ui.appointments.AppointmentsOverviewScreen
import com.fayinterview.app.ui.welcome.WelcomeMain
import com.fayinterview.app.ui.welcome.WelcomeScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = WelcomeScreen
    ) {
        composable<WelcomeScreen> {
            WelcomeMain(
                navController = navController
            )
        }
        composable<AppointmentsOverviewScreen> {
            AppointmentsOverviewMain()
        }
    }
}
