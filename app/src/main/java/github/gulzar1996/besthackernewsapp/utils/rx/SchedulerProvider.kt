package github.gulzar1996.besthackernewsapp.utils.rx

import io.reactivex.Scheduler


interface SchedulerProvider {

    fun ui(): Scheduler

    fun computation(): Scheduler

    fun io(): Scheduler

}