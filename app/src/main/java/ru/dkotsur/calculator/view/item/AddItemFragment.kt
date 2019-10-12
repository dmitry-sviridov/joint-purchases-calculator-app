package ru.dkotsur.calculator.view.item


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fr_add_new_item.*
import ru.dkotsur.calculator.R
import ru.dkotsur.calculator.viewmodel.AddItemViewModel

/**
 * A simple [Fragment] subclass.
 */
class AddItemFragment : Fragment() {

    companion object {
        val TAG = javaClass.simpleName
    }

    private lateinit var viewModel: AddItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fr_add_new_item, container, false)
        return root

        }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = activity!!.run {
            ViewModelProviders.of(this).get(AddItemViewModel::class.java)
        }
        initData()
    }



    private fun initData() {
        var personsNamesAdapter = activity!!.let {
            ArrayAdapter<Any>(
                it,
                R.layout.spinner_row
            )
        }
        viewModel.getAllPersonsInSession().observe(this, Observer { it ->
            it.forEach {
                personsNamesAdapter.add(it.name)
            }
        })

        spinner_bayer_selection.adapter = personsNamesAdapter
    }

}

