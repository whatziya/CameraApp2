package com.example.cameraapp

import android.annotation.SuppressLint
import android.net.Uri
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnCreateContextMenuListener
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.cameraapp.databinding.ItemPhotoBinding
import com.example.cameraapp.model.entity.CamData
import java.io.File

class GalleryAdapter : RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>(){
    private val list = mutableListOf<CamData>()
    inner class GalleryViewHolder(private val itemBinding: ItemPhotoBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(entity: CamData) {
            with(itemBinding) {
                image.load(entity.uri.toUri())
            }
        }

    }
    @SuppressLint("NotifyDataSetChanged")
    fun load(list : List<CamData>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun add(entity: CamData){
        list.add(entity)
        notifyItemInserted(list.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPhotoBinding.inflate(inflater, parent, false)
        return GalleryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.bind(list[position])
    }

}