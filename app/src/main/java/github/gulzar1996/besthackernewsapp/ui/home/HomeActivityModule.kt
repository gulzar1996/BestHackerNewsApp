package github.gulzar1996.besthackernewsapp.ui.home

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides
import github.gulzar1996.besthackernewsapp.utils.rx.RxBus
import javax.inject.Singleton

@Module
class HomeActivityModule {

    @Provides
    fun provideHomePresenter(presenter: HomePresenter<IHomeView, IHomeInteractor>)
            : IHomePresenter<IHomeView, IHomeInteractor> = presenter

    @Provides
    fun provideHomeInteractor(interactor: HomeInteractor)
            : IHomeInteractor = interactor

    @Provides
    fun linearLayoutManager(context: Context) = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

}