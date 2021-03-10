package br.com.marllons.crudprodutos.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.marllons.crudprodutos.controller.ProdutosAdapter
import br.com.marllons.crudprodutos.databinding.FragmentProductBinding
import br.com.marllons.crudprodutos.model.ProductSingleton
import br.com.marllons.crudprodutos.model.Produto
import br.com.marllons.crudprodutos.utils.BackStackNavigator
import br.com.marllons.crudprodutos.utils.NavigationListener


/**
 *  Created by Marllon S on 3/2/2021. All rights reserved.
 */

class ProductFragment : Fragment(), BackStackNavigator {

    var binding : FragmentProductBinding? = null
    private lateinit var adapter : ProdutosAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductBinding.inflate( inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ProdutosAdapter(::call)
        adapter.submitList(ProductSingleton.listProducts)
        binding?.recyclerProd?.adapter = adapter
        var i = 0
        binding?.floatingActionButton?.visibility = View.VISIBLE
        binding?.floatingActionButton?.setOnClickListener{
            NavigationListener.navigateTo(AddEditProductFragment(), "Add Produto", true)
            binding?.floatingActionButton?.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }


    private fun call(prod: Produto, position: Int) {
        NavigationListener.navigateTo(ShowProductFragment.newInstance(prod, position), "Mostrar produto", true)
    }

    override fun onBackPressed() {
        //NavigationListener.popUpTo(MenuFragment().javaClass)
        NavigationListener.popUp()
    }

}