package ua.kpi.comsys.IP8415

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
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SharedViewModel : ViewModel() {
    val bookAdapter = MutableLiveData<BookAdapter?>()
    val isbn13 = MutableLiveData<String>()
    val parentFragmentManager = MutableLiveData<FragmentManager?>()

    fun setBookAdapter(newAdapter: BookAdapter?) {
        bookAdapter.value = newAdapter
    }

    fun onBookClicked(newIsbn13 : String) {
        isbn13.value = newIsbn13
    }

    fun setFragmentManager(fragmentManager: FragmentManager) {
        parentFragmentManager.value = fragmentManager
    }
}

class BooksFragment : Fragment(R.layout.fragment_books) {
    private val model: SharedViewModel by activityViewModels()
    private lateinit var noItemsFoundText: TextView
    var booksList : ArrayList<Book>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        retainInstance = true
        if (model.parentFragmentManager.value == null) model.setFragmentManager(parentFragmentManager)
        val root = inflater.inflate(R.layout.fragment_books, container, false) as ConstraintLayout
        val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        if (model.bookAdapter.value == null) {
            createBookAdapter(root)
        }
        recyclerView.adapter = model.bookAdapter.value
        booksList = model.bookAdapter.value?.bookList
        noItemsFoundText = root.findViewById(R.id.no_items_found)
        noItemsFoundText.isVisible = false
        setSwipeCallback(recyclerView)
        setPlusButtonClickListener(root)
        setSearchViewListener(root)
        return root
    }

    private fun setSwipeCallback(recyclerView: RecyclerView?) {
        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = recyclerView?.adapter as BookAdapter
                val position = viewHolder.adapterPosition
                val item = adapter.bookList[position]
                adapter.removeBook(position)
                booksList?.remove(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    fun createBookAdapter(root: View) {
        val books = BooksLoader("BooksList.txt", root.context).getBooks()
        val clickListener = BookAdapter.BookClickListener { isbn13: String ->
            activity?.supportFragmentManager?.commit {
                model.onBookClicked(isbn13)
                replace(R.id.fragment_container, ExtendedInformationFragment())
                addToBackStack(null)
        } }
        model.setBookAdapter(context?.let { BookAdapter(books, it, clickListener) })
    }

    fun setPlusButtonClickListener(root: View) {
        val addBookButton = root.findViewById<ImageButton>(R.id.plus_button)
        addBookButton.setOnClickListener {
            parentFragmentManager.commit {
                replace(R.id.fragment_container, AddBookFragment())
                addToBackStack(null)
            }
        }
    }

    fun setSearchViewListener(root: View) {
        val searchView = root.findViewById<SearchView>(R.id.searchBook)
        val queryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (booksList?.isEmpty() == true) return true
                if (newText != null) {
                    val filteredBookList = booksList?.filter { book -> book.title.contains(newText) } as ArrayList<Book>
                    model.bookAdapter.value?.setItems(filteredBookList)
                    model.bookAdapter.value?.notifyDataSetChanged()
                    noItemsFoundText.isVisible = filteredBookList.size == 0
                }
                return true
            }
        }
        searchView.setOnQueryTextListener(queryTextListener)
    }
}