package ru.dkotsur.calculator.view.event

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.dkotsur.calculator.R

class EditSessionActivity : AppCompatActivity() {

    companion object {
        val EXTRA_ID = "ru.dkotsur.calculator.EDITID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_session_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container,
                    EditSessionFragment.newInstance()
                )
                .commitNow()
        }
    }

}
