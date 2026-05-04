package com.example.ser210finalproject

import com.example.ser210finalproject.ui.viewmodel.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun isValidQuEmail_acceptsCorrectFormat() {
        // tests that only first.last@quinnipiac.edu emails are accepted by login validation
        val viewModel = LoginViewModel(FakeItemsRepository())

        assertTrue(viewModel.isValidQuEmail("diya.patel@quinnipiac.edu"))
        assertFalse(viewModel.isValidQuEmail("diyapatel@quinnipiac.edu"))
        assertFalse(viewModel.isValidQuEmail("diya.patel@gmail.com"))
    }

    @Test
    fun loginOrRegister_createsStudentWhenMissing() = runTest {
        // tests that login creates and stores a new student when the email is not in the repository
        val repository = FakeItemsRepository()
        val viewModel = LoginViewModel(repository)

        val student = viewModel.loginOrRegister("diya.patel@quinnipiac.edu")

        assertEquals("diya.patel", student.userId)
        assertEquals("diya.patel@quinnipiac.edu", student.email)
        assertEquals(student, repository.studentsByEmail.value["diya.patel@quinnipiac.edu"])
    }
}
