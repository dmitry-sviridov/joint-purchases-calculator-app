package ru.dkotsur.calculator.view.event

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_sessions.*
import ru.dkotsur.calculator.R
import ru.dkotsur.calculator.view.event.adapter.TabsPagerAdapter
import ru.dkotsur.calculator.viewmodel.EventViewModel
import ru.dkotsur.calculator.viewmodel.factory.EventViewModelFactory


class EventActivity : AppCompatActivity() {

    companion object {
        val EXTRA_ID = "ru.dkotsur.calculator.EDITID"
        val EXTRA_TITLE = "ru.dkotsur.calculator.EDITTITLE"
    }

    private var sessionId: Long = 0
    private lateinit var viewModel: EventViewModel
    lateinit var sessionTitle: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sessions)
        sessionId = intent.getLongExtra(EXTRA_ID, 0)
        sessionTitle = intent.getStringExtra(EXTRA_TITLE)

        var editSessionViewModelFactory =
            EventViewModelFactory(sessionId)
        viewModel = ViewModelProviders.of(this, editSessionViewModelFactory).get(EventViewModel::class.java)

        edit_activity_title.text = """${getString(R.string.edit_event)} '$sessionTitle'"""

        val viewPager = findViewById<ViewPager>(R.id.edit_session_pager)
        val myPagerAdapter = TabsPagerAdapter(supportFragmentManager, this)
        viewPager.adapter = myPagerAdapter
        val tabLayout = findViewById<TabLayout>(R.id.edit_session_tl)
        tabLayout.setupWithViewPager(viewPager)

    }


}
