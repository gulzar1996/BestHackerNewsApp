package github.gulzar1996.besthackernewsapp.ui.home

import dagger.Module
import dagger.Provides

@Module
class HomeActivityModule {

    @Provides
    fun provideHomePresenter(presenter: HomePresenter<IHomeView, IHomeInteractor>)
            : IHomePresenter<IHomeView, IHomeInteractor> = presenter

    @Provides
    fun provideHomeInteractor(interactor: HomeInteractor)
            : IHomeInteractor = interactor


}