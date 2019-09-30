package ru.dkotsur.calculator.view.session

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import ru.dkotsur.calculator.R
import ru.dkotsur.calculator.data.db.entity.Session
import ru.dkotsur.calculator.view.adapter.SessionsAdapter
import ru.dkotsur.calculator.viewmodel.SessionViewModel


class SessionActivity : AppCompatActivity() {

    private lateinit var sheetBehavior: BottomSheetBehavior<View>
    private lateinit var bottomSheet: LinearLayout
    private lateinit var editTextSession: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle(R.string.session_screen_title)
        val viewModel = ViewModelProviders.of(this).get(SessionViewModel::class.java)
        val sessionAdapter = SessionsAdapter()

        rv_sessions.apply {
            layoutManager = LinearLayoutManager(this.context).apply {
                stackFromEnd = true
                reverseLayout = true
            }
            adapter = sessionAdapter
            setHasFixedSize(true)
        }

        viewModel.getAllSession().observe(this, Observer {
            it?.let(sessionAdapter::submitList)
            val count = it.size
            rv_sessions.smoothScrollToPosition(rv_sessions.adapter!!.itemCount)
        })


        bottomSheet = findViewById(R.id.bottom_sheet)
        editTextSession = findViewById(R.id.edit_text_session)
        sheetBehavior = BottomSheetBehavior.from(bottomSheet)
        sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        val fabAddSession = findViewById<FloatingActionButton>(R.id.fab_add_session)
        fabAddSession.setOnClickListener{
            sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        editTextSession.setOnEditorActionListener { v, actionId, event ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.saveSession(editTextSession.text)
                closeKeyboard()
                closeBottomsheet()
                handled = true
            }
            handled
        }


        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.deleteSession(sessionAdapter.getSessionAt(viewHolder.adapterPosition))
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }
        }).attachToRecyclerView(rv_sessions)


        sessionAdapter.setOnItemClickListener(object: SessionsAdapter.onItemClickListener {
            override fun onItemClick(session: Session) {
                var intent = Intent(this@SessionActivity, EditSessionActivity::class.java)
                intent.putExtra(EditSessionActivity.EXTRA_ID, session.id)
                startActivity(intent)
            }
        })

    }

    private fun closeKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun closeBottomsheet() {
        Handler().postDelayed({
            sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            editTextSession.text.clear()
        }, 500)
    }

}
