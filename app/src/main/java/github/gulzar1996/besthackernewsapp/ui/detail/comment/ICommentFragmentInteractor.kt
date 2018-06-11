package github.gulzar1996.besthackernewsapp.ui.detail.comment

import github.gulzar1996.besthackernewsapp.data.Comment
import github.gulzar1996.besthackernewsapp.ui.base.IBaseInteractor
import io.reactivex.Single

interface ICommentFragmentInteractor : IBaseInteractor {
    fun getCommentList(postId: Int, isListCacheDirty: Boolean, pageNo: Int): Single<List<Comment>>
}