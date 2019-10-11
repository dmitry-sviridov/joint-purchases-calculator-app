package ru.dkotsur.calculator.view.event.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.li_persons.view.*
import ru.dkotsur.calculator.R
import ru.dkotsur.calculator.data.db.entity.Person

class PersonsAdapter: ListAdapter<Person, PersonsAdapter.Holder>(DIFF_CALLBACK) {

    lateinit var listener: onItemClickListener

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.li_persons, parent, false)
        return Holder(itemView)
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val personName = itemView.tw_person_name

        init {
            itemView.setOnClickListener {
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(pos))
                }
            }
        }

        fun bind(person: Person) {
            personName.text = person.name
        }
    }

    interface onItemClickListener {
        fun onItemClick(person: Person)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        this.listener = listener
    }

    fun getSessionAt(position:Int): Person {
        return getItem(position)
    }

    companion object {
        private val DIFF_CALLBACK = object: DiffUtil.ItemCallback<Person>() {
            override fun areItemsTheSame(oldItem: Person, newItem: Person):Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: Person, newItem: Person):Boolean {
                return (oldItem.name == newItem.name )
            }
        }
    }
}