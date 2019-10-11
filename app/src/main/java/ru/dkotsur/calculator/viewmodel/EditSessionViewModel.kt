package ru.dkotsur.calculator.viewmodel

import android.text.Editable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.dkotsur.calculator.data.db.entity.Person
import ru.dkotsur.calculator.data.db.entity.Session
import ru.dkotsur.calculator.data.db.repository.RepositorySelectedSession

class EditSessionViewModel(sessionId: Long) : ViewModel() {

    private var mSessionId: Long = 0
    private val repositorySelectedSession = RepositorySelectedSession(sessionId)
    private val allPersonsInSession: LiveData<List<Person>>

    init {
        mSessionId = sessionId
        allPersonsInSession = repositorySelectedSession.getPersonsFromSession(mSessionId)
    }

    fun getAllPersonsInSession(): LiveData<List<Person>> {
        return allPersonsInSession
    }
    fun saveNewPerson(text: Editable) {
        if (text.isNotEmpty()) {
            val saved = Person(text.toString(), mSessionId)
            repositorySelectedSession.insertPerson(saved)
            Log.e("INSERT", "Person with name = $text was inserted")
        } else {
            Log.e("INSERT", "text is empty")
        }
    }

}
