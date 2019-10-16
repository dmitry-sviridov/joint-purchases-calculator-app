package ru.dkotsur.calculator.view.item


import android.os.Bundle
import android.provider.SyncStateContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.btn_save_item.*
import kotlinx.android.synthetic.main.c_persons_in_item.*
import kotlinx.android.synthetic.main.fr_add_new_item.*
import ru.dkotsur.calculator.R
import ru.dkotsur.calculator.data.db.entity.Person
import ru.dkotsur.calculator.utils.Helpers
import ru.dkotsur.calculator.viewmodel.AddItemViewModel
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 */
class AddItemFragment : Fragment() {

    companion object {
        val TAG = javaClass.simpleName
    }

    private lateinit var viewModel: AddItemViewModel
    private lateinit var personsGenerated: HashMap<View, Long>
    private lateinit var personsLayout: List<View>
    private var markedPersons = HashSet<Long>()
    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fr_add_new_item, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = activity!!.run {
            ViewModelProviders.of(this).get(AddItemViewModel::class.java)
        }

        initSpinner()
        initCustomViewWithPersons()
        initSaveOperation()
    }

    private fun initSpinner() {
        var personsNamesAdapter = ArrayAdapter<Person>(
            activity!!,
            R.layout.spinner_row
        )
        viewModel.getAllPersonsInSession().observe(this, Observer { it ->
            it.reversed().forEach {
                personsNamesAdapter.add(it)
            }
        })

        spinner_bayer_selection.adapter = personsNamesAdapter
    }

    private fun initCustomViewWithPersons() {
        personsGenerated = HashMap()
        personsLayout = ArrayList<View>()
        viewModel.getAllPersonsInSession().observe(this, Observer { it ->

            it.forEach {
                val container = layoutInflater.inflate(R.layout.c_persons_in_item, null)
                val textView = container.findViewById<TextView>(R.id.tw_person_in_item)
                val switcher = container.findViewById<SwitchCompat>(R.id.switcher_set_person)

                textView.text = it.name
                (personsLayout as ArrayList<View>).add(container)
                personsGenerated.put(container, it.id)
                linear_container.addView(container)

                switcher.setOnCheckedChangeListener { compoundButton, isChecked ->
                    if (isChecked) {
                        markedPersons.add(it.id)
                    } else {
                        markedPersons.remove(it.id)
                    }
                }
            }
        })
    }

    private fun initSaveOperation() {
        btn_save_item.setOnClickListener{
            val itemTitle = edit_text_item_title.text.toString()
            val itemCost = edit_text_items_cost.text.toString()
            val bayerId = (spinner_bayer_selection.selectedItem as Person).id
            val personsIds = markedPersons.toList()

            if (Helpers.validateFields(itemTitle, itemCost, personsIds)) {
                viewModel.saveNewItem(itemTitle = itemTitle, itemCost = itemCost.toDouble(),
                    bayerId = bayerId, personsIds = personsIds)
                activity!!.finish()
            } else {
                Toast.makeText(activity,getString(R.string.toast_incorrect_save_item), Toast.LENGTH_SHORT).show()
            }

        }
    }
}

