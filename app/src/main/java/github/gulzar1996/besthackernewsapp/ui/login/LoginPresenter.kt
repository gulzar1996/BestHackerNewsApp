package github.gulzar1996.besthackernewsapp.ui.detail

import github.gulzar1996.besthackernewsapp.ui.base.BasePresenter
import github.gulzar1996.besthackernewsapp.ui.login.ILoginView
import github.gulzar1996.besthackernewsapp.ui.login.IloginInteractor
import github.gulzar1996.besthackernewsapp.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class LoginPresenter<V : ILoginView, I : IloginInteractor>
@Inject
constructor(schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable, interactor: I)
    : BasePresenter<V, I>(schedulerProvider, compositeDisposable, interactor),
        ILoginPresenter<V, I> {

    override fun onSignInButtonClick() {
        getView.showSignInDialog()
    }

}