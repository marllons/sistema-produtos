package br.com.marllons.crudprodutos.utils

import androidx.fragment.app.Fragment
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable

/**
 *  Created by Marllon S on 3/2/2021. All rights reserved.
 */

/**
 * O [NavigationListener] é um objeto responsável por emitir e observar eventos encapsulados por [NavAction] que devem modificar
 * a stack de fragmentos da activity host sem criar novos acoplamentos.
 * Os fragmentos que utilizarem [NavigationListener] devem implementar [BackStackNavigator]
 */
object NavigationListener {

    /**
     * Classe que representa todos eventos de navegação possíveis.
     * [Push] Evento responsável por adicionar um [Fragment] na stack
     * [PopUpTo] Evento responsável por navegar de volta para um [Fragment] específico
     * [PopUp] Evento responsável por navegar de volta para o [Fragment] anterior
     */
    sealed class NavAction {
        data class Push(val fragment: Fragment, val title: String, val animate: Boolean) : NavAction()
        data class Replace(val fragment: Fragment, val title: String, val animate: Boolean) : NavAction()
        data class PopUpTo(val fragmentClass: Class<*>) : NavAction()
        object PopUp : NavAction()
    }

    private val navigationPublisher = PublishRelay.create<NavAction>()

    /**
     * [io.reactivex.Observable] de [NavAction] contendo o fluxo de eventos da stack de fragmentos
     * @return [io.reactivex.Observable] de [NavAction]
     */
    @JvmStatic
    fun observable(): Observable<NavAction> = navigationPublisher.hide()

    /**
     * Publica um evento de [NavAction.Push] na stream emitida por [observable].
     * A Classe que consome a stream deve navegar para o fragmento especificado
     * utilizando o método add de SupportFragmentManager
     * @property fragment [Fragment] que deve ser adicionado na stack
     * @property title Título do fragmento que deve ser utilizado na [android.widget.Toolbar]
     */
    @JvmStatic
    @JvmOverloads
    fun navigateTo(fragment: Fragment, title: String, animate: Boolean = false) = navigationPublisher.accept(
        NavAction.Push(fragment, title, animate)
    )

    /**
     * Publica um evento de [NavAction.Replace] na stream emitida por [observable].
     * A Classe que consome a stream deve navegar para o fragmento especificado
     * utilizando o método replace de SupportFragmentManager
     * @property fragment [Fragment] que deve ser adicionado na stack
     * @property title Título do fragmento que deve ser utilizado na [android.widget.Toolbar]
     */
    @JvmStatic
    @JvmOverloads
    fun replace(fragment: Fragment, title: String, animate: Boolean = false) = navigationPublisher.accept(
        NavAction.Replace(fragment, title, animate)
    )

    /**
     * Publica um evento de [NavAction.PopUp] na stream emitida por [observable].
     * A Classe que consome a stream deve navegar de volta
     * para o fragmento previamente adicionado utilizando [navigateTo]
     */
    @JvmStatic
    fun popUp() = navigationPublisher.accept(NavAction.PopUp)

    /**
     * Publica um evento de [NavAction.PopUpTo] com [fragmentClass] como parâmetro.
     * A Classe que consome a stream deve navegar de volta
     * para o fragmento específico se este tiver sido previamente adicionado utilizando [navigateTo]
     * @property fragmentClass Class do fragmento para o qual a stack deve retornar
     */
    @JvmStatic
    fun popUpTo(fragmentClass: Class<*>) = navigationPublisher.accept(
        NavAction.PopUpTo(
            fragmentClass
        )
    )
}

