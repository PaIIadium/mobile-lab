package ua.kpi.comsys.IP8415.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import ua.kpi.comsys.IP8415.*

class BooksFragment : Fragment(R.layout.fragment_books) {
    private val model: SharedViewModel by activityViewModels()
    private lateinit var noItemsFoundText: TextView
    private lateinit var recyclerViewFragment: FragmentContainerView
    private var lastText = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        retainInstance = true
        val root = inflater.inflate(R.layout.fragment_books, container, false) as ConstraintLayout
        recyclerViewFragment = root.findViewById(R.id.recycler_view_fragment)
        noItemsFoundText = root.findViewById(R.id.no_items_found)
        if (model.bookAdapter.value != null) {
            setBookAdapter(model.bookAdapter.value!!.bookList)
            noItemsFoundText.isVisible = false
        } else {
            noItemsFoundText.isVisible = true
        }
        setPlusButtonClickListener(root)
        setSearchViewListener(root)
        return root
    }

    override fun onDestroy() {
        model.setBookAdapter(null)
        super.onDestroy()
    }

    private fun createBookAdapter(books: ArrayList<Book>) {
        val clickListener = BookAdapter.BookClickListener { isbn13: String ->
            activity?.supportFragmentManager?.commit {
                model.onBookClicked(isbn13)
                replace(R.id.fragment_container, ExtendedBookScreenFragment())
                addToBackStack(null)
            }
        }
        model.setBookAdapter(context?.let { BookAdapter(books, clickListener) })
    }

    private fun setPlusButtonClickListener(root: View) {
        val addBookButton = root.findViewById<ImageButton>(R.id.plus_button)
        addBookButton.setOnClickListener {
            parentFragmentManager.commit {
                replace(R.id.fragment_container, AddBookFragment())
                addToBackStack(null)
            }
        }
    }

    private fun setSearchViewListener(root: View) {
        val searchView = root.findViewById<SearchView>(R.id.search_book)
        val queryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                val newTextTrimmed = newText?.trim()
                if (newTextTrimmed != null && newTextTrimmed.length > 2 && newTextTrimmed != lastText) {
                    val regex = Regex("[^A-z0-9 ]+")
                    if (newTextTrimmed.contains(regex)) return true
                    activity?.supportFragmentManager?.commit {
                        replace(R.id.recycler_view_fragment, ProgressBarFragment())
                    }
                    model.database.value?.let { db ->
                        BooksLoader(newTextTrimmed, db) {
                            noItemsFoundText.isVisible = it.isEmpty() == true
                            setBookAdapter(it as ArrayList<Book>)
                        }
                    }
                    lastText = newTextTrimmed
                } else if (newTextTrimmed != null && newTextTrimmed != lastText) {
                    setBookAdapter(ArrayList())
                    lastText = newTextTrimmed
                    noItemsFoundText.isVisible = true
                }
                return true
            }
        }
        searchView.setOnQueryTextListener(queryTextListener)
    }

    private fun setBookAdapter(books: ArrayList<Book>) {
        createBookAdapter(books)
        activity?.supportFragmentManager?.commit {
                replace(R.id.recycler_view_fragment, BooksRecyclerViewFragment())
        }
    }
}