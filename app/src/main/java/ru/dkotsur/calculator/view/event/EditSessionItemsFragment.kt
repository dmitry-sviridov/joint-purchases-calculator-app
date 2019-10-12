package ru.dkotsur.calculator.view.event


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import ru.dkotsur.calculator.R
import ru.dkotsur.calculator.viewmodel.EditSessionViewModel

/**
 * A simple [Fragment] subclass.
 */
class EditSessionItemsFragment : Fragment() {

    companion object {
        fun newInstance() = EditSessionItemsFragment()
    }

    private lateinit var viewModel: EditSessionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.edit_sessions_items_fragment, container, false)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = activity!!.run {
            ViewModelProviders.of(this).get(EditSessionViewModel::class.java)
        }

    }
}
