package github.gulzar1996.besthackernewsapp.ui.home

import github.gulzar1996.besthackernewsapp.data.db.DataOperation
import github.gulzar1996.besthackernewsapp.data.db._Post
import github.gulzar1996.besthackernewsapp.data.network.HackerNewsAPI
import io.reactivex.Single
import io.realm.RealmList
import javax.inject.Inject

class HomeInteractor @Inject constructor() : IHomeInteractor {

    @Inject
    lateinit var hackerNewsAPI: HackerNewsAPI

    @Inject
    lateinit var dataOperation: DataOperation


    override fun getTopHackerNewsPost(page: Int, loadCache: Boolean): Single<MutableList<Single<_Post>>> {

        val pageLimit = 20

        //Okay, so first check whether cache is available and how many posts are offline


        return hackerNewsAPI.getTopPostId()
                .concatMapIterable { it }
                .skip(((pageLimit * (page + 1)) - pageLimit).toLong())
                .take(pageLimit.toLong())
                .concatMapEager { it -> hackerNewsAPI.getPostDetails(it) }
                .filter { !it.title.isEmpty() }
                .map { it ->
                    savePost(_Post(
                            id = it.id.toLong(),
                            by = it.by,
                            descendants = it.descendants.toString(),
                            kids = when (it.kids) {
                                null -> RealmList()
                                else -> {
                                    var realmList: RealmList<String> = RealmList()
                                    realmList.addAll(it.kids)
                                    realmList
                                }
                            },
                            score = it.score,
                            time = it.time,
                            title = it.title,
                            type = it.type,
                            url = it.url
                    ))
                }
                .toList()

    }

    override fun savePost(post: _Post): Single<_Post> =
            dataOperation.savePost(post)


}