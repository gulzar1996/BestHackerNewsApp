package github.gulzar1996.besthackernewsapp.ui.home

import github.gulzar1996.besthackernewsapp.data.Post
import github.gulzar1996.besthackernewsapp.data.TopPostList
import github.gulzar1996.besthackernewsapp.ui.base.IBaseInteractor
import io.reactivex.Observable
import io.reactivex.Single

interface IHomeInteractor : IBaseInteractor {

    fun getTopPostListFromDisk(): Single<List<String>>

    fun savePostToDisk(post: Post): Post

    fun saveTopPostListToDisk(topPostList: TopPostList): TopPostList

    fun getPostFromDisk(postId: Int): Single<Post>

    fun getHackerNews(pageNo : Int) : Single<List<Post>>


}