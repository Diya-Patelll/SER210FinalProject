package com.example.ser210finalproject

import com.example.ser210finalproject.data.Student
import org.junit.Assert.assertEquals
import org.junit.Test

class StudentTest {

    @Test
    fun student_storesExpectedValues() {
        // verifies that the student entity stores the values passed
        val student = Student(
            userId = "diya.patel",
            email = "diya.patel@quinnipiac.edu",
            mealpointBalance = 500
        )

        assertEquals("diya.patel", student.userId)
        assertEquals("diya.patel@quinnipiac.edu", student.email)
        assertEquals(500, student.mealpointBalance)
    }
}
