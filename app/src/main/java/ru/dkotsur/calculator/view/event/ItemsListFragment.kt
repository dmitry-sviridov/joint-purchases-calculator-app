package ru.dkotsur.calculator.view.event


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fr_items_list.*
import ru.dkotsur.calculator.R
import ru.dkotsur.calculator.data.db.entity.Item
import ru.dkotsur.calculator.view.event.adapter.ItemsAdapter
import ru.dkotsur.calculator.view.item.ItemActivity
import ru.dkotsur.calculator.viewmodel.EventViewModel

/**
 * A simple [Fragment] subclass.
 */
class ItemsListFragment : Fragment() {

    companion object {
        fun newInstance() = ItemsListFragment()
    }

    private lateinit var viewModel: EventViewModel
    private lateinit var itemsAdapter: ItemsAdapter
    private lateinit var fabAddItem: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fr_items_list, container, false)
        fabAddItem = root.findViewById(R.id.fab_add_item)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = activity!!.run {
            ViewModelProviders.of(this).get(EventViewModel::class.java)
        }
        itemsAdapter = ItemsAdapter()
        initFab()
        initRecyclerView()
    }

    private fun initFab() {
        val id = viewModel.getSessionId()
        fabAddItem.setOnClickListener{
            startActivity(
                Intent(activity, ItemActivity::class.java).putExtra(ItemActivity.EXTRA_SESSION_ID, id))
        }

    }

    private fun initRecyclerView() {
        viewModel.getAllItemsInSession().observe(this, Observer {
            it.let(itemsAdapter::submitList)
            if (rv_items.adapter!!.itemCount > 0) {
                rv_items.smoothScrollToPosition(rv_items.adapter!!.itemCount)
            }
        })

        rv_items.apply {
            layoutManager = LinearLayoutManager(this.context).apply {
                stackFromEnd = true
                reverseLayout = true
            }
            adapter = itemsAdapter
            setHasFixedSize(true)
        }

        itemsAdapter.setOnItemClickListener(object : ItemsAdapter.onItemClickListener {
            override fun onItemClicked(item: Item) {
                startActivity(
                    Intent(activity, ItemActivity::class.java).putExtra(ItemActivity.EXTRA_SESSION_ID, viewModel.getSessionId())
                        .putExtra(ItemActivity.EXTRA_ITEM_ID, item.id)
                )
            }
            override fun onDeleteItem(item: Item) {
                viewModel.deleteItem(item)
            }
        })
    }
}
