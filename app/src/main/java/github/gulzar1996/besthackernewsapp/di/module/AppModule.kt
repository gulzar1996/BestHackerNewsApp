package github.gulzar1996.besthackernewsapp.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import github.gulzar1996.besthackernewsapp.Const
import github.gulzar1996.besthackernewsapp.data.db.DataOperation
import github.gulzar1996.besthackernewsapp.data.network.HackerNewsAPI
import github.gulzar1996.besthackernewsapp.utils.rx.AppSchedulerProvider
import github.gulzar1996.besthackernewsapp.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context = application

    /**
     * Retrofit
     */
    @Provides
    internal fun provideHackerNewsApi(retrofit: Retrofit): HackerNewsAPI = retrofit.create(HackerNewsAPI::class.java)

    @Provides
    @Singleton
    internal fun provideRetrofitInterface(): Retrofit =
            Retrofit.Builder()
                    .baseUrl(Const.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()


    /**
     * DataOperation
     */

    @Provides
    fun provideDataOperation() = DataOperation()

    /**
     * Rx
     */

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()


}