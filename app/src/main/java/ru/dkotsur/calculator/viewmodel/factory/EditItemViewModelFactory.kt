package ru.dkotsur.calculator.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.dkotsur.calculator.viewmodel.EditItemViewModel

class EditItemViewModelFactory(val sessionId: Long, val itemId: Long): ViewModelProvider.Factory {

    private var mSessionId: Long = 0
    private var mItemId: Long = 0

    init {
        mSessionId = sessionId
        mItemId = itemId
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EditItemViewModel(mSessionId, mItemId) as T
    }
}