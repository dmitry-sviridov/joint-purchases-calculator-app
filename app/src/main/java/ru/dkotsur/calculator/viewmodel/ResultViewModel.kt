package ru.dkotsur.calculator.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.dkotsur.calculator.data.CalculationResult
import ru.dkotsur.calculator.data.db.entity.Item
import ru.dkotsur.calculator.data.db.entity.Person
import ru.dkotsur.calculator.data.db.repository.RepositoryItem
import ru.dkotsur.calculator.data.db.repository.RepositorySelectedSession
import java.math.BigDecimal
import kotlin.math.abs

class ResultViewModel(sessionId: Long): ViewModel() {

    val repositorySelectedSession = RepositorySelectedSession(sessionId)
    val repositoryItem = RepositoryItem(sessionId)

    private var personsList = ArrayList<Person>()
    private val itemsList: List<Item>

    private var resultList  = MutableLiveData<List<CalculationResult>>()

    init {
        personsList.addAll(repositorySelectedSession.personsList)
        itemsList = repositorySelectedSession.itemsList
        fillItemsWithPersons()
        calculateBalances()
    }

    private fun fillItemsWithPersons() {
        itemsList.forEach {
            it.addUsers(repositoryItem.getPersonsList(it.id))
        }
    }

    private fun calculateBalances() {
        itemsList.forEach {
            repositoryItem.getPersonById(it.bayerId).plusBudget(it.cost.toBigDecimal())
            for (user in it.users) {
                user.minusBudget(it.unitCost.setScale(15))
            }
        }
    }

    fun calculateTransactions(): MutableLiveData<CalculationResult> {
        var first = 0
        var last: Int

        while (personsList.size > 1) {
            personsList.sort()
            last = personsList.size - 1

            var budgetFirst = personsList.get(first).budget.toDouble()
            var budgetLast = personsList.get(last).budget.toDouble()
            var delta: BigDecimal

            if (abs(budgetFirst) >= abs(budgetLast)) {
                delta = personsList.get(last).budget.abs()
            } else {
                delta = personsList.get(first).budget.abs()
            }

//            resultList.postValue()
//
//                (CalculationResult(personsList.get(first).name,
//                personsList.get(first).name, delta.toDouble()))
            personsList.get(first).plusBudget(delta)
            personsList.get(last).minusBudget(delta)

            val iterator = personsList.iterator()
            while (iterator.hasNext()) {
                if (iterator.next().budget.setScale(1).toDouble() == 0.0) {
                    iterator.remove()
                }
            }
        }

        return resultList
    }

    //TODO FIX


}