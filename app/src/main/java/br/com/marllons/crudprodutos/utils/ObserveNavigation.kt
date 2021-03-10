package br.com.marllons.crudprodutos.utils

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ActionBarContainer
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import br.com.marllons.crudprodutos.R
import br.com.marllons.crudprodutos.view.MainActivity
import br.com.marllons.crudprodutos.view.MenuFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.*

/**
 *  Created by Marllon S on 3/2/2021. All rights reserved.
 */

abstract class ObserveNavigation : AppCompatActivity() {
    private var disposables = CompositeDisposable()
    private var actionBarTitles = Stack<String>()
    private var containerId: Int? = null
    private var toolbar : Toolbar? = null
    private var fragment: Fragment? = null
    protected var hasDrawer = true


    abstract fun getRootView(): View?

    abstract fun getIdToolbar(): Int

    protected fun setFragmentBaseAndObserveNavActions(savedInstanceState: Bundle?, containerId: Int, fragment: Fragment){
        containerId.let { this.containerId = it }
        fragment.let { this.fragment = it }
        savedInstanceState ?: supportFragmentManager.beginTransaction().add(containerId, fragment).addToBackStack(fragment.javaClass.simpleName).commitAllowingStateLoss()
        setSupportActionBarAndObserveNavActions()
    }

    private fun setSupportActionBarAndObserveNavActions() {
        toolbar = getRootView()?.findViewById(getIdToolbar())
        setSupportActionBar(toolbar)
        getTitleToolbar()?.text = getString(R.string.app_name)
        toolbar?.setNavigationOnClickListener { onBackPressed() }

        disposables.add(
            NavigationListener
                .observable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ navAction ->
                    when (navAction) {
                        is NavigationListener.NavAction.Push -> onNavActionPush(navAction)
                        is NavigationListener.NavAction.Replace -> onNavActionReplace(navAction)
                        is NavigationListener.NavAction.PopUpTo -> onNavActionPopUpTo(navAction)
                        is NavigationListener.NavAction.PopUp -> {
                            if (hasDrawer) {
                                if (!actionBarTitles.isEmpty())
                                    supportActionBar?.title = actionBarTitles.pop()
                                //if (resToToolbar()?.isShown == true && actionBarTitles.isEmpty())
                                    //resToToolbar()?.visibility = View.GONE
                            }
                            //supportFragmentManager.beginTransaction().remove(supportFragmentManager.fragments[supportFragmentManager.fragments.size-1]).commitAllowingStateLoss()
//                            supportFragmentManager.popBackStack(supportFragmentManager.fragments[supportFragmentManager.fragments.size-2].javaClass.simpleName, 0)
                            Log.i("POP", supportFragmentManager.fragments[supportFragmentManager.fragments.size-1].javaClass.simpleName)
                            super.onBackPressed()
                        }
                    }
                }, { e -> Log.d("Navigation", e.toString()) })
        )
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.container)

        if (fragment is MenuFragment)
            finish()


        if (fragment is BackStackNavigator) {
            (fragment as BackStackNavigator).onBackPressed()
            return
        }


    }

    private fun resToToolbar(): Toolbar? = getRootView()?.findViewById(getIdToolbar())
    private fun getTitleToolbar(): TextView? = getRootView()?.findViewById(R.id.toolbar_title)


    private fun onNavActionPush(pushAction: NavigationListener.NavAction.Push) {
        val transaction = supportFragmentManager.beginTransaction()
        if (pushAction.animate)
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right)

        transaction
            .add(containerId!!, pushAction.fragment)
            .addToBackStack(pushAction.fragment.javaClass.simpleName)
            .commitAllowingStateLoss()

        if (hasDrawer) {
            actionBarTitles.push(supportActionBar?.title.toString())
            getTitleToolbar()?.text = pushAction.title
            supportActionBar?.title = pushAction.title

            if (!resToToolbar()?.isShown!! && !actionBarTitles.isEmpty())
                resToToolbar()?.visibility = View.VISIBLE
        }
        getTitleToolbar()?.visibility = View.VISIBLE
    }


    private fun onNavActionReplace(replaceAction: NavigationListener.NavAction.Replace) {
        val transaction = supportFragmentManager.beginTransaction()
        if (replaceAction.animate)
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.fade_out)

        transaction
            .replace(containerId!!, replaceAction.fragment)
            .addToBackStack(replaceAction.fragment.javaClass.simpleName)
            .commitAllowingStateLoss()

        if (hasDrawer) {
            actionBarTitles.push(resToToolbar()?.title.toString())
            getTitleToolbar()?.text = replaceAction.title
            supportActionBar?.title = replaceAction.title

            if (!resToToolbar()?.isShown!! && !actionBarTitles.isEmpty())
                resToToolbar()?.visibility = View.VISIBLE
        }
        getTitleToolbar()?.visibility = View.VISIBLE

    }


    private fun onNavActionPopUpTo(popupAction: NavigationListener.NavAction.PopUpTo) {
        var fragments = listOf(supportFragmentManager.fragments)
        for (i in 0..fragments.size) {
            if (fragments.get(i).javaClass == popupAction.fragmentClass) {
                var lastIndex = fragments.size - 1
                while (lastIndex > i) {
                    if (hasDrawer && !actionBarTitles.empty())
                        supportActionBar?.title = actionBarTitles.pop()
                    lastIndex--
                }
                break
            }
        }
        supportFragmentManager.popBackStack(popupAction.fragmentClass.simpleName, 0)
    }

    protected fun onPopTo(fragment: Fragment) {
        if (actionBarTitles.size == 0 || actionBarTitles.size == 1 && fragment is MenuFragment) {
            getTitleToolbar()?.visibility = View.VISIBLE
            getTitleToolbar()?.text = getString(R.string.app_name)
        } else
            supportActionBar?.title = actionBarTitles.pop()
    }
}

