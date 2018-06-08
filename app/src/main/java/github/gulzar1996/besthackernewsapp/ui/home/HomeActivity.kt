package github.gulzar1996.besthackernewsapp.ui.home

import android.os.Bundle
import github.gulzar1996.besthackernewsapp.R
import github.gulzar1996.besthackernewsapp.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject


class HomeActivity : BaseActivity(), IHomeView {

    @Inject
    lateinit var homePresenter: HomePresenter<IHomeView, IHomeInteractor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        homePresenter.onAttach(this)
        homePresenter.loadInitial()

        recyclerView.setOnClickListener {

        }

    }

    override fun onDestroy() {
        homePresenter.onDetach()
        super.onDestroy()

    }
}