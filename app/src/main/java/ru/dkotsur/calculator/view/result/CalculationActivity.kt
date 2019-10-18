package ru.dkotsur.calculator.view.result

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import ru.dkotsur.calculator.R
import ru.dkotsur.calculator.viewmodel.ResultViewModel

class CalculationActivity : AppCompatActivity() {

    private lateinit var progress_indication: ProgressBar
    private lateinit var viewModel: ResultViewModel
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculation)
    }
}
