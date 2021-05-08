package ua.kpi.comsys.IP8415.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import ua.kpi.comsys.IP8415.*

class ExtendedBookScreenFragment : Fragment(R.layout.fragment_extended_information) {
    private val model: SharedViewModel by activityViewModels()
    private lateinit var root: ConstraintLayout
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        root = inflater.inflate(R.layout.fragment_extended_information, container, false) as ConstraintLayout
        activity?.supportFragmentManager?.commit {
            replace(R.id.fragment_extended_book_info, ProgressBarFragment())
        }
        setBackButtonListener(root)
        model.isbn13.value?.let { isbn13 -> ExtendedBookLoader(isbn13) { loadBookInfo(it) } }
        return root
    }

    fun loadBookInfo(book: ExtendedBookServerResponse) {
        activity?.supportFragmentManager?.commit {
            replace(R.id.fragment_extended_book_info, ExtendedBookInfoFragment())
        }
        model.setExtendedBook(book)
    }

    private fun setBackButtonListener(root : ConstraintLayout) {
        val backButton = root.findViewById<ImageButton>(R.id.back_button)
        backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}