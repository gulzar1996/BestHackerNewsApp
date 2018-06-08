package github.gulzar1996.besthackernewsapp.ui.home

import android.util.Log
import github.gulzar1996.besthackernewsapp.ui.base.BasePresenter
import github.gulzar1996.besthackernewsapp.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class HomePresenter<V : IHomeView, I : IHomeInteractor>
@Inject
constructor(schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable, interactor: I)
    : BasePresenter<V, I>(schedulerProvider, compositeDisposable, interactor),
        IHomePresenter<V, I> {

    var page = 0

    val TAG: String = javaClass.simpleName

    override fun loadInitial() {
        compositeDisposable.add(
                interactor.getHackerNews(page)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe({ it -> Log.d(TAG, it.size.toString()) },
                                { err -> Log.d(TAG, err.localizedMessage) })
        )
    }

    fun loadPost() {
        compositeDisposable.addAll(
                interactor.getPostFromDisk(17267368)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe({ it ->
                            Log.d(TAG, it?.title)
                        }, { err -> Log.d(TAG, err.localizedMessage) }))
    }

}