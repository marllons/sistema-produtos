package br.com.marllons.crudprodutos.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.marllons.crudprodutos.databinding.ItemProdutoBinding
import br.com.marllons.crudprodutos.model.Produto

/**
 *  Created by Marllon S on 3/9/2021. All rights reserved.
 */

class ProdutoViewHolder(private val binding: ItemProdutoBinding,
                        private val callback: (Produto, Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(prod: Produto, position: Int) {
        binding.run {
            tvProduto.text = prod.nome
            tvPreco.text = prod.precoEntrada
            card.setOnClickListener {
                callback.invoke(prod, position)
            }
        }
    }

    companion object {
        fun inflate(parent: ViewGroup, callback: (Produto, Int) -> Unit): ProdutoViewHolder {
            return ProdutoViewHolder(
                ItemProdutoBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                callback
            )
        }
    }
}