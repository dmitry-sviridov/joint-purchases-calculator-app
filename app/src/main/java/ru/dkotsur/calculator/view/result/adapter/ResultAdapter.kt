package ru.dkotsur.calculator.view.result.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.li_result.view.*
import ru.dkotsur.calculator.R
import ru.dkotsur.calculator.data.CalculationResult

class ResultAdapter: ListAdapter<CalculationResult, ResultAdapter.Holder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.li_result, parent, false)
        return Holder(itemView)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val firstPerson = itemView.person_1
        private val secondPerson = itemView.person_2
        private val resultMoney = itemView.tw_result_money

        fun bind(result: CalculationResult) {
            firstPerson.text = result.userName_1
            secondPerson.text = result.userName_2
            resultMoney.text = result.money.toString()
        }

    }


    companion object {
        private val DIFF_CALLBACK = object: DiffUtil.ItemCallback<CalculationResult>() {
            override fun areItemsTheSame(oldItem:CalculationResult, newItem:CalculationResult):Boolean {
                return (oldItem.userName_1 == newItem.userName_1 &&
                        oldItem.userName_2 == newItem.userName_2)
            }
            override fun areContentsTheSame(oldItem:CalculationResult, newItem:CalculationResult):Boolean {
                return (oldItem.userName_1 == newItem.userName_1 &&
                        oldItem.userName_2 == newItem.userName_2 &&
                        oldItem.money == newItem.money)
            }
        }
    }

}