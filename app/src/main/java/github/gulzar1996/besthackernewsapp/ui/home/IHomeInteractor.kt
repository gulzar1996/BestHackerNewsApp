package github.gulzar1996.besthackernewsapp.ui.home

import github.gulzar1996.besthackernewsapp.data.Post
import github.gulzar1996.besthackernewsapp.ui.base.IBaseInteractor
import io.reactivex.Single

interface IHomeInteractor : IBaseInteractor {

    fun getHackerNews(pageNo: Int, isPostCacheDirty: Boolean, isListCacheDirty: Boolean): Single<List<Post>>

    fun getLastRefreshedTime(): Single<String>


}