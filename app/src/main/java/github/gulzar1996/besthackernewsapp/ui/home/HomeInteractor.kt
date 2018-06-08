package github.gulzar1996.besthackernewsapp.ui.home

import github.gulzar1996.besthackernewsapp.data.Post
import github.gulzar1996.besthackernewsapp.data.TopPostList
import github.gulzar1996.besthackernewsapp.data.db.DataOperation
import github.gulzar1996.besthackernewsapp.data.network.HackerNewsAPI
import io.reactivex.Observable
import io.reactivex.Single
import io.realm.RealmList
import javax.inject.Inject

class HomeInteractor @Inject constructor() : IHomeInteractor {


    @Inject
    lateinit var hackerNewsAPI: HackerNewsAPI

    @Inject
    lateinit var dataOperation: DataOperation

    val pageLimit = 20


    override fun getHackerNews(pageNo: Int): Single<List<Post>> {
        return Observable.concat(
                getFromDisk(pageNo).onErrorResumeNext(Observable.empty()),
                getFromNetwork(pageNo))
                .filter { !it.isEmpty() }
                .first(emptyList())


    }

    fun getFromNetwork(page: Int): Observable<List<Post>> =

            hackerNewsAPI.getTopPostId()
                    .map {
                        saveTopPostListToDisk(TopPostList(id = 0, list = when (it) {
                            null -> RealmList()
                            else -> {
                                val realmList: RealmList<String> = RealmList()
                                realmList.addAll(it)
                                realmList
                            }
                        }))
                    }
                    .flatMap { it -> Observable.just(it.list) }
                    .concatMapIterable { it }
                    .skip(((pageLimit * (page + 1)) - pageLimit).toLong())
                    .take(pageLimit.toLong())
                    .concatMapEager { it -> hackerNewsAPI.getPostDetails(it.toInt()) }
                    .map { it ->
                        savePostToDisk(it)
                    }
                    .filter { !it.title.isEmpty() }
                    .toList()
                    .toObservable()


    fun getFromDisk(page: Int): Observable<List<Post>> =

            getTopPostListFromDisk()
                    .toObservable()
                    .concatMapIterable { it }
                    .skip(((pageLimit * (page + 1)) - pageLimit).toLong())
                    .take(pageLimit.toLong())
                    .concatMapEager { it -> getPostFromDisk(it.toInt()).toObservable() }
                    .filter { !it.title.isEmpty() }
                    .toList()
                    .toObservable()


    /**
     * Queries post by postId from persistence
     * @param postId
     */
    override fun getPostFromDisk(postId: Int): Single<Post> = dataOperation.getPost(postId)

    /**
     * Saves post to persistence
     * @param postId
     */
    override fun savePostToDisk(post: Post) = dataOperation.savePost(post)

    /**
     * Save top post Id to persistence
     * @param postId
     */
    override fun saveTopPostListToDisk(topPostList: TopPostList) = dataOperation.saveTopPostList(topPostList)

    override fun getTopPostListFromDisk(): Single<List<String>> = dataOperation.getTopPostId()


}

