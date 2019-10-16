package ru.dkotsur.calculator.view.item

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_add_iem.*
import ru.dkotsur.calculator.R
import ru.dkotsur.calculator.viewmodel.AddItemViewModel
import ru.dkotsur.calculator.viewmodel.EditItemViewModel
import ru.dkotsur.calculator.viewmodel.factory.AddItemViewModelFactory
import ru.dkotsur.calculator.viewmodel.factory.EditItemViewModelFactory


class AddEditItemActivity : AppCompatActivity() {

    companion object {
        val EXTRA_SESSION_ID = "ru.dkotsur.calculator.addItem.SessionID"
        val EXTRA_ITEM_ID = "ru.dkotsur.calculator.editItem.ItemID"
    }


    private lateinit var viewModel: ViewModel
    private var sessionId: Long = 0L
    private var itemId: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_iem)
        sessionId = intent.getLongExtra(EXTRA_SESSION_ID, -1L)
        itemId = intent.getLongExtra(EXTRA_ITEM_ID, -1L)
        checkIsEdit(sessionId, itemId)
    }


    private fun checkIsEdit(sessionId: Long, itemId: Long) {
        if (itemId == -1L) {
            add_item_title.text = getString(ru.dkotsur.calculator.R.string.add_item)
            setupViewModel(sessionId, itemId)
            startFragment(AddItemFragment())
        } else {
            add_item_title.text = getString(R.string.edit_item)
            setupViewModel(sessionId, itemId)
            startFragment(EditItemFragment())
        }
    }

    private fun startFragment(fragment: Fragment) {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.add(R.id.container_fr, fragment, fragment.tag)
        transaction.commit()
    }

    private fun setupViewModel(sessionId: Long, itemId: Long) {
        viewModel = if (itemId == -1L) {
            var factory = AddItemViewModelFactory(sessionId)
            ViewModelProviders.of(this, factory).get(AddItemViewModel::class.java)
        } else {
            var factory = EditItemViewModelFactory(sessionId, itemId)
            ViewModelProviders.of(this, factory).get(EditItemViewModel::class.java)
        }
    }

    fun currentItemId(): Long {
        return itemId
    }


}
