package ru.dkotsur.calculator.view.result

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_calculation.*
import ru.dkotsur.calculator.R
import ru.dkotsur.calculator.view.item.ItemActivity
import ru.dkotsur.calculator.view.result.adapter.ResultAdapter
import ru.dkotsur.calculator.viewmodel.ResultViewModel

class CalculationActivity : AppCompatActivity() {

    companion object {
        val EXTRA_SESSION_ID = "ru.dkotsur.calculator.result.SessionID"
    }


    private lateinit var progress_indication: ProgressBar
    private lateinit var viewModel: ResultViewModel
    private lateinit var recyclerViewAdapter: ResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculation)
        recyclerViewAdapter = ResultAdapter()

        val id = intent.getLongExtra(ItemActivity.EXTRA_SESSION_ID, -1L)
        if (id < 0) {
            finish()
        }

        viewModel = ResultViewModel(id)
        progress_indication = findViewById(R.id.progress_circular)
        progress_indication.visibility = View.VISIBLE
        initRecyclerView()
    }


    fun initRecyclerView() {
        viewModel.calculateTransactions().observe(this, Observer {
            it.let(recyclerViewAdapter::submitList)
        })

        rv_result.apply {

        }
    }
}
