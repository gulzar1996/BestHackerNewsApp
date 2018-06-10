package github.gulzar1996.besthackernewsapp.ui.detail.webview

import github.gulzar1996.besthackernewsapp.ui.base.IBasePresenter

interface IWebFragmentPresenter<V : IWebFragmentView, I : IWebFragmentInteractor> : IBasePresenter<V, I> {
    fun loadUrl(id: Int)
}