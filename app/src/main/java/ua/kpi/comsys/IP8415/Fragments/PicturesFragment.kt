package ua.kpi.comsys.IP8415.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.arasthel.spannedgridlayoutmanager.SpanSize
import com.arasthel.spannedgridlayoutmanager.SpannedGridLayoutManager
import ua.kpi.comsys.IP8415.*
import ua.kpi.comsys.IP8415.Adapters.ImageAdapter

class PicturesFragment: Fragment(R.layout.fragment_images) {
    private val model: SharedViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        retainInstance = true
        val root = inflater.inflate(R.layout.fragment_images, container, false)
        val recyclerView = root.findViewById<RecyclerView>(R.id.images_recycler_view)

        val spannedGridLayoutManager = SpannedGridLayoutManager(
            orientation = SpannedGridLayoutManager.Orientation.VERTICAL,
            spans = 3)
        spannedGridLayoutManager.itemOrderIsStable = true
        spannedGridLayoutManager.spanSizeLookup = SpannedGridLayoutManager.SpanSizeLookup { position ->
            when (position % 9) {
                4 -> SpanSize(2,2)
                else -> SpanSize(1, 1)
            }
        }

        recyclerView.layoutManager = spannedGridLayoutManager
        if (model.imageAdapter.value == null) {
            createImageAdapter()
            recyclerView.adapter = model.imageAdapter.value
            fillLoadingImages()
            ImagesURLLoader("night+city", 27) {imagesURL ->
                for (i in imagesURL.indices) {
                    ImageLoader().getImage(imagesURL[i].webformatURL) { image ->
                        model.imageAdapter.value?.imageList?.set(i, image)
                        model.imageAdapter.value?.notifyDataSetChanged()
                    }
                }
            }
        } else {
            recyclerView.adapter = model.imageAdapter.value
        }
        return root
    }

    private fun fillLoadingImages() {
        repeat(27) {
            model.imageAdapter.value?.addImage(null)
        }
    }

    private fun createImageAdapter() {
        model.setImageAdapter(context?.let { ImageAdapter(ArrayList()) })
    }
}