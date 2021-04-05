package ua.kpi.comsys.IP8415

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

class ExtendedInformationFragment : Fragment(R.layout.fragment_extended_information) {
    private val model: SharedViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_extended_information, container, false) as ConstraintLayout
        val book = model.isbn13.value?.let { ExtendedBookLoader(it, root.context).getBook() }
        if (book != null) {
            setBookProperties(root, book)
        } else {
            parentFragmentManager.popBackStack()
        }
        setBackButtonListener(root)
        return root
    }

    private fun setBookProperties(root : ConstraintLayout, book: ExtendedBook) {
        root.findViewById<TextView>(R.id.title_extended).text = "Title: ${book.title}"
        root.findViewById<TextView>(R.id.subtitle_extended).text = "Subtitle: ${book.subtitle}"
        root.findViewById<TextView>(R.id.description_extended).text = "Description: ${book.desc}"
        root.findViewById<TextView>(R.id.authors_extended).text = "Authors: ${book.authors}"
        root.findViewById<TextView>(R.id.publisher_extended).text = "Publisher: ${book.publisher}"
        root.findViewById<TextView>(R.id.pages_extended).text = "Pages: ${book.pages}"
        root.findViewById<TextView>(R.id.year_extended).text = "Year: ${book.year}"
        root.findViewById<TextView>(R.id.rating_extended).text = "Rating: ${book.rating} / 5"
        root.findViewById<ImageView>(R.id.image_extended).setImageBitmap(book.getImageBitmap(root.context))
    }

    private fun setBackButtonListener(root : ConstraintLayout) {
        val backButton = root.findViewById<ImageButton>(R.id.back_button)
        backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}