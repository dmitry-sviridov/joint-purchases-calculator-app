package ru.dkotsur.calculator.viewmodel

import android.text.Editable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.dkotsur.calculator.data.db.entity.Session
import ru.dkotsur.calculator.data.db.repository.RepositorySession
import java.text.SimpleDateFormat
import java.util.*

class SessionViewModel: ViewModel() {

    private val sessionRepository: RepositorySession = RepositorySession()
    private val allSession: LiveData<List<Session>>

    init {
        allSession = sessionRepository.allSessions
    }

    fun deleteSession(session: Session) {
        sessionRepository.delete(session)
    }

    fun getAllSession(): LiveData<List<Session>> {
        return allSession
    }

    fun saveSession(text: Editable) {
        if (text.isNotEmpty()) {
            val saved = Session(text.toString(), getDate())
            sessionRepository.insert(saved)
        } else {
            Log.e("INSERT", "text is empty")
        }
    }

    fun deleteAllSession() {
        sessionRepository.deleteAll()
    }

    private fun getDate(): String {
        val current = Date()
        val formatter = SimpleDateFormat("dd.MM.yyyy")
        val result = formatter.format(current)
        return result
    }
}