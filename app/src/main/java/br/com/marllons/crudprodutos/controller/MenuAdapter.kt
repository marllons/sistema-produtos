package br.com.marllons.crudprodutos.controller

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import br.com.marllons.crudprodutos.model.Menu

/**
 *  Created by Marllon S on 3/2/2021. All rights reserved.
 */

class MenuAdapter(
    private val callback: (Menu, Int) -> Unit
) : ListAdapter<Menu, MenuViewHolder>(DIFF_CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        return MenuViewHolder.inflate(parent, callback)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Menu>() {
            override fun areItemsTheSame(oldItem: Menu, newItem: Menu) = oldItem.label == newItem.label
            override fun areContentsTheSame(oldItem: Menu, newItem: Menu) = newItem == oldItem
        }
    }
}

