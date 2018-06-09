package github.gulzar1996.besthackernewsapp.utils.rx

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class RxBus {

    private val publisher = PublishSubject.create<Any>()

    fun publish(event: Any) {
        publisher.onNext(event)
    }

    fun <T> listen(eventType: Class<T>): Observable<T> = publisher.ofType(eventType)
}