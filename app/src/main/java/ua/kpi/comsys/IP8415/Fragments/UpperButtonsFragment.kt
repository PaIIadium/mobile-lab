package ua.kpi.comsys.IP8415.Fragments

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import ua.kpi.comsys.IP8415.R

class UpperButtonsFragment : Fragment(R.layout.fragment_upper_buttons) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val graphicButton = view.findViewById<RadioButton>(R.id.graphic_button)
        graphicButton?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                activity?.supportFragmentManager?.commit {
                    replace(R.id.fragment_container, GraphicFragment())
                }
            } else {
                activity?.supportFragmentManager?.commit {
                    replace(R.id.fragment_container, DiagramFragment())
                }
            }
        }
    }

}