package br.com.marllons.crudprodutos.model

import java.io.Serializable

/**
 *  Created by Marllon S on 3/9/2021. All rights reserved.
 */
data class Produto(var id: Int?, var nome: String?, var codBarras: String?, var idForn: String?, var tipoVenda: String?, var precoEntrada: String?, var precoSaida: String?, var qtd: String?) : Serializable {
    constructor(id: Int?, nome: String?, precoEntrada: String?) : this(id, nome, null, null, null, precoEntrada, null, "10")
}
