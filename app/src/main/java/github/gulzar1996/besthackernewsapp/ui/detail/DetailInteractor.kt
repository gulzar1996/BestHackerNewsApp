package github.gulzar1996.besthackernewsapp.ui.detail

import github.gulzar1996.besthackernewsapp.data.Post
import github.gulzar1996.besthackernewsapp.data.db.HackerNewsLocal
import io.reactivex.Single
import javax.inject.Inject

class DetailInteractor @Inject constructor() : IDetailInteractor {

    @Inject
    lateinit var hackerNewsLocal: HackerNewsLocal

    override fun getPost(postId: Int): Single<Post> {
        return hackerNewsLocal.getPost(postId)
    }

}