package github.gulzar1996.besthackernewsapp.ui.detail.webview

import github.gulzar1996.besthackernewsapp.ui.base.BasePresenter
import github.gulzar1996.besthackernewsapp.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class WebFragmentPresenter<V : IWebFragmentView, I : IWebFragmentInteractor>
@Inject constructor(interactor: I, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V, I>(schedulerProvider, compositeDisposable, interactor), IWebFragmentPresenter<V, I> {

}
