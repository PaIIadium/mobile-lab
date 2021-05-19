package ua.kpi.comsys.IP8415.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ua.kpi.comsys.IP8415.BookAdapter
import ua.kpi.comsys.IP8415.R
import ua.kpi.comsys.IP8415.SharedViewModel
import ua.kpi.comsys.IP8415.SwipeToDeleteCallback

class BooksRecyclerViewFragment : Fragment(R.layout.fragment_recycler_view) {
    private val model: SharedViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        val root = inflater.inflate(R.layout.fragment_recycler_view, container, false) as ConstraintLayout
        val recyclerView = root.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        val bookList = model.bookAdapter.value?.bookList
        val clickListener = BookAdapter.BookClickListener { isbn13: String ->
            activity?.supportFragmentManager?.commit {
                model.onBookClicked(isbn13)
                replace(R.id.fragment_container, ExtendedBookScreenFragment())
                addToBackStack(null)
            }
        }
        val adapter = bookList?.let { BookAdapter(it, clickListener, requireContext()) }
        recyclerView.adapter = adapter
        setSwipeCallback(recyclerView)
        return root
    }

    private fun setSwipeCallback(recyclerView: RecyclerView?) {
        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = recyclerView?.adapter as BookAdapter
                val position = viewHolder.adapterPosition
                adapter.removeBook(position)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}