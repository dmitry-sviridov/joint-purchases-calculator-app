package ru.dkotsur.calculator.viewmodel

import android.util.Log
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

    val TAG = this.javaClass.simpleName

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
        calculateTransactions()
    }

    fun getTransactions(): MutableLiveData<List<CalculationResult>> {
        return resultList
    }

    private fun fillItemsWithPersons() {
        itemsList.forEach {
            val persons = repositoryItem.getPersonsList(it.id)
            val ids = ArrayList<Long>()
            for (person in persons) {
                ids.add(person.id)
            }
            it.addUsers(ids)
        }
    }


    //TODO: FIX OOM, REMOVE BIGDECIMAL

    private fun calculateBalances() {
        itemsList.forEach { item ->
            val cost = item.cost.toBigDecimal()
            val unitcost = item.unitCost.setScale(2)

            val bayerId = item.bayerId
            personsList.forEach {
                if (it.id.equals(bayerId)) {
                    it.plusBudget(cost)
                }
                if (it.id in item.users) {
                    it.minusBudget(unitcost)
                }
            }
        }
    }

    private fun calculateTransactions() {
        var transactions = ArrayList<CalculationResult>()
        val iterator = personsList.iterator()

        Log.e(TAG, " size = ${personsList.size}")
        while (personsList.size > 1) {

            personsList.sort()

            val budgetFirst = personsList.first().budget.toDouble()
            val budgetLast = personsList.last().budget.toDouble()
            val nameFirst = personsList.first().name
            val nameLast = personsList.last().name
            var delta: BigDecimal

            if (abs(budgetFirst) >= abs(budgetLast)) {
                delta = personsList.last().budget.abs()
            } else {
                delta = personsList.first().budget.abs()
            }
            transactions.add(CalculationResult(nameFirst, nameLast, delta.toDouble()))

            personsList.first().plusBudget(delta)
            personsList.last().minusBudget(delta)

            while (iterator.hasNext()) {
                if (iterator.next().budget.setScale(2).toDouble() == 0.0) {
                    iterator.remove()
                }
            }

        }
        resultList.postValue(transactions)
    }
}