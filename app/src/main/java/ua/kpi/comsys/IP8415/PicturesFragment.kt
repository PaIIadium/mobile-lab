package ua.kpi.comsys.IP8415

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.arasthel.spannedgridlayoutmanager.SpanSize
import com.arasthel.spannedgridlayoutmanager.SpannedGridLayoutManager

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
        }
        recyclerView.adapter = model.imageAdapter.value

        setPlusButtonClickListener(root)
        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            model.imageAdapter.value?.addImage(data?.data)
        }
    }

    private fun createImageAdapter() {
        model.setImageAdapter(context?.let { ImageAdapter(ArrayList()) })
    }

    private fun setPlusButtonClickListener(root: View) {
        root.findViewById<ImageButton>(R.id.plus_button).setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }
    }
}