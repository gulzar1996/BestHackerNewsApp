package github.gulzar1996.besthackernewsapp.ui.detail

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides
import github.gulzar1996.besthackernewsapp.ui.login.ILoginView
import github.gulzar1996.besthackernewsapp.ui.login.IloginInteractor

@Module
class LoginActivityModule {

    @Provides
    fun provideDetailPresenter(presenter: LoginPresenter<ILoginView, IloginInteractor>)
            : ILoginPresenter<ILoginView, IloginInteractor> = presenter

    @Provides
    fun provideLoginInteractor(interactor: LoginInteractor)
            : IloginInteractor = interactor


}