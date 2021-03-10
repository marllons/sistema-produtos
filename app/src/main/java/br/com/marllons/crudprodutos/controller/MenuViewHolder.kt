package br.com.marllons.crudprodutos.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.marllons.crudprodutos.databinding.ItemMenuBinding
import br.com.marllons.crudprodutos.model.Menu

/**
 *  Created by Marllon S on 3/2/2021. All rights reserved.
 */
class MenuViewHolder(
    private val binding: ItemMenuBinding,
    private val callback: (Menu, Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(menu: Menu, position: Int){
        binding.run {
            label.text = menu.label
            img.setImageResource(menu.image)
            card.setOnClickListener {
                callback.invoke(menu, position)
            }
        }
    }

    companion object {
        fun inflate(parent: ViewGroup, callback: (Menu, Int) -> Unit): MenuViewHolder {
            return MenuViewHolder(ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false), callback
            )
        }
    }

}