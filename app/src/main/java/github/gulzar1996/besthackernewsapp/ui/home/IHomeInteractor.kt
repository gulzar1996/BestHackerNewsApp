package github.gulzar1996.besthackernewsapp.ui.home

import github.gulzar1996.besthackernewsapp.data.Post
import github.gulzar1996.besthackernewsapp.ui.base.IBaseInteractor
import io.reactivex.Single

interface IHomeInteractor : IBaseInteractor {

    fun getHackerNews(pageNo : Int) : Single<List<Post>>


}