package ua.kpi.comsys.IP8415

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookViewHolder(itemView: View, private val ctx : Context) : RecyclerView.ViewHolder(itemView) {
    private val bookImage = itemView.findViewById<ImageView>(R.id.bookImage)
    private val bookTitle = itemView.findViewById<TextView>(R.id.bookTitle)
    private val bookSubtitle = itemView.findViewById<TextView>(R.id.bookSubtitle)
    private val bookPrice = itemView.findViewById<TextView>(R.id.bookPrice)
    private val bookIsbn13 = itemView.findViewById<TextView>(R.id.bookIsbn13)

    fun bind(book : Book) {
        bookImage.setImageBitmap(book.getImageBitmap(ctx))
        bookTitle.text = book.title
        bookSubtitle.text = book.subtitle
        bookPrice.text = book.price
        bookIsbn13.text = "ISBN: ${book.isbn13}"
    }
}

class BookAdapter(var bookList : ArrayList<Book>, private val ctx : Context) : RecyclerView.Adapter<BookViewHolder>() {
    fun setItems(bookList : ArrayList<Book>) {
        this.bookList = bookList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BookViewHolder(itemView, ctx)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(bookList[position])
    }

    override fun getItemCount(): Int {
        return bookList.size
    }
}