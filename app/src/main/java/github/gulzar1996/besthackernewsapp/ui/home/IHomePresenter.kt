package github.gulzar1996.besthackernewsapp.ui.home

import github.gulzar1996.besthackernewsapp.ui.base.IBasePresenter

interface IHomePresenter<V : IHomeView, I : IHomeInteractor> : IBasePresenter<V, I> {

    fun loadInitial()
}