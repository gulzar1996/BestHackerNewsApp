package github.gulzar1996.besthackernewsapp.ui.detail

import github.gulzar1996.besthackernewsapp.ui.base.IBasePresenter

interface IDetailPresenter<V : IDetailView, I : IDetailInteractor> : IBasePresenter<V, I> {

    fun loadDetails(id: Int)
}