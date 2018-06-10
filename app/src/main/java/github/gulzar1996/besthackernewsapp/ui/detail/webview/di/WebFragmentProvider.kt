package github.gulzar1996.besthackernewsapp.ui.detail.webview.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import github.gulzar1996.besthackernewsapp.ui.detail.webview.WebFragment

@Module
internal abstract class WebFragmentProvider {

    @ContributesAndroidInjector(modules = [WebFragmentModule::class])
    internal abstract fun provideWebFragmentFactory(): WebFragment
}