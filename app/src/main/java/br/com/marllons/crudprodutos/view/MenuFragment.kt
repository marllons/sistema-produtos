package br.com.marllons.crudprodutos.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.marllons.crudprodutos.R
import br.com.marllons.crudprodutos.controller.MenuAdapter
import br.com.marllons.crudprodutos.databinding.FragmentMenuBinding
import br.com.marllons.crudprodutos.model.Menu
import br.com.marllons.crudprodutos.utils.BackStackNavigator
import br.com.marllons.crudprodutos.utils.NavigationListener


/**
 *  Created by Marllon S on 3/2/2021. All rights reserved.
 */

class MenuFragment : Fragment() {
    var binding : FragmentMenuBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuList = Menu.buildMenu()
        var adapter = MenuAdapter(::call)
        adapter.submitList(menuList)
        binding?.recyclerMenu?.adapter = adapter
    }

    private fun call(menu: Menu, position: Int){
        when(position) {
            0 -> NavigationListener.navigateTo(ProductFragment(), "Produtos", true)
            else -> Toast.makeText(activity, "Implementação em versões futuras", Toast.LENGTH_SHORT).show()
        }

    }
}