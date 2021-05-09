package com.closer.test.presentation.main.postalcodes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.closer.test.databinding.PostalCodeRowBinding
import com.closer.test.util.model.PostalCode


class PostalCodeAdapter: RecyclerView.Adapter<PostalCodeHolder>() {

    private val items = mutableListOf<PostalCode>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostalCodeHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = PostalCodeRowBinding.inflate(layoutInflater, parent, false)

        return PostalCodeHolder(binding)
    }

    override fun onBindViewHolder(holder: PostalCodeHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun refreshData(items: List<PostalCode>) {
        this.items.clear()
        this.items.addAll(items)

        notifyDataSetChanged()
    }
}
