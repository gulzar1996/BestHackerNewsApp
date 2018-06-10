package github.gulzar1996.besthackernewsapp.ui.detail

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides

@Module
class DetailActivityModule {

    @Provides
    fun provideDetailPresenter(presenter: DetailPresenter<IDetailView, IDetailInteractor>)
            : IDetailPresenter<IDetailView, IDetailInteractor> = presenter

    @Provides
    fun provideHomeInteractor(interactor: DetailInteractor)
            : IDetailInteractor = interactor

    @Provides
    fun linearLayoutManager(context: Context) = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

}