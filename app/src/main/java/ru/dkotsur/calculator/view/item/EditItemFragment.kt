package ru.dkotsur.calculator.view.item

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.btn_save_item.*
import kotlinx.android.synthetic.main.fr_add_new_item.*
import ru.dkotsur.calculator.R
import ru.dkotsur.calculator.data.db.entity.Person
import ru.dkotsur.calculator.utils.Helpers
import ru.dkotsur.calculator.viewmodel.EditItemViewModel

class EditItemFragment: Fragment() {


    companion object {
        val TAG = javaClass.simpleName
    }

    private lateinit var viewModel: EditItemViewModel
    private lateinit var personsGenerated: HashMap<View, Long>
    private lateinit var personsLayout: List<View>
    private val markedPersons = HashSet<Long>()
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
            ViewModelProviders.of(this).get(EditItemViewModel::class.java)
        }

        fetchData()
        initCustomViewWithPersons()
        initSaveOperation()
    }

    private fun fetchData() {
        var personsNamesAdapter = ArrayAdapter<Person>(
            activity!!,
            R.layout.spinner_row
        )

        viewModel.getAllPersonsInSession().observe(this, Observer { it ->
            it.reversed().forEachIndexed { index, p ->
                personsNamesAdapter.add(p)
                if (p.id == viewModel.getItemsBayer().id) {
                    spinner_bayer_selection.setSelection(index)
                }
            }
        })

        spinner_bayer_selection.adapter = personsNamesAdapter

        edit_text_item_title.setText(viewModel.getItemById().title)
        edit_text_items_cost.setText(viewModel.getItemById().cost.toString())

        viewModel.getAllPersonsInItemIDS().observe(this, Observer { it ->
            it.forEach {
                markedPersons.add(it)
                Log.e("ADD PERSON TO MARKED" , "ADDED WITH ID = $it")
            }
        })
    }

    private fun initCustomViewWithPersons() {
        personsGenerated = HashMap()
        personsLayout = ArrayList<View>()
        val smth = viewModel.getAllPersonsInSession().observe(this, Observer { it ->

            it.forEach {
                val container = layoutInflater.inflate(R.layout.c_persons_in_item, null)
                val textView = container.findViewById<TextView>(R.id.tw_person_in_item)
                val switcher = container.findViewById<SwitchCompat>(R.id.switcher_set_person)

                textView.text = it.name
                (personsLayout as ArrayList<View>).add(container)
                personsGenerated.put(container, it.id)
                linear_container.addView(container)

                val listener = CompoundButton.OnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        markedPersons.add(it.id)
                        Log.e("Debug", "Added person with name = ${it.name}")
                    } else {
                        markedPersons.remove(it.id)
                        Log.e("Debug", "Removed person with name = ${it.name}")
                    }
                }

                switcher.setOnCheckedChangeListener(listener)
            }
        })

    }

    private fun initSaveOperation() {
        btn_save_item.setOnClickListener{
            val itemId = (activity as AddEditItemActivity).currentItemId()
            val itemTitle = edit_text_item_title.text.toString()
            val itemCost = edit_text_items_cost.text.toString()
            val bayerId = (spinner_bayer_selection.selectedItem as Person).id
            val personsIds = markedPersons.toList()

            if (Helpers.validateFields(itemTitle, itemCost, personsIds)) {
                viewModel.updateItem(itemId, itemTitle = itemTitle, itemCost = itemCost.toDouble(),
                    bayerId = bayerId, personsIds = personsIds)
                activity!!.finish()
            } else {
                Toast.makeText(activity,getString(R.string.toast_incorrect_save_item), Toast.LENGTH_SHORT).show()
            }
        }
    }
}
