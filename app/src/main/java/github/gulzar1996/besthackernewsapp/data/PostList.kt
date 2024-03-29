package github.gulzar1996.besthackernewsapp.data

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

public open class PostList(
        @PrimaryKey open var id: Long = 0,
        open var list: RealmList<String> = RealmList(),
        open var timeStamp: Long = 0
) : RealmObject()