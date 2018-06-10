package github.gulzar1996.besthackernewsapp.ui.detail.webview

import github.gulzar1996.besthackernewsapp.data.db.HackerNewsLocal
import io.reactivex.Single
import javax.inject.Inject

class WebFragmentInteractor @Inject constructor() : IWebFragmentInteractor {

    @Inject
    lateinit var hackerNewsLocal: HackerNewsLocal

    override fun getPostUrl(postId: Int): Single<String> =
            hackerNewsLocal.getPost(postId)
                    .map { it -> it.url }

}