package github.gulzar1996.besthackernewsapp.ui.detail

import android.app.Activity
import dagger.Module
import dagger.Provides


@Module
class DetailActivityModule {

    @Provides
    fun provideDetailPresenter(presenter: DetailPresenter<IDetailView, IDetailInteractor>)
            : IDetailPresenter<IDetailView, IDetailInteractor> = presenter

    @Provides
    fun provideDetailInteractor(interactor: DetailInteractor)
            : IDetailInteractor = interactor

    @Provides
    fun provideActivity(detailActivity: DetailActivity): Activity = detailActivity

    @Provides
    fun provideTabLayoutAdapter(activity: DetailActivity):
            ViewPagerAdapter = ViewPagerAdapter(activity.supportFragmentManager)


}