package github.gulzar1996.besthackernewsapp.di.component

import dagger.Subcomponent
import dagger.android.AndroidInjector
import github.gulzar1996.besthackernewsapp.ui.detail.DetailActivity
import github.gulzar1996.besthackernewsapp.ui.detail.DetailActivityModule

@Subcomponent(modules = [(DetailActivityModule::class)])
interface DetailActivityComponent : AndroidInjector<DetailActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<DetailActivity>() {}

}