package ua.kpi.comsys.IP8415

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val bookImage = itemView.findViewById<ImageView>(R.id.book_image)
    private val bookTitle = itemView.findViewById<TextView>(R.id.book_title)
    private val bookSubtitle = itemView.findViewById<TextView>(R.id.book_subtitle)
    private val bookPrice = itemView.findViewById<TextView>(R.id.book_price)
    private val bookIsbn13 = itemView.findViewById<TextView>(R.id.book_isbn13)

    fun bind(book : Book, ctx: Context) {
        book.getImageBitmap(this, ctx)
        bookTitle.text = book.title
        bookSubtitle.text = book.subtitle
        bookPrice.text = book.price
        bookIsbn13.text = "ISBN: ${book.isbn13}"
    }
}

class BookAdapter(var bookList: ArrayList<Book>, private val clickListener: BookClickListener, val ctx: Context)
    : RecyclerView.Adapter<BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BookViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = bookList[position]
        holder.bind(book, ctx)
        holder.itemView.setOnClickListener {
            clickListener.onClick(book.isbn13)
        }
    }

    class BookClickListener(val clickListener: (isbn13: String) -> Unit) {
        fun onClick(isbn13: String) = clickListener(isbn13)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    fun addBook(book: Book) {
        bookList.add(book)
        notifyItemInserted(itemCount - 1)
    }

    fun removeBook(position: Int) {
        bookList.removeAt(position)
        notifyItemRemoved(position)
    }
}