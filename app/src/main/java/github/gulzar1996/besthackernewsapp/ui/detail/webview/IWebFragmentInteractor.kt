package github.gulzar1996.besthackernewsapp.ui.detail.webview

import github.gulzar1996.besthackernewsapp.ui.base.IBaseInteractor
import io.reactivex.Single

interface IWebFragmentInteractor : IBaseInteractor {
    fun getPostUrl(postId: Int): Single<String>

}