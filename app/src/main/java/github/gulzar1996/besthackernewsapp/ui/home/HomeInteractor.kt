package github.gulzar1996.besthackernewsapp.ui.home

import android.util.Log
import github.gulzar1996.besthackernewsapp.data.Post
import github.gulzar1996.besthackernewsapp.data.PostList
import github.gulzar1996.besthackernewsapp.data.db.HackerNewsLocal
import github.gulzar1996.besthackernewsapp.data.network.HackerNewsRemote
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.realm.RealmList
import org.ocpsoft.prettytime.PrettyTime
import java.util.*
import javax.inject.Inject

class HomeInteractor @Inject constructor() : IHomeInteractor {


    @Inject
    lateinit var hackerNewsRemote: HackerNewsRemote

    @Inject
    lateinit var hackerNewsLocal: HackerNewsLocal

    private val pageLimit = 20

    val TAG = javaClass.simpleName


    override fun getHackerNews(pageNo: Int, isPostCacheDirty: Boolean, isListCacheDirty: Boolean): Single<List<Post>> =
            when (isPostCacheDirty && isListCacheDirty) {
                false -> Observable.concat(
                        getFromDisk(pageNo, isPostCacheDirty).onErrorResumeNext(Observable.empty()),
                        getFromNetwork(pageNo, isPostCacheDirty))
                        .filter { !it.isEmpty() }
                        .first(emptyList())
                true -> getFromNetwork(pageNo, isPostCacheDirty).first(emptyList())
            }


    /**
     * NETWORK ONLY
     * All the operations are fetched from network and persisted on the disk
     */
    private fun getFromNetwork(page: Int, isCacheDirty: Boolean): Observable<List<Post>> =
            hackerNewsRemote.getTopPostId()
                    .doOnNext({ Log.d(TAG, "Loading from Network page : $page") })
                    .subscribeOn(Schedulers.io())
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
                    .concatMapEager { it ->
                        getPostDetails(it, isCacheDirty)
                    }
                    .filter { !it.title.isEmpty() }
                    .toList()
                    .toObservable()


    /**
     * DISK + NETWORK
     * Top post Id List is fetched from DISK
     * Post details id fetched from DISK + NETWORK
     */
    private fun getFromDisk(page: Int, isCacheDirty: Boolean): Observable<List<Post>> =

            hackerNewsLocal.getTopPostId()
                    .subscribeOn(Schedulers.io())
                    .toObservable()
                    .doOnNext({ Log.d(TAG, "Loading from DISK page : $page") })
                    .concatMapIterable { it }
                    .skip(((pageLimit * (page + 1)) - pageLimit).toLong())
                    .take(pageLimit.toLong())
                    .concatMapEager { it ->
                        getPostDetails(it, isCacheDirty)
                    }
                    .filter { !it.title.isEmpty() }
                    .toList()
                    .toObservable()


    /**
     * Only used when retrieving from cache
     * This is used to retrieve post details from persistence if available
     * otherwise from network
     * Hack is used to fallback to network by making the post.id =-1
     */
    private fun getPostDetails(it: String, isCacheDirty: Boolean) =

            when (isCacheDirty) {
                false -> {
                    Observable.concat(hackerNewsLocal.getPost(it.toInt())
                            .subscribeOn(Schedulers.io())
                            .toObservable()
                            .doOnNext({ Log.d(TAG, "Loading from POST DETAILS FROM DISK page  : ${it.id}") })
                            .onErrorResumeNext(Observable.just(Post(id = -1)))
                            , hackerNewsRemote.getPostDetails(it.toInt())
                            .subscribeOn(Schedulers.io())
                            .onErrorResumeNext(Observable.just(Post(id = -1)))
                            .filter { it.id.toInt() != -1 }
                            .map { it -> hackerNewsLocal.savePost(it) }
                            .doOnNext({ Log.d(TAG, "Loading from POST DETAILS FROM NETWORK page : ${it.id}") })
                            .subscribeOn(Schedulers.io()))
                            .filter { it.id.toInt() != -1 }
                            .first(Post())
                            .toObservable()
                }
                true -> {
                    hackerNewsRemote.getPostDetails(it.toInt())
                            .map { it -> hackerNewsLocal.savePost(it) }
                            .doOnNext({ Log.d(TAG, "Loading from POST DETAILS FROM NETWORK page CacheDirty? : $isCacheDirty : ${it.id}") })
                            .subscribeOn(Schedulers.io())
                }
            }

    override fun getLastRefreshedTime(): Single<String> = hackerNewsLocal.getLastRefreshedTime()
            .flatMap { it -> Single.just(PrettyTime().format(Date(it))) }


}

