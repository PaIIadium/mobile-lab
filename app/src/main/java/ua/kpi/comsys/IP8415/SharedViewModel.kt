package ua.kpi.comsys.IP8415

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    val bookAdapter = MutableLiveData<BookAdapter?>()
    val imageAdapter = MutableLiveData<ImageAdapter?>()
    val isbn13 = MutableLiveData<String>()
    val parentFragmentManager = MutableLiveData<FragmentManager?>()

    fun setBookAdapter(newAdapter: BookAdapter?) {
        bookAdapter.value = newAdapter
    }

    fun setImageAdapter(newAdapter: ImageAdapter?) {
        imageAdapter.value = newAdapter
    }

    fun onBookClicked(newIsbn13: String) {
        isbn13.value = newIsbn13
    }

    fun setFragmentManager(fragmentManager: FragmentManager) {
        parentFragmentManager.value = fragmentManager
    }
}