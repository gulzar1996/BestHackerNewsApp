package github.gulzar1996.besthackernewsapp.data

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

public open class TopPostList(
        @PrimaryKey open var id: Long = 0,
        open var list: RealmList<String> = RealmList()
) : RealmObject()