package ua.kpi.comsys.IP8415.Fragments

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
import ua.kpi.comsys.IP8415.*

class ExtendedBookInfoFragment : Fragment(R.layout.fragment_extended_book_info) {
    private val model: SharedViewModel by activityViewModels()
    private lateinit var root: ConstraintLayout
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        root = inflater.inflate(R.layout.fragment_extended_book_info, container, false) as ConstraintLayout
        model.extendedBook.value?.let { setBookProperties(it) }
        return root
    }

    private fun setBookProperties(book: ExtendedBookServerResponse) {

        root.findViewById<TextView>(R.id.title_extended).text = "Title: ${book.title}"
        root.findViewById<TextView>(R.id.subtitle_extended).text = "Subtitle: ${book.subtitle}"
        root.findViewById<TextView>(R.id.description_extended).text = "Description: ${book.desc}"
        root.findViewById<TextView>(R.id.authors_extended).text = "Authors: ${book.authors}"
        root.findViewById<TextView>(R.id.publisher_extended).text = "Publisher: ${book.publisher}"
        root.findViewById<TextView>(R.id.pages_extended).text = "Pages: ${book.pages}"
        root.findViewById<TextView>(R.id.year_extended).text = "Year: ${book.year}"
        root.findViewById<TextView>(R.id.rating_extended).text = "Rating: ${book.rating} / 5"
        val imageLoader = ImageLoader()
        imageLoader.getImage(book.image) {
            root.findViewById<ImageView>(R.id.image_extended).setImageBitmap(it)
        }
    }
}