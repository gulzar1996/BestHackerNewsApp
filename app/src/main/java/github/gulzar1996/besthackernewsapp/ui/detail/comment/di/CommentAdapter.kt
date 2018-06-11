package github.gulzar1996.besthackernewsapp.ui.detail.comment.di

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import github.gulzar1996.besthackernewsapp.R
import github.gulzar1996.besthackernewsapp.data.Comment
import github.gulzar1996.besthackernewsapp.utils.rx.RxBus
import kotlinx.android.synthetic.main.item_comment.view.*
import org.ocpsoft.prettytime.PrettyTime

import java.util.*

class CommentAdapter(val rxBus: RxBus) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var comments: ArrayList<Comment> = ArrayList()

    val preloadThreshold = 5

    val TAG = javaClass.simpleName

    fun addItems(posts: ArrayList<Comment>) {
        this.comments.addAll(posts)
        notifyItemInserted(comments.size - 1)

    }

    fun removeItems() {
        this.comments.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            CommentViewholder(LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false))

    override fun getItemCount(): Int = comments.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        positionTracker(position)
        (holder as CommentViewholder).bind(comments[position], rxBus)
    }


    private fun positionTracker(position: Int) {
        val lastPositionReached = (position + preloadThreshold) > comments.size
        Log.d(TAG, position.toString())
        if (lastPositionReached || comments.size == 0) {
            Log.d(TAG, "Subject triggered")

            rxBus.publish(CommentPaginator())
        }
    }


    class CommentViewholder(itemView: View?) : RecyclerView.ViewHolder(itemView), ViewH {
        override fun bind(comment: Comment, rxBus: RxBus) {
            itemView.description.text = comment.text
            itemView.author.text = comment.by
            itemView.datetime.text = PrettyTime().format(Date(comment.time.toLong() * 1000))
        }
    }

    interface ViewH {
        fun bind(comment: Comment, rxBus: RxBus)
    }

    open class CommentPaginator()

}