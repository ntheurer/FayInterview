package com.fayinterview.app.ui.appointments

import app.cash.turbine.test
import com.fayinterview.app.data.FayRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AppointmentsOverviewViewModelTest {
    private val fayRepository = mockk<FayRepository>()

    private val upcomingAppointmentInfo = mockk<AppointmentInfo>()
    private val pastAppointmentInfo = mockk<AppointmentInfo>()

    private lateinit var viewModel: AppointmentsOverviewViewModel

    @Before
    fun setup() {
        coEvery {
            fayRepository.fetchAppointments()
        } returns listOf(upcomingAppointmentInfo, pastAppointmentInfo)
        every { upcomingAppointmentInfo.isPast } returns false
        every { pastAppointmentInfo.isPast } returns true

        viewModel = AppointmentsOverviewViewModel(fayRepository)
    }

    @Test
    fun testSortingAppointments() = runTest {
        viewModel.uiState.test {
            awaitItem() // initialization values can be ignored
            // WHEN
            viewModel.refresh()
            awaitItem() // setting loading values can be ignored

            // THEN
            val actual = awaitItem()
            assert(!actual.isLoading)
            assert(actual.pastAppointments.size == 1)
            assert(actual.pastAppointments[0] == pastAppointmentInfo)
            assert(actual.upcomingAppointments.size == 1)
            assert(actual.upcomingAppointments[0] == upcomingAppointmentInfo)
        }
    }
}
