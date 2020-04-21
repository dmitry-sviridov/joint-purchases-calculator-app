package ru.dkotsur.calculator.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.dkotsur.calculator.viewmodel.EventViewModel

class EventViewModelFactory(sessionId: Long): ViewModelProvider.Factory {

    private var mSessionId: Long = 0

    init {
        mSessionId = sessionId
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EventViewModel(mSessionId) as T
    }
}