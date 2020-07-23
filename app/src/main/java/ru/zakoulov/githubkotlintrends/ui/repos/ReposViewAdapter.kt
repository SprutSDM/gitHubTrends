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

class ReposViewAdapter(
    initReposList: ReposList
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
        }
    }

    class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val authorName: TextView = view.findViewById(R.id.repo_author_name)
        private val authorIcon = (view.findViewById(R.id.repo_author_icon) as ImageView).apply {
            clipToOutline = true
        }
        private val title: TextView = view.findViewById(R.id.repo_title)
        private val description: TextView = view.findViewById(R.id.repo_description)

        fun setTitle(title: String) {
            this.title.text = title
        }

        fun setDescription(description: String) {
            this.description.text = description
        }

        fun setAuthorName(name: String) {
            this.authorName.text = name
        }

        fun setAuthorIcon(iconPath: String) {
            Picasso.get()
                .load(iconPath)
                .fit()
                .into(authorIcon)
        }
    }
}
