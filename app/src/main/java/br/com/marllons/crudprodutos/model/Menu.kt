package br.com.marllons.crudprodutos.model

import br.com.marllons.crudprodutos.R

/**
 *  Created by Marllon S on 3/2/2021. All rights reserved.
 */
data class Menu(val label: String, var image: Int) {

    companion object {
        fun buildMenu(): List<Menu> =
            listOf(
                Menu("Produtos", R.drawable.ic_prod),
                Menu("Fornecedores", R.drawable.ic_forn),
                Menu("Entidade", R.drawable.ic_entid),
                Menu("Caixas", R.drawable.ic_caixa),
                Menu("Vendas", R.drawable.ic_venda),
                Menu("Entradas", R.drawable.ic_entradas),
                Menu("Relatórios", R.drawable.ic_relatorios),
                Menu("Funcionarios", R.drawable.ic_func),
                Menu("Usuários", R.drawable.ic_user)
            )
    }
}
