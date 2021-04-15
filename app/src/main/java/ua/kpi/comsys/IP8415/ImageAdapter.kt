package ua.kpi.comsys.IP8415

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class ImageAdapter(val imageList: ArrayList<Uri>) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>(){

    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView = view as ImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val imageLayout = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(imageLayout)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {

        val params = GridLayout.LayoutParams()
        params.rowSpec = GridLayout.spec(0, 2, null, 1f)
        params.columnSpec = GridLayout.spec(0, 2, null, 1f)

        holder.imageView.layoutParams = params
        holder.imageView.setImageURI(imageList[position])
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    fun addImage(image: Uri?) {
        if (image == null) return
        imageList.add(image)
        notifyDataSetChanged()
    }
}