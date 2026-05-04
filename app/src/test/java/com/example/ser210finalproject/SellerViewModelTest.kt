package com.example.ser210finalproject

import com.example.ser210finalproject.data.Student
import com.example.ser210finalproject.viewmodel.SellerViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SellerViewModelTest {

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun postListing_rejectsWhenMealPointsAreInsufficient() = runTest {
        // tests that a seller cannot post more MealPoints than they currently have available
        val repository = FakeItemsRepository()
        repository.studentsByEmail.value = mutableMapOf(
            "diya.patel@quinnipiac.edu" to Student(
                userId = "diya.patel",
                email = "diya.patel@quinnipiac.edu",
                mealpointBalance = 50
            )
        )
        val viewModel = SellerViewModel(repository)
        var error: String? = null

        viewModel.postListing(
            sellerEmail = "diya.patel@quinnipiac.edu",
            points = "100",
            price = "20",
            onSuccess = {},
            onError = { error = it }
        )
        advanceUntilIdle()

        assertEquals("Insufficient MealPoints.", error)
        assertTrue(repository.listings.value.isEmpty())
    }

    @Test
    fun postListing_insertsListingWhenInputIsValid() = runTest {
        // tests that a valid seller listing is inserted and success callback
        val repository = FakeItemsRepository()
        repository.studentsByEmail.value = mutableMapOf(
            "diya.patel@quinnipiac.edu" to Student(
                userId = "diya.patel",
                email = "diya.patel@quinnipiac.edu",
                mealpointBalance = 500
            )
        )
        val viewModel = SellerViewModel(repository)
        var successCalled = false

        viewModel.postListing(
            sellerEmail = "diya.patel@quinnipiac.edu",
            points = "100",
            price = "40",
            onSuccess = { successCalled = true },
            onError = {}
        )
        advanceUntilIdle()

        assertTrue(successCalled)
        assertEquals(1, repository.listings.value.size)
        assertEquals("diya.patel", repository.listings.value.first().sellerID)
    }
}
