package github.gulzar1996.besthackernewsapp.ui.detail

import github.gulzar1996.besthackernewsapp.ui.base.BasePresenter
import github.gulzar1996.besthackernewsapp.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import org.ocpsoft.prettytime.PrettyTime
import java.util.*
import javax.inject.Inject

class DetailPresenter<V : IDetailView, I : IDetailInteractor>
@Inject
constructor(schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable, interactor: I)
    : BasePresenter<V, I>(schedulerProvider, compositeDisposable, interactor),
        IDetailPresenter<V, I> {

    override fun loadDetails(id: Int) {
        compositeDisposable.add(interactor.getPost(id)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({
                    getView.showTitle(it.title)
                    getView.showAuthor(it.by)
                    getView.showTime(PrettyTime().format(Date(it.time * 1000)))
                    when (it.type) {
                        "story" -> {
                            getView.addCommentFragment(id, it.kids.size)
                            getView.addWebViewFragmet(id)
                            getView.setUpTabs()
                            getView.showUrl(it.url)

                        }
                        else -> {
                            getView.addWebViewFragmet(id)
                            getView.setUpTabs()
                        }
                    }
                }, {
                    getView.showToast(it.toString())
                }))
    }

}