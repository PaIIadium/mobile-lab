package ua.kpi.comsys.IP8415.Adapters

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import ua.kpi.comsys.IP8415.R

class ImageAdapter(val imageList: ArrayList<Bitmap?>) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>(){

    class ImageViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val imageView = view.findViewById(R.id.image_view) as ImageView
        val progressBar = view.findViewById(R.id.image_progress_bar) as ProgressBar
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val imageLayout = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(imageLayout)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {

        val params = GridLayout.LayoutParams(ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT))
        params.rowSpec = GridLayout.spec(0, 1, null, 1f)
        params.columnSpec = GridLayout.spec(0, 1, null, 1f)

        holder.view.layoutParams = params
        if (imageList[position] != null) holder.progressBar.visibility = INVISIBLE
        holder.imageView.setImageBitmap(imageList[position])
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    fun addImage(image: Bitmap?) {
        imageList.add(image)
        notifyDataSetChanged()
    }
}