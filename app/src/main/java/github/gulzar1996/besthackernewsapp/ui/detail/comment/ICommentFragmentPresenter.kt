package github.gulzar1996.besthackernewsapp.ui.detail.comment

import github.gulzar1996.besthackernewsapp.ui.base.IBasePresenter

interface ICommentFragmentPresenter<V : ICommentFragmentView, I : ICommentFragmentInteractor>
    : IBasePresenter<V, I> {
    fun loadComment(postId: Int)
}