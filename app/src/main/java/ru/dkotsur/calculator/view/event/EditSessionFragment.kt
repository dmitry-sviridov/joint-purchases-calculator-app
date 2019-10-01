package ru.dkotsur.calculator.view.event

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.dkotsur.calculator.R
import ru.dkotsur.calculator.viewmodel.EditSessionViewModel

class EditSessionFragment : Fragment() {

    companion object {
        fun newInstance() = EditSessionFragment()
    }

    private lateinit var viewModel: EditSessionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.edit_session_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(EditSessionViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
