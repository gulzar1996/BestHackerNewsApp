package github.gulzar1996.besthackernewsapp.data.network

import io.reactivex.Observable
import org.json.JSONArray
import retrofit2.http.GET
import retrofit2.http.Path

interface HackerNewsAPI {

    @GET("/askstories.json?print=pretty")
    fun getTopPostId(): Observable<JSONArray>

    @GET("/item/{id}.json?print=pretty")
    fun getPostDetails(@Path("id") id: Int): Observable<Post>

    @GET("/item/{id}.json?print=pretty")
    fun getCommentDetails(@Path("id") id: Int): Observable<Comment>

}