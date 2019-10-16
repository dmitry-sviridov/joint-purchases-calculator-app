package ru.dkotsur.calculator.utils

import android.util.Log
import java.lang.Exception


class Helpers {

    companion object {
        fun validateFields(
            itemTitle: String,
            itemCost: String,
            personsIds: List<Long>
        ): Boolean {
            var result: Boolean
            try {
                result = (itemTitle.isNotEmpty() && itemCost.isNotEmpty() && personsIds.isNotEmpty() && itemCost.toDouble() > 0)
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }
            return result
        }
    }
}
