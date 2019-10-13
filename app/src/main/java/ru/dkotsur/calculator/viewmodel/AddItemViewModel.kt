package ru.dkotsur.calculator.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.dkotsur.calculator.data.db.entity.Item
import ru.dkotsur.calculator.data.db.entity.Person
import ru.dkotsur.calculator.data.db.repository.RepositoryItem
import ru.dkotsur.calculator.data.db.repository.RepositorySelectedSession

class AddItemViewModel(sessionId: Long) : ViewModel() {

    private var mSessionId: Long = 0
    private val repositorySelectedSession = RepositorySelectedSession(sessionId)
    private val repositoryAddItem =
        RepositoryItem(sessionId)

    private val allPersonsInSession: LiveData<List<Person>>

    init {
        mSessionId = sessionId
        allPersonsInSession = repositorySelectedSession.getPersonsFromSession(mSessionId)
    }

    fun getAllPersonsInSession(): LiveData<List<Person>> {
        return allPersonsInSession
    }

    fun saveNewItem(itemTitle: String, itemCost: Double, bayerId: Long, personsIds: List<Long>) {
        val item = Item(itemTitle, itemCost, bayerId, mSessionId)
        var itemId = repositoryAddItem.insertItem(item)
        repositoryAddItem.insertPersonsItems(itemId, personsIds)
    }
}