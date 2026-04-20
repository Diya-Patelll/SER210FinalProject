package com.example.ser210finalproject.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ser210finalproject.data.ItemsRepository
import com.example.ser210finalproject.data.Student
import kotlinx.coroutines.flow.firstOrNull

class LoginViewModel(private val itemsRepository: ItemsRepository) : ViewModel() {

    private val quEmailPattern = Regex("^[a-zA-Z]+\\.[a-zA-Z]+@quinnipiac\\.edu$")

    fun isValidQuEmail(email: String): Boolean {
        return quEmailPattern.matches(email.trim())
    }

    fun getDisplayNameFromEmail(email: String): String {
        val namePart = email.trim().substringBefore("@")
        val firstName = namePart.substringBefore(".")
            .lowercase()
            .replaceFirstChar { it.uppercase() }
        val lastName = namePart.substringAfter(".")

        return "$firstName ${lastName.first().uppercaseChar()}."
    }

    suspend fun loginOrRegister(email: String): Student {
        val normalizedEmail = email.trim().lowercase()
        require(isValidQuEmail(normalizedEmail)) {
            "Email must be in first.last@quinnipiac.edu format."
        }

        val existingStudent = itemsRepository.getStudentStream(normalizedEmail).firstOrNull()
        if (existingStudent != null) {
            return existingStudent
        }

        val newStudent = Student(
            userId = normalizedEmail.substringBefore("@"),
            email = normalizedEmail,
            mealpointBalance = 500
        )
        itemsRepository.insertStudent(newStudent)
        return newStudent
    }
}
