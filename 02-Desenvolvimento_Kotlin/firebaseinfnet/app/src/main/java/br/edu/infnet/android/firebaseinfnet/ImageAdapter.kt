package br.edu.infnet.android.firebaseinfnet

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

typealias onItemClick = (filename: String)->Unit
class ImageAdapter(
    val urls: List<Map<String,String>>,
    val context: Context,
    val funcaoClick: (Map<String, String>) -> Unit
): RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
    inner class ImageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.fragment_image_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return urls.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val url = urls[position]["url"]
        Glide.with(holder.itemView).load(url).into(holder.itemView.findViewById(R.id.ivImage))
        holder.itemView.setOnClickListener {
            Toast.makeText(context, "Cliquei em ${urls[position]}", Toast.LENGTH_LONG).show()
            funcaoClick(urls[position])
        }

    }
}