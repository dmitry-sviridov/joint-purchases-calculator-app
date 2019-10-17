package ru.dkotsur.calculator.view.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.btn_save_item.*
import kotlinx.android.synthetic.main.fr_items_edit.*
import ru.dkotsur.calculator.R
import ru.dkotsur.calculator.data.db.entity.Person
import ru.dkotsur.calculator.utils.Helpers
import ru.dkotsur.calculator.viewmodel.EditItemViewModel

class EditItemFragment: Fragment() {


    companion object {
        val TAG = javaClass.simpleName
    }

    private lateinit var viewModel: EditItemViewModel
    private lateinit var personsLayout: List<View>
    private var markedPersons = HashSet<Long>()
    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fr_items_edit, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = activity!!.run {
            ViewModelProviders.of(this).get(EditItemViewModel::class.java)
        }
        personsLayout = ArrayList<View>()

        fetchData()
        initSaveOperation()
    }

    private fun fetchData() {
        markedPersons.addAll(viewModel.getAllPersonsInItemIDS())
        var personsNamesAdapter = ArrayAdapter<Person>(
            activity!!,
            R.layout.spinner_row
        )

        viewModel.getAllPersonsInSession().observe(this, Observer { it ->
            it.forEachIndexed { index, p ->
                personsNamesAdapter.add(p)
                if (p.id == viewModel.getItemsBayer().id) {
                    spinner_bayer_selection.setSelection(index)
                }
                initCustomViewWithPersons(p.name, p.id)
            }
        })

        spinner_bayer_selection.adapter = personsNamesAdapter
        edit_text_item_title.setText(viewModel.getItemById().title)
        edit_text_items_cost.setText(viewModel.getItemById().cost.toString())
    }

    private fun initCustomViewWithPersons(name: String, id: Long) {
        val container = layoutInflater.inflate(R.layout.c_persons_in_item, null)
        val textView = container.findViewById<TextView>(R.id.tw_person_in_item)
        val switcher = container.findViewById<SwitchCompat>(R.id.switcher_set_person)

        textView.text = name
        (personsLayout as ArrayList<View>).add(container)
        linear_container.addView(container)

        val listener = CompoundButton.OnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                markedPersons.add(id)
            } else {
                markedPersons.remove(id)
            }
        }
        if (id in markedPersons) {
            switcher.setOnCheckedChangeListener(null)
            switcher.isChecked = true
        }
        switcher.setOnCheckedChangeListener(listener)

    }

    private fun initSaveOperation() {
        btn_save_item.setOnClickListener{
            val itemId = (activity as ItemActivity).currentItemId()
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
