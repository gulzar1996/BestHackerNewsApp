package github.gulzar1996.besthackernewsapp.ui.home

import android.support.v4.widget.SwipeRefreshLayout
import github.gulzar1996.besthackernewsapp.data.Post
import github.gulzar1996.besthackernewsapp.ui.base.IBaseView

interface IHomeView : IBaseView {

    fun addToAdapter(post: ArrayList<Post>)

    fun deleteAdapter()

    fun showToast(string: String)

    fun getRefresh(): SwipeRefreshLayout

    fun setLastRefershedTime(string: String)

    fun navigateToDetailActivity(postId: Int)

}