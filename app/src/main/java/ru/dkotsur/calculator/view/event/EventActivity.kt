package ru.dkotsur.calculator.view.event

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_sessions.*
import ru.dkotsur.calculator.R
import ru.dkotsur.calculator.view.event.adapter.TabsPagerAdapter
import ru.dkotsur.calculator.view.result.CalculationActivity
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

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.edit_session_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        var editSessionViewModelFactory =
            EventViewModelFactory(sessionId)
        viewModel = ViewModelProviders.of(this,
            editSessionViewModelFactory).get(EventViewModel::class.java)

        edit_activity_title.text = """${getString(R.string.edit_event)} '$sessionTitle'"""

        val viewPager = findViewById<ViewPager>(R.id.edit_session_pager)
        val myPagerAdapter = TabsPagerAdapter(supportFragmentManager, this)
        viewPager.adapter = myPagerAdapter
        val tabLayout = findViewById<TabLayout>(R.id.edit_session_tl)
        tabLayout.setupWithViewPager(viewPager)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.calculate_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.calculate_btn -> {
                runResultActivity()
                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }

    fun runResultActivity() {
        val intent = Intent(this@EventActivity, CalculationActivity::class.java)
        intent.putExtra(CalculationActivity.EXTRA_SESSION_ID, sessionId)
        startActivity(intent)
    }

}
