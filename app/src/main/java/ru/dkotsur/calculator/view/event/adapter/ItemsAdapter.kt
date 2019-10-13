package ru.dkotsur.calculator.view.event.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.li_items.view.*
import ru.dkotsur.calculator.R
import ru.dkotsur.calculator.data.db.entity.Item
import ru.dkotsur.calculator.data.db.entity.Person
import ru.dkotsur.calculator.view.event.adapter.ItemsAdapter.Holder

class ItemsAdapter: ListAdapter<Item, Holder>(DIFF_CALLBACK) {

    lateinit var listener: onItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.li_items, parent, false)
        return Holder(itemView)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val itemTitle = itemView.tw_item_title
        private val itemCost = itemView.tw_item_cost
        private val removeButton = itemView.btn_delete_item

        fun bind(item: Item) {
            itemTitle.text = item.title
            itemCost.text = item.cost.toString()
        }

        init {
            removeButton.setOnClickListener{
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    listener.onDeleteItem(getItemAt(pos))
                }
            }
        }
    }

    interface onItemClickListener {
        fun onDeleteItem(item: Item)
        fun onItemClicked(item: Item)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        this.listener = listener
    }

    fun getItemAt(position:Int): Item {
        return getItem(position)
    }

    companion object {
        private val DIFF_CALLBACK = object: DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item):Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: Item, newItem: Item):Boolean {
                return (oldItem.title == newItem.title && oldItem.cost == newItem.cost )
            }
        }
    }
}