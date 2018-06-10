package github.gulzar1996.besthackernewsapp.di.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import github.gulzar1996.besthackernewsapp.ui.detail.DetailActivity
import github.gulzar1996.besthackernewsapp.ui.home.HomeActivity
import github.gulzar1996.besthackernewsapp.ui.home.HomeActivityModule


@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [(HomeActivityModule::class)])
    abstract fun bindHomeActivity(): HomeActivity

//    @ContributesAndroidInjector(modules = [(DetailActivity::class)])
//    abstract fun bindDetailActivity(): DetailActivity

}