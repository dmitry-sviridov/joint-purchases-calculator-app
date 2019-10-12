package ru.dkotsur.calculator.view.item

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_add_iem.*
import ru.dkotsur.calculator.R
import ru.dkotsur.calculator.viewmodel.AddItemViewModel
import ru.dkotsur.calculator.viewmodel.factory.AddItemViewModelFactory


class AddEditItemActivity : AppCompatActivity() {

    companion object {
        val EXTRA_SESSION_ID = "ru.dkotsur.calculator.addItem.SessionID"
        val EXTRA_ITEM_ID = "ru.dkotsur.calculator.editItem.ItemID"
    }

    private var sessionId: Long = -1
    private var itemId: Long = -1
    private lateinit var viewModel: AddItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ru.dkotsur.calculator.R.layout.activity_add_iem)
        sessionId = intent.getLongExtra(EXTRA_SESSION_ID, -1)
        itemId = intent.getLongExtra(EXTRA_ITEM_ID, -1)
        checkIsEdit()
    }


    private fun checkIsEdit() {
        if (itemId == (-1).toLong()) {
            add_item_title.text = getString(ru.dkotsur.calculator.R.string.add_item)
            setupViewModel(sessionId, -1)
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
        transaction.add(ru.dkotsur.calculator.R.id.container_fr, fragment, fragment.tag)
        transaction.commit()
    }

    private fun setupViewModel(sessionId: Long, itemId: Long) {
        var factory = AddItemViewModelFactory(sessionId)
        viewModel = ViewModelProviders.of(this, factory).get(AddItemViewModel::class.java)
    }


}
