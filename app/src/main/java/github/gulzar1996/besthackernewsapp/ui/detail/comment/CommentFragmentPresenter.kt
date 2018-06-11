package github.gulzar1996.besthackernewsapp.ui.detail.comment

import android.util.Log
import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout
import github.gulzar1996.besthackernewsapp.data.Comment
import github.gulzar1996.besthackernewsapp.ui.base.BasePresenter
import github.gulzar1996.besthackernewsapp.ui.detail.comment.di.CommentAdapter
import github.gulzar1996.besthackernewsapp.utils.rx.RxBus
import github.gulzar1996.besthackernewsapp.utils.rx.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.PublishProcessor
import io.reactivex.rxkotlin.subscribeBy
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CommentFragmentPresenter<V : ICommentFragmentView, I : ICommentFragmentInteractor>
@Inject constructor(interactor: I, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V, I>(schedulerProvider, compositeDisposable, interactor),
        ICommentFragmentPresenter<V, I> {


    var isCacheDirty = false
    var isLoading = false

    val TAG: String = javaClass.simpleName

    @Inject
    lateinit var rxBus: RxBus

    lateinit var paginator: PublishProcessor<Int>
    var currentPage = 0

    var cd: CompositeDisposable = CompositeDisposable()

    override fun loadComment(postId: Int) {

        paginationSetup(postId)
        Log.d(TAG, "loadCommnet()")


        cd.add(rxBus.listen(CommentAdapter.CommentPaginator::class.java)
                .subscribe({
                    Log.d(TAG, "loadCommnet() pagination")
                    paginator.onNext(currentPage)
                }))
        //HACK
        cd.add(Observable.interval(1, TimeUnit.SECONDS)
                .subscribe({
                    Log.d(TAG, "loadCommnet() interval")
                    paginator.onNext(currentPage)
                }))
        paginator.onNext(currentPage)

        cd.add(
                RxSwipeRefreshLayout.refreshes(getView.getRefresh())
                        .subscribeBy(
                                onNext = {
                                    Log.d(TAG, "SWIPE - TO - REFRESH TRIGGERED")
                                    isLoading = false
                                    currentPage = 0
                                    isCacheDirty = true
                                    paginator.onNext(currentPage)
                                }
                        )
        )
    }

    private fun paginationSetup(postId: Int) {

        paginator = PublishProcessor.create()

        cd.add(paginator
                .onBackpressureDrop()
                .filter {
                    Log.d(TAG, "loading Sate $it Current Page $currentPage")
                    !isLoading
                }
                .doOnNext({ isLoading = true })
                .concatMap { _ ->
                    interactor.getCommentList(postId, isCacheDirty, currentPage)
                            .toFlowable()
                            .retryWhen { it -> it }
                }
                .filter { !it.isEmpty() }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ it ->
                    when (currentPage) {
                        0 -> {
                            getView.deleteAdapter()
                            getView.addToAdapter(it as ArrayList<Comment>)
                        }
                        else -> {
                            getView.addToAdapter(it as ArrayList<Comment>)
                        }
                    }

                    isLoading = false
                    currentPage++

                    when (getView.getRefresh().isRefreshing) {
                        true -> {
                            isCacheDirty = true
                            getView.getRefresh().isRefreshing = false
                        }
                    }

                }
                        , { err ->
                    isLoading = false
                    getView.getRefresh().isRefreshing = false

                }))
    }

    override fun onDetach() {
        super.onDetach()
        cd.dispose()
        restoreState()
    }

    fun restoreState() {
        getView.getRefresh().isRefreshing = false
        isLoading = false
    }

}