package br.com.marllons.crudprodutos.utils

/**
 * Interface responsável por interceptar os eventos de onBackPressed.
 * Geralmente utilizada em conjunto com Navigation Listener
 */
interface BackStackNavigator {

    /**
     * Esse método deve ser implementado para interceptar os eventos de [android.app.Activity.onBackPressed]
     */
    fun onBackPressed()
}