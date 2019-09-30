package ru.dkotsur.calculator.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.li_session.view.*
import ru.dkotsur.calculator.R
import ru.dkotsur.calculator.data.db.entity.Session

class SessionsAdapter: ListAdapter<Session, SessionsAdapter.Holder>(DIFF_CALLBACK) {

    lateinit var listener: onItemClickListener

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.li_session, parent, false)
        return Holder(itemView)
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val sessionTitle = itemView.tw_session_title
        private val sessionDate = itemView.tw_session_date

        init {
            itemView.setOnClickListener {
                val pos = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(pos))
                }
            }
        }

        fun bind(session: Session) {
            sessionTitle.text = session.title
            sessionDate.text = session.date
        }
    }

    interface onItemClickListener {
        fun onItemClick(session: Session)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        this.listener = listener
    }

    fun getSessionAt(position:Int): Session {
        return getItem(position)
    }

    companion object {
        private val DIFF_CALLBACK = object: DiffUtil.ItemCallback<Session>() {
            override fun areItemsTheSame(oldItem:Session, newItem:Session):Boolean {
                return oldItem.id === newItem.id
            }
            override fun areContentsTheSame(oldItem:Session, newItem:Session):Boolean {
                return (oldItem.title == newItem.title &&
                        oldItem.date == newItem.date)
            }
        }
    }
}