package br.com.marllons.crudprodutos.controller

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import br.com.marllons.crudprodutos.model.Produto

/**
 *  Created by Marllon S on 3/9/2021. All rights reserved.
 */

class ProdutosAdapter(private val callback: (Produto, Int) -> Unit
) : ListAdapter<Produto, ProdutoViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdutoViewHolder {
        return ProdutoViewHolder.inflate(parent, callback)
    }

    override fun onBindViewHolder(holder: ProdutoViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Produto>() {
            override fun areItemsTheSame(oldItem: Produto, newItem: Produto) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Produto, newItem: Produto) = newItem == oldItem
        }
    }
}