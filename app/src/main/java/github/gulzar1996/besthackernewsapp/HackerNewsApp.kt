package github.gulzar1996.besthackernewsapp

import android.app.Activity
import android.app.Application
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import github.gulzar1996.besthackernewsapp.di.component.DaggerAppComponent
import io.realm.Realm
import javax.inject.Inject

class HackerNewsApp : Application(), HasActivityInjector {

    @Inject
    lateinit internal var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>


    override fun activityInjector() = activityDispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this)
    }

}
