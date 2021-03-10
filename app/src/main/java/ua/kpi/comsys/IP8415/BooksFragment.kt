package ua.kpi.comsys.IP8415

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BooksFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_books, container, false) as ConstraintLayout
        val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        val bookAdapter = this.context?.let { BookAdapter(ArrayList(), it) }
        val books = BooksLoader("BooksList.txt", root.context).getBooks()
        bookAdapter?.setItems(books)
        recyclerView.adapter = bookAdapter
        return root
    }
}