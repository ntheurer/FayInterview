package com.fayinterview.app.ui.welcome

import app.cash.turbine.test
import com.fayinterview.app.data.FayRepository
import com.fayinterview.app.R
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class WelcomeViewModelTest {
    private val fayRepository = mockk<FayRepository>()

    private lateinit var viewModel: WelcomeViewModel

    @Before
    fun setup() {
        viewModel = WelcomeViewModel(fayRepository)
    }

    @Test
    fun testSignInShouldSetInitialLoadingValues() = runTest {
        // GIVEN
        val expected = WelcomeUiState(
            isLoading = true,
            navForward = false,
            errorMessage = null
        )
        signInReturnsSuccess()

        viewModel.uiState.test {
            awaitItem() // initialization values can be ignored
            // WHEN
            viewModel.signIn("username", "password")

            // THEN
            val actual = awaitItem()
            assert(actual.isLoading == expected.isLoading)
            assert(actual.navForward == expected.navForward)
            assert(actual.errorMessage == expected.errorMessage)
        }
    }

    @Test
    fun testSuccessfulSignIn() = runTest {
        // GIVEN
        val expected = WelcomeUiState(
            isLoading = true,
            navForward = true,
            errorMessage = null
        )
        signInReturnsSuccess()

        viewModel.uiState.test {
            awaitItem() // initialization values can be ignored
            // WHEN
            viewModel.signIn("username", "password")
            awaitItem() // setting loading values can be ignored

            // THEN
            val actual = awaitItem()
            assert(actual.isLoading == expected.isLoading)
            assert(actual.navForward == expected.navForward)
            assert(actual.errorMessage == expected.errorMessage)
        }
    }

    @Test
    fun testSignInTimeout() = runTest {
        // GIVEN
        val expected = WelcomeUiState(
            isLoading = false,
            navForward = false,
            errorMessage = R.string.timeout_error_message
        )
        signInReturnsFailure(errorCode = 408)

        viewModel.uiState.test {
            awaitItem() // initialization values can be ignored
            // WHEN
            viewModel.signIn("username", "password")
            awaitItem() // setting loading values can be ignored

            // THEN
            val actual = awaitItem()
            assert(actual.isLoading == expected.isLoading)
            assert(actual.navForward == expected.navForward)
            assert(actual.errorMessage == expected.errorMessage)
        }
    }

    @Test
    fun testSignInWrongUsernameOrPassword() = runTest {
        // GIVEN
        val expected = WelcomeUiState(
            isLoading = false,
            navForward = false,
            errorMessage = R.string.unauthorized_error_message
        )
        signInReturnsFailure(errorCode = 401)

        viewModel.uiState.test {
            awaitItem() // initialization values can be ignored
            // WHEN
            viewModel.signIn("username", "password")
            awaitItem() // setting loading values can be ignored

            // THEN
            val actual = awaitItem()
            assert(actual.isLoading == expected.isLoading)
            assert(actual.navForward == expected.navForward)
            assert(actual.errorMessage == expected.errorMessage)
        }
    }

    private fun signInReturnsSuccess() {
        coEvery {
            fayRepository.signIn(any(), any())
        } returns null
    }

    private fun signInReturnsFailure(errorCode: Int) {
        coEvery {
            fayRepository.signIn(any(), any())
        } returns errorCode
    }
}
