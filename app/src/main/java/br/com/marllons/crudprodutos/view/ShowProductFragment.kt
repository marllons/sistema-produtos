package br.com.marllons.crudprodutos.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.marllons.crudprodutos.databinding.FragmentShowProductBinding
import br.com.marllons.crudprodutos.model.ProductSingleton
import br.com.marllons.crudprodutos.model.Produto
import br.com.marllons.crudprodutos.utils.NavigationListener


/**
 *  Created by Marllon S on 3/2/2021. All rights reserved.
 */

class ShowProductFragment : Fragment() {

    private lateinit var binding: FragmentShowProductBinding
    private val prod by lazy { arguments?.getSerializable("PROD") as? Produto}
    private val pos by lazy { arguments?.getInt("POS") }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShowProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.run {
            textViewNome.text = prod?.nome
            textViewCodBarras.text = prod?.codBarras
            textViewEntradaP.text = prod?.precoEntrada
            textViewSaidaP.text = prod?.precoSaida
            textViewQuantidade.text = prod?.qtd
            textViewTipoVenda.text = prod?.tipoVenda

            editar.setOnClickListener {
                NavigationListener.replace(AddEditProductFragment.newInstance(pos!!), "Editar", true)
            }

            deletar.setOnClickListener {
                pos?.let {
                    ProductSingleton.listProducts.removeAt(it)
                    Toast.makeText(activity, "Produto removido com sucesso", Toast.LENGTH_SHORT).show()
                    NavigationListener.replace(ProductFragment(), "Produtos", true)
                }
            }


        }
    }

    companion object {
        @JvmStatic
        fun newInstance(prod: Produto, position: Int) = ShowProductFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("PROD", prod)
                    putInt("POS", position)
                }
            }
    }
}