package ua.kpi.comsys.IP8415

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.textfield.TextInputEditText

class AddBookFragment : Fragment(R.layout.fragment_add_book) {
    private val model: SharedViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_add_book, container, false) as ConstraintLayout
        setAddBookClickListener(root)
        return root
    }

    private fun setAddBookClickListener(root: View) {
        val addBookButton = root.findViewById<Button>(R.id.add_book_button)
        addBookButton.setOnClickListener {
            val priceEditText = root.findViewById<EditText>(R.id.new_book_price)
            val price = priceEditText.text.toString()
            val regEx = Regex("[0-9]+\\.[0-9][0-9]")
            if (!price.matches(regEx)) {
                priceEditText.error = "Wrong value"
                return@setOnClickListener
            }
            val title = root.findViewById<TextInputEditText>(R.id.new_book_title).text.toString()
            val subtitle = root.findViewById<TextInputEditText>(R.id.new_book_subtitle).text.toString()
            val book = Book(title, subtitle, "", "$$price", "")
            model.bookAdapter.value?.addBook(book)
            parentFragmentManager.popBackStack()
        }
    }
}