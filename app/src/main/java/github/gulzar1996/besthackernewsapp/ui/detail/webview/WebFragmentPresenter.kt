package github.gulzar1996.besthackernewsapp.ui.detail.webview

import android.util.Log
import github.gulzar1996.besthackernewsapp.ui.base.BasePresenter
import github.gulzar1996.besthackernewsapp.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class WebFragmentPresenter<V : IWebFragmentView, I : IWebFragmentInteractor>
@Inject constructor(interactor: I, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V, I>(schedulerProvider, compositeDisposable, interactor), IWebFragmentPresenter<V, I> {

    override fun loadUrl(id: Int) {

        interactor.getPostUrl(id)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({
                    getView.loadUrl(url = it)
                }, { er ->
                    Log.d("WebFragmentPresenter", er.toString())
                })
    }

}
