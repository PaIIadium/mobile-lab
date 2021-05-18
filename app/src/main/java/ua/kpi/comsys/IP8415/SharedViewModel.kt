package ua.kpi.comsys.IP8415

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ua.kpi.comsys.IP8415.Adapters.ImageAdapter
import ua.kpi.comsys.IP8415.Database.Database

class SharedViewModel : ViewModel() {
    val bookAdapter = MutableLiveData<BookAdapter?>()
    val imageAdapter = MutableLiveData<ImageAdapter?>()
    val isbn13 = MutableLiveData<String>()
    val extendedBook = MutableLiveData<ExtendedBookServerResponse>()
    val database = MutableLiveData<Database>()

    fun setBookAdapter(newAdapter: BookAdapter?) {
        bookAdapter.postValue(newAdapter)
    }

    fun setImageAdapter(newAdapter: ImageAdapter?) {
        imageAdapter.value = newAdapter
    }

    fun onBookClicked(newIsbn13: String) {
        isbn13.value = newIsbn13
    }

    fun setExtendedBook(newExtendedBook: ExtendedBookServerResponse) {
        extendedBook.value = newExtendedBook
    }

    fun setDatabase(newDb: Database) {
        database.value = newDb
    }
}