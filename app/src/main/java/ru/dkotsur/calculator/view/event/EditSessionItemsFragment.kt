package ru.dkotsur.calculator.view.event


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.dkotsur.calculator.R
import ru.dkotsur.calculator.view.item.AddEditItemActivity
import ru.dkotsur.calculator.viewmodel.EditSessionViewModel

/**
 * A simple [Fragment] subclass.
 */
class EditSessionItemsFragment : Fragment() {

    companion object {
        fun newInstance() = EditSessionItemsFragment()
    }

    private lateinit var viewModel: EditSessionViewModel
    private lateinit var fabAddItem: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fr_edit_items_in_session, container, false)
        fabAddItem = root.findViewById(R.id.fab_add_item)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = activity!!.run {
            ViewModelProviders.of(this).get(EditSessionViewModel::class.java)
        }
        initFab()
    }

    private fun initFab() {
        val id = viewModel.getSessionId()
        fabAddItem.setOnClickListener{
            startActivity(
                Intent(activity, AddEditItemActivity::class.java).putExtra(AddEditItemActivity.EXTRA_SESSION_ID, id))
        }

    }
}
