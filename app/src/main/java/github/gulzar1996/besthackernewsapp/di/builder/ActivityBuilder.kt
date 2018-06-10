package github.gulzar1996.besthackernewsapp.di.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import github.gulzar1996.besthackernewsapp.ui.detail.DetailActivity
import github.gulzar1996.besthackernewsapp.ui.detail.DetailActivityModule
import github.gulzar1996.besthackernewsapp.ui.detail.LoginActivityModule
import github.gulzar1996.besthackernewsapp.ui.home.HomeActivity
import github.gulzar1996.besthackernewsapp.ui.home.HomeActivityModule
import github.gulzar1996.besthackernewsapp.ui.login.LoginActivity


@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [(HomeActivityModule::class)])
    abstract fun bindHomeActivity(): HomeActivity

    @ContributesAndroidInjector(modules = [(LoginActivityModule::class)])
    abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [(DetailActivityModule::class)])
    abstract fun bindDetailActivity(): DetailActivity

}