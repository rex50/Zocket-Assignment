package com.rex50.zocketassignment.ui.page

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rex50.zocketassignment.databinding.ItemDetailBinding

class PageDetailsAdapter(
    private val details: ArrayList<Pair<String, String>> = arrayListOf()
): RecyclerView.Adapter<PageDetailsAdapter.PageDetailViewHolder>() {

    inner class PageDetailViewHolder(val binding: ItemDetailBinding): RecyclerView.ViewHolder(binding.root)

    fun update(data: List<Pair<String, String>>) {
        details.clear()
        details.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageDetailViewHolder {
        return PageDetailViewHolder(
            ItemDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PageDetailViewHolder, position: Int) {
        val (title, value) = details[position]
        holder.binding.tvTitle.text = title
        holder.binding.tvValue.text = value

    }

    override fun getItemCount(): Int {
        return details.size
    }

}