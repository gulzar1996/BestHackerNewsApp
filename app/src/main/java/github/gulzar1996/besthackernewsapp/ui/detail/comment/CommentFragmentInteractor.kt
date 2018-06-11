package github.gulzar1996.besthackernewsapp.ui.detail.comment

import android.util.Log
import github.gulzar1996.besthackernewsapp.data.Comment
import github.gulzar1996.besthackernewsapp.data.db.HackerNewsLocal
import github.gulzar1996.besthackernewsapp.data.network.HackerNewsRemote
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CommentFragmentInteractor @Inject constructor() : ICommentFragmentInteractor {


    @Inject
    lateinit var hackerNewsRemote: HackerNewsRemote

    @Inject
    lateinit var hackerNewsLocal: HackerNewsLocal

    private val pageLimit = 10

    val TAG = javaClass.simpleName


    override fun getCommentList(postId: Int, isListCacheDirty: Boolean, pageNo: Int): Single<List<Comment>> {
        Log.d(TAG,"getCommentList func called")
      return  Observable.concat(
                getFromDisk(postId, pageNo).onErrorResumeNext(Observable.empty()),
                getFromNetwork(postId, pageNo))
                .filter { !it.isEmpty() }
                .first(emptyList())

    }


    fun getFromNetwork(postId: Int, page: Int): Observable<List<Comment>> =
            hackerNewsRemote.getPostDetails(postId)
                    .map { it -> hackerNewsLocal.savePost(it) }
                    .subscribeOn(Schedulers.io())
                    .map { it -> it.kids }
                    .filter { it.size > 0 }
                    .flatMapIterable { it }
                    .skip(((pageLimit * (page + 1)) - pageLimit).toLong())
                    .take(pageLimit.toLong())
                    .concatMapEager { hackerNewsRemote.getCommentDetails(it.toInt()) }
                    .filter { !it.text.isEmpty() }
                    .map { it -> hackerNewsLocal.saveComment(it) }
                    .toList()
                    .toObservable()


    fun getFromDisk(postId: Int, page: Int): Observable<List<Comment>> =

            hackerNewsLocal.getPost(postId)
                    .subscribeOn(Schedulers.io())

                    .toObservable()
                    .doOnNext({ Log.d(TAG, "Loading from Comment DETAILS SIZE  : ${it.kids}") })

                    .map { it -> it.kids }

                    .filter { it.size > 0 }
                    .flatMapIterable { it }
                    .skip(((pageLimit * (page + 1)) - pageLimit).toLong())
                    .take(pageLimit.toLong())

                    .concatMapEager { it ->
                        getCommentDetails(it)
                    }
                    .filter { !it.text.isEmpty() }
                    .doOnNext({ Log.d(TAG, "Loaded  : ${it}") })

                    .toList()
                    .toObservable()


    fun getCommentDetails(it: String) =

            Observable.concat(hackerNewsLocal.getComment(it.toInt())
                    .subscribeOn(Schedulers.io())
                    .toObservable()
                    .doOnError({ Log.d(TAG, "Comment loading error  : ${it}") })
                    .doOnNext({ Log.d(TAG, "Loading from Comment DETAILS FROM DISK page  : ${it.id}") })
                    .onErrorResumeNext(Observable.just(Comment(id = -1)))
                    , hackerNewsRemote.getCommentDetails(it.toInt())
                    .subscribeOn(Schedulers.io())
                    .onErrorResumeNext(Observable.just(Comment(id = -1)))
                    .filter { it.id.toInt() != -1 }
                    .map { it -> hackerNewsLocal.saveComment(it) }
                    .doOnNext({ Log.d(TAG, "Loading from Comment DETAILS FROM NETWORK page : ${it.id}") })
                    .subscribeOn(Schedulers.io()))
                    .filter { it.id.toInt() != -1 }
                    .first(Comment())
                    .toObservable()


}