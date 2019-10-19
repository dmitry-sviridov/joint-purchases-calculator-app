package ru.dkotsur.calculator.view.result

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_calculation.*
import ru.dkotsur.calculator.R
import ru.dkotsur.calculator.data.CalculationResult
import ru.dkotsur.calculator.view.event.EventActivity
import ru.dkotsur.calculator.view.item.ItemActivity
import ru.dkotsur.calculator.view.result.adapter.ResultAdapter
import ru.dkotsur.calculator.viewmodel.ResultViewModel
import java.util.*

class CalculationActivity : AppCompatActivity() {

    companion object {
        val EXTRA_SESSION_ID = "ru.dkotsur.calculator.result.SessionID"
    }

    private lateinit var progress_indication: ProgressBar
    private lateinit var viewModel: ResultViewModel
    private lateinit var recyclerViewAdapter: ResultAdapter
    private lateinit var transactions: MutableLiveData<List<CalculationResult>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculation)
        recyclerViewAdapter = ResultAdapter()

        val id = intent.getLongExtra(EXTRA_SESSION_ID, -1L)
        if (id < 0) {
            finish()
        }

        viewModel = ResultViewModel(id)
        transactions = viewModel.getTransactions()
        progress_indication = findViewById(R.id.progress_circular)
        progress_indication.visibility = View.VISIBLE
        initRecyclerView()
    }


    fun initRecyclerView() {
        transactions.observe(this, Observer {
            it.forEach {
                Log.e("234", "${it.userName_1} -> ${it.money} -> ${it.userName_2} ")
            }
            it.let(recyclerViewAdapter::submitList)
            if (rv_result.adapter!!.itemCount > 0) {
                rv_result.smoothScrollToPosition(rv_result.adapter!!.itemCount)
                progress_indication.visibility = View.GONE
            }
        })

        rv_result.apply {
            layoutManager = LinearLayoutManager(this.context).apply {
                stackFromEnd = false
            }
            adapter = recyclerViewAdapter
            setHasFixedSize(false)
        }
    }
}
