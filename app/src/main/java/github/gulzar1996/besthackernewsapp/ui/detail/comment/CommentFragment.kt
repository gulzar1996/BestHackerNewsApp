package github.gulzar1996.besthackernewsapp.ui.detail.comment

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import github.gulzar1996.besthackernewsapp.Const
import github.gulzar1996.besthackernewsapp.R
import github.gulzar1996.besthackernewsapp.data.Comment
import github.gulzar1996.besthackernewsapp.ui.base.BaseFragment
import github.gulzar1996.besthackernewsapp.ui.detail.comment.di.CommentAdapter
import github.gulzar1996.besthackernewsapp.utils.rx.RxBus
import kotlinx.android.synthetic.main.fragment_comment.*
import javax.inject.Inject

class CommentFragment() : BaseFragment(), ICommentFragmentView {


    @Inject
    internal lateinit var presenter: ICommentFragmentPresenter<ICommentFragmentView, ICommentFragmentInteractor>

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var rxBus: RxBus

    lateinit var commentAdapter: CommentAdapter

    val TAG = javaClass.simpleName


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_comment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listUISetup()
        val bundle = this.arguments

        val postId = bundle?.get(Const.POST_ID) as Int
        Log.d(TAG,"Post id $postId")
        presenter.onAttach(this)
        presenter.loadComment(postId)

    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyViewCalled")
        presenter.onDetach()
        super.onDestroyView()
    }

    override fun addToAdapter(comments: ArrayList<Comment>) {
        Log.d(TAG, "add ${comments.size}")
        commentAdapter.addItems(comments)
    }

    override fun deleteAdapter() {
        commentAdapter.removeItems()
    }

    override fun getRefresh(): SwipeRefreshLayout = swipeRefresh2


    private fun listUISetup() {
        commentAdapter = CommentAdapter(rxBus)
        recyclerView2.apply {
            layoutManager = linearLayoutManager
            adapter = commentAdapter
        }
    }
}