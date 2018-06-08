package github.gulzar1996.besthackernewsapp.data

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

public open class Post(
        @SerializedName("id") public @PrimaryKey open var id: Long = 0,
        @SerializedName("title") public open var title: String = "",
        @SerializedName("by") public open var by: String = "",
        @SerializedName("descendants") public open var descendants: String = "",
        @SerializedName("kids") public open var kids: RealmList<String> = RealmList(),
        @SerializedName("score") public open var score: Int = 0,
        @SerializedName("time") public open var time: Long = 0,
        @SerializedName("type") public open var type: String = "",
        @SerializedName("url") public open var url: String = ""
) : RealmObject() {
    companion object {
        val ID = "id"
    }
}