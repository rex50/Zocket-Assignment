package com.rex50.zocketassignment.ui.pages

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rex50.zocketassignment.data.models.PageData
import com.rex50.zocketassignment.databinding.ItemPageBinding
import com.rex50.zocketassignment.utils.extensions.loadImage

class PageListAdapter(
    private var pageData: ArrayList<PageData> = arrayListOf()
): RecyclerView.Adapter<PageListAdapter.PageViewHolder>() {

    var onPageClicked: ((PageData) -> Unit)? = null

    fun updatePages(list: List<PageData>) {
        pageData.clear()
        pageData.addAll(list)
        // TODO: Implement DiffUtil for better performance
        notifyDataSetChanged()
    }

    inner class PageViewHolder(val binding: ItemPageBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        return PageViewHolder(
            ItemPageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        val page = pageData[position]

        // Bind view
        holder.binding.tvPageName.text = page.name
        holder.binding.tvCategory.text = page.category
        val followers = "Followers: ${page.followers_count}"
        holder.binding.tvFollowers.text = followers
        holder.binding.ivPicture.loadImage(page.picture.data.url, animate = true)
        holder.binding.cardPage.setOnClickListener {
            onPageClicked?.invoke(page)
        }
    }

    override fun getItemCount(): Int {
        return pageData.size
    }
}