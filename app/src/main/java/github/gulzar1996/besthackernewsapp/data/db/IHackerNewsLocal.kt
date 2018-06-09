package github.gulzar1996.besthackernewsapp.data.db

import github.gulzar1996.besthackernewsapp.data.Comment
import github.gulzar1996.besthackernewsapp.data.Post
import github.gulzar1996.besthackernewsapp.data.PostList
import io.reactivex.Single

interface IHackerNewsLocal {

    /**
     *  inserting objects to the DB
     *  Okay so why am I returning then ?
     *  Realm write operation takes place in a different thread and doesn't immediately update the db.
     *  So, its better to return the un-managed object to prevent NPE!!
     */
    fun savePost(post: Post): Post

    fun saveTopPostList(ids: PostList): PostList

    fun saveComment(comment: Comment): Single<Comment>

    fun getLastRefreshedTime(): Single<Long>

    fun getPost(postId: Int): Single<Post>

    fun getComment(commentId: Int): Single<Comment>

    fun getTopPostId(): Single<List<String>>

}