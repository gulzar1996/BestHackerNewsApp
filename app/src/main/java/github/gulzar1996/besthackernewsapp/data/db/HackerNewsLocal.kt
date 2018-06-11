package github.gulzar1996.besthackernewsapp.data.db

import github.gulzar1996.besthackernewsapp.data.Comment
import github.gulzar1996.besthackernewsapp.data.Post
import github.gulzar1996.besthackernewsapp.data.PostList
import io.reactivex.Single
import io.realm.Realm
import io.realm.kotlin.where

class HackerNewsLocal : IHackerNewsLocal {


    /**
     * Queries post by postId from persistence
     * @param postId
     */
    override fun getPost(postId: Int): Single<Post> =
            Single.create({
                try {
                    val r: Realm = Realm.getDefaultInstance()
                    val post: Post? = r.where<Post>().equalTo(Post.ID, postId).findFirst()
                    return@create when (post) {
                        null -> {
                            r.close()
                            it.onError(NullPointerException("Not found"))
                        }
                        else -> {
                            val t = r.copyFromRealm(post)
                            r.close()
                            it.onSuccess(t)
                        }
                    }
                } catch (e: Exception) {
                    it.onError(e)
                }
            })

    /**
     * Retrieves List of top post id from persistence
     */

    override fun getTopPostId(): Single<List<String>> = Single.create({
        try {
            val r: Realm = Realm.getDefaultInstance()
            val postList: PostList? = r.where<PostList>().findFirst()
            return@create when (postList) {
                null -> {
                    r.close()
                    it.onError(NullPointerException("Not found"))
                }
                else -> {
                    val t = r.copyFromRealm(postList).list
                    r.close()
                    it.onSuccess(t)
                }
            }
        } catch (e: Exception) {
            it.onError(e)
        }
    })

    /**
     * Saves post to persistence
     * @param postId
     */

    override fun savePost(post: Post): Post {
        val r: Realm = Realm.getDefaultInstance()
        try {
            r.executeTransaction({
                r.insertOrUpdate(post)
            })
        } catch (e: Exception) {
        } finally {
            r.close()
        }
        return post
    }

    override fun getComment(commentId: Int): Single<Comment> =
            Single.create({
                try {
                    val r: Realm = Realm.getDefaultInstance()
                    val comment: Comment? = r.where<Comment>().equalTo(Post.ID, commentId).findFirst()
                    return@create when (comment) {
                        null -> {
                            r.close()
                            it.onError(NullPointerException("Not found"))
                        }
                        else -> {
                            val t = r.copyFromRealm(comment)
                            r.close()
                            it.onSuccess(t)
                        }
                    }
                } catch (e: Exception) {
                    it.onError(e)
                }
            })


    /**
     * Save top post Id List and timestamp to persistence to persistence
     * @param postId
     */

    override fun saveTopPostList(ids: PostList): PostList {
        val r: Realm = Realm.getDefaultInstance()
        try {
            r.executeTransaction({
                r.insertOrUpdate(ids)
            })
        } catch (e: Exception) {
        } finally {
            r.close()
        }
        return ids
    }

    override fun getLastRefreshedTime(): Single<Long> = Single.create({
        try {
            val r: Realm = Realm.getDefaultInstance()
            val postList: PostList? = r.where<PostList>().findFirst()
            return@create when (postList) {
                null -> {
                    r.close()
                    it.onError(NullPointerException("Not found"))
                }
                else -> {
                    val t = r.copyFromRealm(postList).timeStamp
                    r.close()
                    it.onSuccess(t)
                }
            }
        } catch (e: Exception) {
            it.onError(e)
        }
    })

    override fun saveComment(comment: Comment): Comment {
        val r: Realm = Realm.getDefaultInstance()
        try {
            r.executeTransaction({
                r.insertOrUpdate(comment)
            })
        } catch (e: Exception) {
        } finally {
            r.close()
        }
        return comment
    }

}