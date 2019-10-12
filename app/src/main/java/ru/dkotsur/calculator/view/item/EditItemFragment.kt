package ru.dkotsur.calculator.view.item


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.dkotsur.calculator.R

/**
 * A simple [Fragment] subclass.
 */
class EditItemFragment : Fragment() {

    companion object {
        val TAG = javaClass.simpleName
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fr_edit_item, container, false)
        return root
    }

}



