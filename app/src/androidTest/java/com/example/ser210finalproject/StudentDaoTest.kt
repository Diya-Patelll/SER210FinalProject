package com.example.ser210finalproject

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.ser210finalproject.data.AppDatabase
import com.example.ser210finalproject.data.Student
import com.example.ser210finalproject.data.StudentDAO
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StudentDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var studentDao: StudentDAO

    @Before
    fun createDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        studentDao = database.studentDao()
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun insertStudent_thenReadByEmail_returnsStudent() = runBlocking {
        // tests that room can insert a student and read the same student back by email
        val student = Student(
            userId = "diya.patel",
            email = "diya.patel@quinnipiac.edu",
            mealpointBalance = 500
        )

        studentDao.insertStudent(student)
        val savedStudent = studentDao.getStudentByEmail("diya.patel@quinnipiac.edu").first()

        assertNotNull(savedStudent)
        assertEquals("diya.patel", savedStudent?.userId)
        assertEquals(500, savedStudent?.mealpointBalance)
    }
}
