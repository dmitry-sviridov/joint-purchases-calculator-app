package ru.dkotsur.calculator.view.event

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_edit_session.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fr_edit_users_in_session.*
import ru.dkotsur.calculator.R
import ru.dkotsur.calculator.data.db.entity.Person
import ru.dkotsur.calculator.view.event.adapter.PersonsAdapter
import ru.dkotsur.calculator.view.event.adapter.TabsPagerAdapter
import ru.dkotsur.calculator.viewmodel.EditSessionViewModel
import ru.dkotsur.calculator.viewmodel.factory.EditSessionViewModelFactory


class EditSessionActivity : AppCompatActivity() {

    companion object {
        val EXTRA_ID = "ru.dkotsur.calculator.EDITID"
        val EXTRA_TITLE = "ru.dkotsur.calculator.EDITTITLE"
    }

    private var sessionId: Long = 0
    private lateinit var viewModel: EditSessionViewModel
    lateinit var sessionTitle: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_session)
        sessionId = intent.getLongExtra(EXTRA_ID, 0)
        sessionTitle = intent.getStringExtra(EXTRA_TITLE)

        var editSessionViewModelFactory =
            EditSessionViewModelFactory(sessionId)
        viewModel = ViewModelProviders.of(this, editSessionViewModelFactory).get(EditSessionViewModel::class.java)

        edit_activity_title.text = """${getString(R.string.edit_event)} '$sessionTitle'"""

        val viewPager = findViewById<ViewPager>(R.id.edit_session_pager)
        val myPagerAdapter = TabsPagerAdapter(supportFragmentManager, this)
        viewPager.adapter = myPagerAdapter
        val tabLayout = findViewById<TabLayout>(R.id.edit_session_tl)
        tabLayout.setupWithViewPager(viewPager)

    }


}
