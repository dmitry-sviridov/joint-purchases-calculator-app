package ru.dkotsur.calculator.view.event

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.edit_sessions_users_fragment.*
import ru.dkotsur.calculator.R
import ru.dkotsur.calculator.view.event.adapter.PersonsAdapter
import ru.dkotsur.calculator.viewmodel.EditSessionViewModel

class EditSessionUsersFragment : Fragment() {

    private lateinit var sheetBehavior: BottomSheetBehavior<View>
    private lateinit var bottomSheet: LinearLayout
    private lateinit var editTextUserName: EditText
    private lateinit var fabAddPerson: FloatingActionButton
    private lateinit var imm: InputMethodManager
    private lateinit var personsAdapter: PersonsAdapter


    companion object {
        fun newInstance() = EditSessionUsersFragment()
    }

    private lateinit var viewModel: EditSessionViewModel

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        Log.e("FR", "VIEW CREATED")

        val root = inflater.inflate(R.layout.edit_sessions_users_fragment, container, false)
        initBottomSheet(root)

        fabAddPerson = root.findViewById(R.id.fab_add_user)
        fabAddPerson.setOnClickListener{
            sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        editTextUserName.setOnEditorActionListener { v, actionId, event ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.saveNewPerson(editTextUserName.text)
                closeKeyboard()
                closeBottomsheet()
                handled = true
            }
            handled
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.e("FR", "CREATED")
        viewModel = activity!!.run {
            ViewModelProviders.of(this).get(EditSessionViewModel::class.java)
        }
        imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        personsAdapter = PersonsAdapter()
        initRecyclerView()
    }

    private fun initBottomSheet(root: View) {
        bottomSheet = root.findViewById(R.id.bottom_sheet)
        var bottomSheetTitle = root.findViewById<TextView>(R.id.bottom_sheet_title_tw)
        bottomSheetTitle.setText(R.string.add_person_bsh_title)
        editTextUserName = root.findViewById(R.id.edit_text_session)
        sheetBehavior = BottomSheetBehavior.from(bottomSheet)
        sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun closeBottomsheet() {
        Handler().postDelayed({
            sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            editTextUserName.text.clear()
        }, 500)
    }

    private fun closeKeyboard() {
        val view = activity!!.currentFocus
        if (view != null) {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun initRecyclerView() {
        viewModel.getAllPersonsInSession().observe(this, Observer {
            it?.let(personsAdapter::submitList)
            Log.e("init recycler view", "Observer calls!")
            if (rv_persons.adapter!!.itemCount > 0) {
                rv_persons.smoothScrollToPosition(rv_persons.adapter!!.itemCount)
            }
        })

        rv_persons.apply {
            layoutManager = LinearLayoutManager(this.context).apply {
                stackFromEnd = true
                reverseLayout = true
            }
            adapter = personsAdapter
            setHasFixedSize(true)
        }
    }

}
