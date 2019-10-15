package ru.dkotsur.calculator.view.item.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.li_persons_item.view.*
import ru.dkotsur.calculator.R
import ru.dkotsur.calculator.data.db.entity.Person


class PersonsItemAdapter(val markedPersons: HashSet<Long>) : ListAdapter<Person, PersonsItemAdapter.Holder>(DIFF_CALLBACK) {

    lateinit var listener: onItemClickListener

    override fun onBindViewHolder(holder: PersonsItemAdapter.Holder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonsItemAdapter.Holder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.li_persons_item, parent, false)
        return Holder(itemView)
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val personName = itemView.tw_person_name
        private val markPerson: SwitchCompat = itemView.rb_set_user

        init {
            markPerson.setOnCheckedChangeListener { _, isChecked ->
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    if (isChecked) {
                        listener.onPersonMarkedTrue(getPersonIdAt(pos))
                    } else {
                        listener.onPersonMarkedFalse(getPersonIdAt(pos))
                    }
                }
            }
        }

        fun bind(person: Person) {
            personName.text = person.name
            if (person.id in markedPersons) {
                markPerson.isChecked = true
            }
        }
    }

    interface onItemClickListener {
        fun onPersonMarkedTrue(person: Long)
        fun onPersonMarkedFalse(person: Long)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        this.listener = listener
    }

    fun getPersonAt(position:Int): Person {
        return getItem(position)
    }

    fun getPersonIdAt(position: Int): Long {
        return getItem(position).id
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