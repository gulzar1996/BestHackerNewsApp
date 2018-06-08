package github.gulzar1996.besthackernewsapp.data

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Comment(
        @SerializedName("by") open var by: String = "",
        @SerializedName("id") @PrimaryKey open var id: Long = 0,
        @SerializedName("kids") open var kids: RealmList<String> = RealmList(),
        @SerializedName("parent") open var parent: Int = 0,
        @SerializedName("text") open var text: String = "",
        @SerializedName("time") open var time: Int = 0,
        @SerializedName("type") open var type: String = ""
) : RealmObject()