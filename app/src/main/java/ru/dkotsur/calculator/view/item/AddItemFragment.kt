package ru.dkotsur.calculator.view.item


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.btn_save_item.*
import kotlinx.android.synthetic.main.fr_add_new_item.*
import ru.dkotsur.calculator.R
import ru.dkotsur.calculator.data.db.entity.Person
import ru.dkotsur.calculator.view.item.adapter.PersonsItemAdapter
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
    private lateinit var personsItemAdapter: PersonsItemAdapter

    private val markedPersons = HashSet<Long>()

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
        personsItemAdapter = PersonsItemAdapter(markedPersons)
        initRecyclerView()
        initSaveOperation()
    }

    private fun initData() {
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

    private fun initRecyclerView() {
        viewModel.getAllPersonsInSession().observe(this, Observer {
            it?.let(personsItemAdapter::submitList)
            if (rv_persons_item.adapter!!.itemCount > 0) {
                rv_persons_item.smoothScrollToPosition(rv_persons_item.adapter!!.itemCount)
            }
        })

        rv_persons_item.apply {
            layoutManager = LinearLayoutManager(this.context).apply {
                stackFromEnd = true
                reverseLayout = true
            }
            adapter = personsItemAdapter
            setHasFixedSize(true)
        }

        personsItemAdapter.setOnItemClickListener(object : PersonsItemAdapter.onItemClickListener {

            override fun onPersonMarkedTrue(personId: Long) {
                try {
                    markedPersons.add(personId)
                } catch (e: Exception) {
                    e.stackTrace
                }
            }

            override fun onPersonMarkedFalse(personId: Long) {
                try {
                    markedPersons.remove(personId)
                } catch (e: Exception) {
                    e.stackTrace
                }
            }

        })


    }

    private fun initSaveOperation() {
        btn_save_item.setOnClickListener{
            val itemTitle = edit_text_item_title.text.toString()
            val itemCost = edit_text_items_cost.text.toString().toDouble()
            val bayerId = (spinner_bayer_selection.selectedItem as Person).id
            val personsIds = markedPersons.toList()

            viewModel.saveNewItem(itemTitle = itemTitle, itemCost = itemCost,
                                    bayerId = bayerId, personsIds = personsIds)

            activity!!.finish()
        }
    }
}

