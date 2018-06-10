package github.gulzar1996.besthackernewsapp.ui.detail

import github.gulzar1996.besthackernewsapp.ui.base.IBasePresenter
import github.gulzar1996.besthackernewsapp.ui.login.ILoginView
import github.gulzar1996.besthackernewsapp.ui.login.IloginInteractor

interface ILoginPresenter<V : ILoginView, I : IloginInteractor> : IBasePresenter<V, I> {

    fun onSignInButtonClick()
}