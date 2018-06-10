package github.gulzar1996.besthackernewsapp.ui.home

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import github.gulzar1996.besthackernewsapp.R
import github.gulzar1996.besthackernewsapp.data.Post
import github.gulzar1996.besthackernewsapp.utils.rx.RxBus
import kotlinx.android.synthetic.main.item_news_card.view.*
import org.ocpsoft.prettytime.PrettyTime
import java.util.*


class HackerNewsAdapter(val rxBus: RxBus) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_NEWS_CARD = 0
        const val TYPE_PROGRESS = 1
    }

    val preloadThreshold = 5

    var posts: ArrayList<Post> = ArrayList()

    fun addItems(posts: ArrayList<Post>) {
        this.posts.addAll(posts)
        notifyItemInserted(posts.size)
    }

    fun removeItems() {
        this.posts.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            when (viewType) {
                TYPE_NEWS_CARD -> NewsCardViewholder(LayoutInflater.from(parent.context).inflate(R.layout.item_news_card, parent, false))
                else -> ProgressViewholder(LayoutInflater.from(parent.context).inflate(R.layout.item_progress, parent, false))
            }


    override fun getItemViewType(position: Int) =
            when (position) {
                posts.size -> TYPE_PROGRESS
                else -> TYPE_NEWS_CARD
            }

    override fun getItemCount(): Int = posts.size + 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            TYPE_NEWS_CARD -> (holder as ViewH).bind(posts[position], rxBus)
            TYPE_PROGRESS -> holder
        }
        positionTracker(position)
    }

    private fun positionTracker(position: Int) {
        val lastPositionReached = (position + preloadThreshold) > posts.size - 1
        if (lastPositionReached || posts.size == 0) {
            rxBus.publish(HackerNewsPaginator())
        }
    }

    class NewsCardViewholder(itemView: View?) : RecyclerView.ViewHolder(itemView), ViewH {
        override fun bind(post: Post, rxBus: RxBus) {
            itemView.header_label.text = post.title
            itemView.upVotes.text = post.score.toString()
            itemView.url.text = post.url
            itemView.descendant.text = post.descendants
            itemView.by.text = post.by
            itemView.time.text = PrettyTime().format(Date(post.time * 1000))
            itemView.setOnClickListener { rxBus.publish(HackerNewsClick(post.id)) }

        }
    }

    class ProgressViewholder(itemView: View?) : RecyclerView.ViewHolder(itemView)

    interface ViewH {
        fun bind(post: Post, rxBus: RxBus)
    }

    open class HackerNewsPaginator()

    data class HackerNewsClick(val id: Long)

}