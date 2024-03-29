package ru.dkotsur.calculator.viewmodel

import android.text.Editable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.dkotsur.calculator.data.db.entity.Item
import ru.dkotsur.calculator.data.db.entity.Person
import ru.dkotsur.calculator.data.db.repository.RepositoryItem
import ru.dkotsur.calculator.data.db.repository.RepositorySelectedSession

class EventViewModel(sessionId: Long) : ViewModel() {

    private var mSessionId: Long = 0
    private val repositorySelectedSession = RepositorySelectedSession(sessionId)
    private val repositoryItem = RepositoryItem(sessionId)
    private val allPersonsInSession: LiveData<List<Person>>
    private val allItemsInSession: LiveData<List<Item>>

    init {
        mSessionId = sessionId
        allPersonsInSession = repositorySelectedSession.getPersonsFromSession(mSessionId)
        allItemsInSession = repositorySelectedSession.getSessionItems(mSessionId)
    }

    fun getAllPersonsInSession(): LiveData<List<Person>> {
        return allPersonsInSession
    }

    fun getAllItemsInSession(): LiveData<List<Item>> {
        return allItemsInSession
    }

    fun deleteItem(item: Item) {
        repositoryItem.deleteItem(item)
    }

    fun saveNewPerson(text: Editable) {
        if (text.isNotEmpty()) {
            val saved = Person(text.toString(), mSessionId)
            repositorySelectedSession.insertPerson(saved)
        } else {
            Log.e("INSERT", "text is empty")
        }
    }

    fun deletePerson(person: Person) {
        repositorySelectedSession.deletePerson(person)
    }

    fun getSessionId(): Long {
        return mSessionId
    }

}
