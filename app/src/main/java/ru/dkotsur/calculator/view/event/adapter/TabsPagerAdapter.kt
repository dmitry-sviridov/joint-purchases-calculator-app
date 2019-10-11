package ru.dkotsur.calculator.view.event.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import ru.dkotsur.calculator.R
import ru.dkotsur.calculator.view.event.EditSessionItemsFragment
import ru.dkotsur.calculator.view.event.EditSessionUsersFragment



class TabsPagerAdapter(fragmentManager: FragmentManager, private var context: Context): FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> EditSessionUsersFragment()
            else -> EditSessionItemsFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> context.getString(R.string.tab_title_users)
            else -> context.getString(R.string.tab_title_items)
        }
    }

}

