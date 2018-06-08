package github.gulzar1996.besthackernewsapp.ui.home

import github.gulzar1996.besthackernewsapp.data.Post
import github.gulzar1996.besthackernewsapp.data.PostList
import github.gulzar1996.besthackernewsapp.data.db.HackerNewsLocal
import github.gulzar1996.besthackernewsapp.data.network.HackerNewsRemote
import io.reactivex.Observable
import io.reactivex.Single
import io.realm.RealmList
import javax.inject.Inject

class HomeInteractor @Inject constructor() : IHomeInteractor {


    @Inject
    lateinit var hackerNewsRemote: HackerNewsRemote

    @Inject
    lateinit var hackerNewsLocal: HackerNewsLocal

    private val pageLimit = 20


    override fun getHackerNews(pageNo: Int, isCacheDirty: Boolean): Single<List<Post>> =
            when (isCacheDirty) {
                false -> Observable.concat(
                        getFromDisk(pageNo).onErrorResumeNext(Observable.empty()),
                        getFromNetwork(pageNo))
                        .filter { !it.isEmpty() }
                        .first(emptyList())
                true -> getFromNetwork(pageNo).first(emptyList())
            }


    /**
     * NETWORK ONLY
     * All the operations are fetched from network and persisted on the disk
     */
    private fun getFromNetwork(page: Int): Observable<List<Post>> =

            hackerNewsRemote.getTopPostId()
                    .map {
                        hackerNewsLocal.saveTopPostList(
                                PostList(id = 0
                                        , timeStamp = System.currentTimeMillis()
                                        , list = when (it) {
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
                    .concatMapEager { it -> hackerNewsRemote.getPostDetails(it.toInt()) }
                    .map { it -> hackerNewsLocal.savePost(it) }
                    .filter { !it.title.isEmpty() }
                    .toList()
                    .toObservable()


    /**
     * DISK + NETWORK
     * Top post Id List is fetched from DISK
     * Post details id fetched from DISK + NETWORK
     */
    private fun getFromDisk(page: Int): Observable<List<Post>> =

            hackerNewsLocal.getTopPostId()
                    .toObservable()
                    .concatMapIterable { it }
                    .skip(((pageLimit * (page + 1)) - pageLimit).toLong())
                    .take(pageLimit.toLong())
                    .concatMapEager { it ->
                        getPostDetails(it)
                    }
                    .filter { !it.title.isEmpty() }
                    .toList()
                    .toObservable()


    /**
     * Only used when retrieving from cache
     * This is used to retrieve post details from persistence if available
     * otherwise from network
     * Hack is used to fallback to network by making the post id =-1
     */
    private fun getPostDetails(it: String) =
            Observable.concat(hackerNewsLocal.getPost(it.toInt()).toObservable()
                    .onErrorResumeNext(Observable.just(Post(id = -1)))
                    , hackerNewsRemote.getPostDetails(it.toInt()))
                    .filter { it.id.toInt() != -1 }
                    .first(Post())
                    .toObservable()


}

