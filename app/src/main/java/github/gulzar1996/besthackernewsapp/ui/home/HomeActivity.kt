package github.gulzar1996.besthackernewsapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import github.gulzar1996.besthackernewsapp.Const
import github.gulzar1996.besthackernewsapp.R
import github.gulzar1996.besthackernewsapp.data.Post
import github.gulzar1996.besthackernewsapp.ui.base.BaseActivity
import github.gulzar1996.besthackernewsapp.ui.detail.DetailActivity
import github.gulzar1996.besthackernewsapp.ui.login.LoginActivity
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
        toolbarSetup()


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.logout -> {
                val auth = FirebaseAuth.getInstance()
                auth.signOut()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun toolbarSetup() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
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
        //hackerNewsAdapter.notifyDataSetChanged()
    }

    override fun deleteAdapter() {
        hackerNewsAdapter.removeItems()
    }

    override fun showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    override fun setLastRefershedTime(string: String) {
        refreshedTime.visibility = View.VISIBLE
        refreshedTime.text = string
    }

    override fun navigateToDetailActivity(postId: Int) {
        val i = Intent(this, DetailActivity::class.java)
        i.putExtra(Const.POST_ID, postId)
        startActivity(i)
    }

    override fun onStart() {
        super.onStart()
        homePresenter.setup()

    }

    override fun onStop() {
        homePresenter.onDetach()
        super.onStop()
    }

    override fun onFragmentDetached(tag: String) {}

    override fun onFragmentAttached() {}
}