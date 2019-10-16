package ru.dkotsur.calculator.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.dkotsur.calculator.data.db.entity.Item
import ru.dkotsur.calculator.data.db.entity.Person
import ru.dkotsur.calculator.data.db.repository.RepositoryItem
import ru.dkotsur.calculator.data.db.repository.RepositorySelectedSession

class EditItemViewModel(sessionId: Long, itemId: Long) : ViewModel() {

    private var mItemId: Long = 0
    private var mSessionId: Long = 0

    private val repositorySelectedSession = RepositorySelectedSession(sessionId)
    private val repositoryItem = RepositoryItem(sessionId, itemId)

    private val allPersonsInSession: LiveData<List<Person>>
    private val allPersonsInItem: LiveData<List<Person>>
    private val allPersonsInItemID: LiveData<List<Long>>

    init {
        mSessionId = sessionId
        mItemId = itemId
        allPersonsInSession = repositorySelectedSession.getPersonsFromSession(mSessionId)
        allPersonsInItem = repositoryItem.personsForItem
        allPersonsInItemID = repositoryItem.allPersonsForItemId
    }

    fun getAllPersonsInSession(): LiveData<List<Person>> {
        return allPersonsInSession
    }

    fun getAllPersonsInItemIDS(): LiveData<List<Long>> {
        return allPersonsInItemID
    }

    fun updateItem(
        itemId: Long,
        personsIds: List<Long>,
        itemTitle: String,
        itemCost: Double,
        bayerId: Long)
    {
        val triple: Triple<String, Double, List<Long>> = Triple(itemTitle, itemCost, listOf(itemId, bayerId))
        repositoryItem.updateItem(triple)
        repositoryItem.clearPersonItemForItem(itemId)
        repositoryItem.insertPersonsItems(itemId, personsIds)
    }

    fun getItemsBayer(): Person {
        return repositoryItem.itemsBayer
    }

    fun getItemById(): Item {
        return repositoryItem.getItemById(mItemId)
    }

}