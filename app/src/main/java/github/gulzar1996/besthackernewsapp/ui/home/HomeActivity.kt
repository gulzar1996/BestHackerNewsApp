package github.gulzar1996.besthackernewsapp.ui.home

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout
import github.gulzar1996.besthackernewsapp.R
import github.gulzar1996.besthackernewsapp.data.Post
import github.gulzar1996.besthackernewsapp.ui.base.BaseActivity
import github.gulzar1996.besthackernewsapp.utils.rx.RxBus
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject


class HomeActivity : BaseActivity(), IHomeView {


    @Inject
    lateinit var homePresenter: HomePresenter<IHomeView, IHomeInteractor>

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var rxBus: RxBus

    lateinit var hackerNewsAdapter: HackerNewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        homePresenter.onAttach(this)

        listUISetup()
        refreshListener()

        homePresenter.paginationSetup()

    }

    private fun refreshListener() {
        RxSwipeRefreshLayout
                .refreshes(swipeRefresh)
    }

    override fun getRefresh() = swipeRefresh

    private fun listUISetup() {
        hackerNewsAdapter = HackerNewsAdapter(rxBus)
        recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = hackerNewsAdapter
        }
    }

    override fun addToAdapter(post: ArrayList<Post>) {
        hackerNewsAdapter.addItems(post)
        hackerNewsAdapter.hasStableIds()
        hackerNewsAdapter.notifyDataSetChanged()
    }

    override fun deleteAdapter() {
        hackerNewsAdapter.removeItems()
    }

    override fun showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        homePresenter.onDetach()
        super.onDestroy()

    }
}