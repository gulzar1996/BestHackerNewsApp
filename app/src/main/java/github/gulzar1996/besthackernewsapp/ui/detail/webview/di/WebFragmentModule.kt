package github.gulzar1996.besthackernewsapp.ui.detail.webview.di

import dagger.Module
import dagger.Provides
import github.gulzar1996.besthackernewsapp.ui.detail.webview.*

@Module
class WebFragmentModule {

    @Provides
    internal fun provideWebFInteractor(interactor: WebFragmentInteractor): IWebFragmentInteractor = interactor

    @Provides
    internal fun provideWebFPresenter(presenter: WebFragmentPresenter<IWebFragmentView, IWebFragmentInteractor>)
            : IWebFragmentPresenter<IWebFragmentView, IWebFragmentInteractor> = presenter

}