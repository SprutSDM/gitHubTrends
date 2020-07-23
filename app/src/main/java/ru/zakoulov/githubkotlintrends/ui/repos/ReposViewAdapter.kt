package ru.zakoulov.githubkotlintrends.ui.repos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.zakoulov.githubkotlintrends.R
import ru.zakoulov.githubkotlintrends.data.ReposList
import ru.zakoulov.githubkotlintrends.formatToShort

class ReposViewAdapter(
    initReposList: ReposList,
    private val callbacks: ReposCallbacks
) : RecyclerView.Adapter<ReposViewAdapter.RepoViewHolder>() {

    var reposList: ReposList = initReposList
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val repoItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_repo_view, parent, false) as View
        return RepoViewHolder(repoItem)
    }

    override fun getItemCount() = reposList.repos.size

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = reposList.repos[position]
        holder.apply {
            setTitle(repo.name)
            setDescription(repo.description)
            setAuthorName(repo.author)
            setAuthorIcon(repo.avatar)
            setStars(repo.stars)
            setForks(repo.forks)
        }
        holder.view.setOnClickListener {
            callbacks.onClick(repo)
        }
    }

    class RepoViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val authorName: TextView = view.findViewById(R.id.repo_author_name)
        private val authorIcon = (view.findViewById(R.id.repo_author_icon) as ImageView).apply {
            clipToOutline = true
        }
        private val title: TextView = view.findViewById(R.id.repo_title)
        private val description: TextView = view.findViewById(R.id.repo_description)
        private val stars: TextView = view.findViewById(R.id.repo_stars)
        private val forks: TextView = view.findViewById(R.id.repo_forks)

        fun setTitle(title: String) {
            this.title.text = title
        }

        fun setDescription(description: String) {
            this.description.text = description
        }

        fun setAuthorName(name: String) {
            this.authorName.text = name
        }

        fun setStars(stars: Int) {
            this.stars.text = stars.formatToShort()
        }

        fun setForks(forks: Int) {
            this.forks.text = forks.formatToShort()
        }

        fun setAuthorIcon(iconPath: String) {
            Picasso.get()
                .load(iconPath)
                .fit()
                .into(authorIcon)
        }
    }

    companion object {
        private const val TAG = "ReposViewAdapter"
    }
}
