package github.gulzar1996.besthackernewsapp.ui.detail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.Toast
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import github.gulzar1996.besthackernewsapp.Const
import github.gulzar1996.besthackernewsapp.R
import github.gulzar1996.besthackernewsapp.ui.base.BaseActivity
import github.gulzar1996.besthackernewsapp.ui.detail.comment.CommentFragment
import github.gulzar1996.besthackernewsapp.ui.detail.webview.WebFragment
import kotlinx.android.synthetic.main.activity_detail.*
import javax.inject.Inject


class DetailActivity : BaseActivity(), IDetailView, HasSupportFragmentInjector {


    @Inject
    internal lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var detailPresenter: DetailPresenter<IDetailView, IDetailInteractor>

    @Inject
    lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentDispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left)

        setUpToolbar()

        detailPresenter.onAttach(this)

        sendIntent()

    }

    fun sendIntent() {
        val postId: Int? = intent.extras.get(Const.POST_ID) as Int?
        postId?.let { detailPresenter.loadDetails(it) }
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        collapsableToolbar.title = ""
    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right)
    }

    override fun showToast(str: String) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
    }

    override fun addCommentFragment(postId: Int) {
        val b = Bundle()
        b.putInt(Const.POST_ID, postId)
        viewPagerAdapter.addContainer(CommentFragment(), "COMMENT", b)

    }

    override fun addWebViewFragmet(postId: Int) {
        val b = Bundle()
        b.putInt(Const.POST_ID, postId)
        viewPagerAdapter.addContainer(WebFragment(), "WEB", b)

    }


    override fun setUpTabs() {
        viewpager.adapter = viewPagerAdapter
        tabs.setupWithViewPager(viewpager)
    }


    override fun onFragmentDetached(tag: String) {}

    override fun onFragmentAttached() {}
}