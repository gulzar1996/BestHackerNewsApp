package github.gulzar1996.besthackernewsapp.ui.detail.comment.di

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides
import github.gulzar1996.besthackernewsapp.ui.detail.comment.*

@Module
class CommentFragmentModule {

    @Provides
    internal fun provideCommentFInteractor(interactor: CommentFragmentInteractor):
            ICommentFragmentInteractor = interactor

    @Provides
    internal fun provideCommentFPresenter(
            presenter: CommentFragmentPresenter<ICommentFragmentView, ICommentFragmentInteractor>)
            : ICommentFragmentPresenter<ICommentFragmentView, ICommentFragmentInteractor> = presenter

    @Provides
    fun linearLayoutManager(context: Context) = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)


}