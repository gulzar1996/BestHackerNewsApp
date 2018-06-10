package github.gulzar1996.besthackernewsapp.ui.detail

import github.gulzar1996.besthackernewsapp.data.Post
import github.gulzar1996.besthackernewsapp.ui.base.IBaseInteractor
import io.reactivex.Single

interface IDetailInteractor : IBaseInteractor {
    fun getPost(postId: Int): Single<Post>
}