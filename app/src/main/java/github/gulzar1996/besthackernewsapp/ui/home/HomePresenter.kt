package github.gulzar1996.besthackernewsapp.ui.home

import android.util.Log
import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout
import github.gulzar1996.besthackernewsapp.data.Post
import github.gulzar1996.besthackernewsapp.ui.base.BasePresenter
import github.gulzar1996.besthackernewsapp.utils.rx.RxBus
import github.gulzar1996.besthackernewsapp.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.PublishProcessor
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class HomePresenter<V : IHomeView, I : IHomeInteractor>
@Inject
constructor(schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable, interactor: I)
    : BasePresenter<V, I>(schedulerProvider, compositeDisposable, interactor),
        IHomePresenter<V, I> {


    var isCacheDirty = false
    var isListCacheDirty = false
    var isLoading = false

    val TAG: String = javaClass.simpleName

    @Inject
    lateinit var rxBus: RxBus

    lateinit var paginator: PublishProcessor<Int>
    var currentPage = 0

    init {
        loadPost()
    }

    override fun paginationSetup() {

        compositeDisposable.add(rxBus.listen(HackerNewsAdapter.HackerNewsPaginator::class.java)
                .subscribe({
                    paginator.onNext(currentPage)
                }))

        compositeDisposable.add(
                RxSwipeRefreshLayout.refreshes(getView.getRefresh())
                        .subscribeBy(
                                onNext = {
                                    Log.d(TAG, "SWIPE - TO - REFRESH TRIGGERED")
                                    isLoading = false
                                    currentPage = 0
                                    isCacheDirty = true
                                    isListCacheDirty = true
                                    paginator.onNext(currentPage)
                                }
                        )
        )
    }

    fun loadPost() {


        paginator = PublishProcessor.create()

        compositeDisposable.add(paginator
                .onBackpressureDrop()
                .filter {
                    Log.d(TAG, "loading Sate $it Current Page $currentPage")
                    !isLoading
                }
                .doOnNext({ isLoading = true })
                .concatMap { _ ->
                    interactor.getHackerNews(currentPage, isCacheDirty, isListCacheDirty)
                            .toFlowable()
                            .retryWhen { it -> it }
                }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ it ->
                    when (currentPage) {
                        0 -> {
                            getView.deleteAdapter()
                            getView.addToAdapter(it as ArrayList<Post>)
                        }
                        else -> getView.addToAdapter(it as ArrayList<Post>)
                    }

                    loadRefreshedTime()
                    isLoading = false
                    currentPage++

                    when (getView.getRefresh().isRefreshing) {
                        true -> {
                            isListCacheDirty = false
                            isCacheDirty = true
                            getView.getRefresh().isRefreshing = false
                            getView.showToast("Refreshed")
                        }
                    }

                }
                        , { err ->
                    isLoading = false
                    getView.showToast(err.toString())
                    getView.getRefresh().isRefreshing = false

                }))
    }

    private fun loadRefreshedTime() {
        compositeDisposable.add(interactor.getLastRefreshedTime()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .filter { !it.isEmpty() }
                .subscribe({ getView.setLastRefershedTime(it) },
                        { e ->
                            Log.e(TAG, e.toString())
                        }))
    }
}