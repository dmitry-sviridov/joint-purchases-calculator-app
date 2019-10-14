package ru.dkotsur.calculator.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.dkotsur.calculator.data.db.entity.Item
import ru.dkotsur.calculator.data.db.entity.Person
import ru.dkotsur.calculator.data.db.repository.RepositoryItem
import ru.dkotsur.calculator.data.db.repository.RepositorySelectedSession
import kotlin.math.log

class EditItemViewModel(sessionId: Long, itemId: Long) : ViewModel() {

    private var mItemId: Long = 0
    private var mSessionId: Long = 0

    private val repositorySelectedSession = RepositorySelectedSession(sessionId)
    private val repositoryItem = RepositoryItem(sessionId, itemId)

    private val allPersonsInSession: LiveData<List<Person>>

    init {
        mSessionId = sessionId
        mItemId = itemId
        Log.e("1231313123", "session Id = $sessionId itemid = $itemId")
        allPersonsInSession = repositorySelectedSession.getPersonsFromSession(mSessionId)
    }

    fun getAllPersonsInSession(): LiveData<List<Person>> {
        return allPersonsInSession
    }

    fun updateItem(itemTitle: String, itemCost: Double, bayerId: Long, personsIds: List<Long>) {
        val item = Item(itemTitle, itemCost, bayerId, mSessionId)
        var itemId = repositoryItem.insertItem(item)
        repositoryItem.insertPersonsItems(itemId, personsIds)
    }

    fun getItemsBayer(): Person {
        return repositoryItem.itemsBayer
    }

}