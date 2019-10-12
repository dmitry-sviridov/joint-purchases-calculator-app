package ru.dkotsur.calculator.view.event.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.li_persons.view.*
import ru.dkotsur.calculator.R
import ru.dkotsur.calculator.data.db.entity.Person

class PersonsAdapter: ListAdapter<Person, PersonsAdapter.Holder>(DIFF_CALLBACK) {

    lateinit var listener: PersonsAdapter.onItemClickListener

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
        private val deletePerson: ImageView = itemView.btn_delete_person

        init {
            deletePerson.setOnClickListener{
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    listener.onDeletePersonClick(getItem(pos))
                }
            }
        }

        fun bind(person: Person) {
            personName.text = person.name
        }

    }

    interface onItemClickListener {
        fun onDeletePersonClick(person: Person)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        this.listener = listener
    }

    fun getPersonAt(position:Int): Person {
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