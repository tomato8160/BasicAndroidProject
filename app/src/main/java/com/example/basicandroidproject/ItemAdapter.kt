package com.example.basicandroidproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.basicandroidproject.databinding.ItemBinding

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    private var items = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.number.text = (position+1).toString()
        holder.contents.text = items[position]
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateAdapter(content : String){
        items.add(0, content)
        notifyDataSetChanged()
    }

    inner class ViewHolder(binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val number = binding.number
        val contents = binding.contents
    }

}
