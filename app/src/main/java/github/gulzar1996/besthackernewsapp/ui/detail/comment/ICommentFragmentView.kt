package github.gulzar1996.besthackernewsapp.ui.detail.comment

import android.support.v4.widget.SwipeRefreshLayout
import github.gulzar1996.besthackernewsapp.data.Comment
import github.gulzar1996.besthackernewsapp.data.Post
import github.gulzar1996.besthackernewsapp.ui.base.IBaseView

interface ICommentFragmentView : IBaseView {

    fun addToAdapter(post: ArrayList<Comment>)

    fun deleteAdapter()


    fun getRefresh(): SwipeRefreshLayout
}