package github.gulzar1996.besthackernewsapp.ui.detail

import github.gulzar1996.besthackernewsapp.ui.base.IBaseView

interface IDetailView : IBaseView {

    fun showToast(str: String)

    fun addCommentFragment(postId: Int, commentCount: Int)

    fun addWebViewFragmet(postId: Int)

    fun showTitle(title: String)

    fun showUrl(url: String)

    fun showTime(time: String)

    fun showAuthor(author: String)


    fun setUpTabs()

}