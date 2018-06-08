package github.gulzar1996.besthackernewsapp.ui.base

import github.gulzar1996.besthackernewsapp.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

open class BasePresenter<V : IBaseView, I : IBaseInteractor>
@Inject constructor(
        var schedulerProvider: SchedulerProvider,
        var compositeDisposable: CompositeDisposable,
        var interactor: I

) : IBasePresenter<V, I> {


    lateinit var getView: V
    lateinit var getInteractor: I

    override fun onAttach(baseView: V) {
        getView = baseView
    }

    override fun onDetach() {
        compositeDisposable.dispose()
    }

}