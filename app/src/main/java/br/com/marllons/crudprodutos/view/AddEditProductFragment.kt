package br.com.marllons.crudprodutos.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.marllons.crudprodutos.databinding.FragmentAddEditProductBinding
import br.com.marllons.crudprodutos.model.ProductSingleton
import br.com.marllons.crudprodutos.model.Produto
import br.com.marllons.crudprodutos.utils.NavigationListener

/**
 *  Created by Marllon S on 3/2/2021. All rights reserved.
 */

class AddEditProductFragment : Fragment() {

    private lateinit var binding: FragmentAddEditProductBinding

    private val pos by lazy { arguments?.getInt("POS")}
    private var isEdit : Boolean? = null ?: false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddEditProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.salvar.setOnClickListener {
            binding.run {
                if (isEdit == true) {
                    ProductSingleton.listProducts[pos!!].apply {
                        nome = editNomeProduto.text.toString()
                        codBarras = editCodBarras.text.toString()
                        tipoVenda = editTipoVenda.text.toString()
                        precoEntrada = editPrecoEntrada.text.toString()
                        precoSaida = editPrecoSaida.text.toString()
                        qtd = editQuantidade.text.toString()
                    }
                    Toast.makeText(activity, "Produto editado com sucesso", Toast.LENGTH_SHORT).show()

                } else {
                    ProductSingleton.listProducts.add(Produto(
                        (0..99).random(),
                        editNomeProduto.text.toString(),
                        editCodBarras.text.toString(),
                        (0..10).random().toString(),
                        editTipoVenda.text.toString(),
                        editPrecoEntrada.text.toString(),
                        editPrecoSaida.text.toString(),
                        editQuantidade.text.toString()))
                    Toast.makeText(activity, "Produto salvo com sucesso", Toast.LENGTH_SHORT).show()

                }

            }
            NavigationListener.navigateTo(ProductFragment(), "Produto", true)
        }

        if (isEdit == true) {
            binding.run {
                editNomeProduto.setText(ProductSingleton.listProducts[pos!!].nome)
                editCodBarras.setText(ProductSingleton.listProducts[pos!!].codBarras)
                editPrecoEntrada.setText(ProductSingleton.listProducts[pos!!].precoEntrada)
                editPrecoSaida.setText(ProductSingleton.listProducts[pos!!].precoSaida)
                editQuantidade.setText(ProductSingleton.listProducts[pos!!].qtd)
                editTipoVenda.setText(ProductSingleton.listProducts[pos!!].tipoVenda)
            }
        }
    }



    companion object {
        @JvmStatic
        fun newInstance(pos: Int) = AddEditProductFragment().apply {
                arguments = Bundle().apply {
                    putInt("POS", pos)
                }
                isEdit = true
            }
    }
}