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


    override fun getHackerNews(pageNo: Int): Single<List<Post>> = Observable.concat(
            getFromDisk(pageNo).onErrorResumeNext(Observable.empty()),
            getFromNetwork(pageNo))
            .filter { !it.isEmpty() }
            .first(emptyList())

    /**
     * NETWORK ONLY
     * All the operations are fetched from network and persisted on the disk
     */
    private fun getFromNetwork(page: Int): Observable<List<Post>> =

            hackerNewsRemote.getTopPostId()
                    .map {
                        saveTopPostListToDisk(PostList(id = 0
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
                    .map { it ->
                        savePostToDisk(it)
                    }
                    .filter { !it.title.isEmpty() }
                    .toList()
                    .toObservable()


    /**
     * DISK + NETWORK
     * Top post Id List is fetched from DISK
     * Post details id fetched from DISK + NETWORK
     */
    private fun getFromDisk(page: Int): Observable<List<Post>> =

            getTopPostListFromDisk()
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
            Observable.concat(getPostFromDisk(it.toInt())
                    .onErrorResumeNext(Observable.just(Post(id = -1)))
                    , hackerNewsRemote.getPostDetails(it.toInt()))
                    .filter { it.id.toInt() != -1 }
                    .first(Post())
                    .toObservable()


    /**
     * Queries post by postId from persistence
     * @param postId
     */
    fun getPostFromDisk(postId: Int): Observable<Post> = hackerNewsLocal.getPost(postId).toObservable()

    /**
     * Saves post to persistence
     * @param postId
     */
    fun savePostToDisk(post: Post) = hackerNewsLocal.savePost(post)

    /**
     * Save top post Id List and timestamp to persistence to persistence
     * @param postId
     */
    fun saveTopPostListToDisk(postList: PostList) = hackerNewsLocal.saveTopPostList(postList)

    /**
     * Retrieves List of top post id from persistence
     */
    fun getTopPostListFromDisk(): Single<List<String>> = hackerNewsLocal.getTopPostId()


}

