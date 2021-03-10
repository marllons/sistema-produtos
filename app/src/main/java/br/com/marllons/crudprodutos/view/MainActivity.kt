package br.com.marllons.crudprodutos.view


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import br.com.marllons.crudprodutos.R
import br.com.marllons.crudprodutos.databinding.ActivityMainBinding
import br.com.marllons.crudprodutos.utils.BackStackNavigator
import br.com.marllons.crudprodutos.utils.ObserveNavigation

/**
 *  Created by Marllon S on 3/2/2021. All rights reserved.
 */

class MainActivity : ObserveNavigation() {

    var binding: ActivityMainBinding? = null

    override fun getRootView(): View? {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        return binding?.root
    }

    override fun getIdToolbar(): Int = R.id.toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getRootView()
        setFragmentBaseAndObserveNavActions(savedInstanceState, R.id.container, MenuFragment())

    }


}