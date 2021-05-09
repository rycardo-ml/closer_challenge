package com.closer.test.presentation.postalcodes.adapter

import androidx.recyclerview.widget.RecyclerView
import com.closer.test.databinding.PostalCodeRowBinding
import com.closer.test.util.model.PostalCode

class PostalCodeHolder(val binding: PostalCodeRowBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: PostalCode) {
        binding.tvCode.text = "${item.numeroCodigoPostal}-${item.extCodigoPostal}"
        binding.tvName.text = item.designacaoPostal
    }
}
