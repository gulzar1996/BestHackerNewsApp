package github.gulzar1996.besthackernewsapp.ui.detail

import github.gulzar1996.besthackernewsapp.ui.base.IBaseView

interface IDetailView : IBaseView {

    fun showToast(str : String)

    fun addCommentFragment(postId: Int)

    fun addWebViewFragmet(postId: Int)

    fun setUpTabs()

}