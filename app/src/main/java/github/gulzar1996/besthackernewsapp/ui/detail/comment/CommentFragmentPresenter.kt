package github.gulzar1996.besthackernewsapp.ui.detail.comment

import github.gulzar1996.besthackernewsapp.ui.base.BasePresenter
import github.gulzar1996.besthackernewsapp.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class CommentFragmentPresenter<V : ICommentFragmentView, I : ICommentFragmentInteractor>
@Inject constructor(interactor: I, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V, I>(schedulerProvider, compositeDisposable, interactor),
        ICommentFragmentPresenter<V, I> {

}