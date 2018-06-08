package github.gulzar1996.besthackernewsapp.data.network

import github.gulzar1996.besthackernewsapp.data.Comment
import github.gulzar1996.besthackernewsapp.data.Post
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface HackerNewsRemote {

    @GET("topstories.json?print=pretty")
    fun getTopPostId(): Observable<List<String>>

    @GET("item/{id}.json?print=pretty")
    fun getPostDetails(@Path("id") id: Int): Observable<Post>

    @GET("item/{id}.json?print=pretty")
    fun getCommentDetails(@Path("id") id: Int): Observable<Comment>

}