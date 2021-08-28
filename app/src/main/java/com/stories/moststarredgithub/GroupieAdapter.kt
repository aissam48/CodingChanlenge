package com.stories.moststarredgithub

import android.content.Context
import android.widget.Toast
import com.squareup.picasso.Picasso
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_repos.*

class GroupieAdapter(val item: com.stories.moststarredgithub.PackageModulData.Item, val context:Context):Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.repos_name.text = item.name
        viewHolder.repos_description.text = item.description
        viewHolder.repos_owner_name.text = item.owner.login

        viewHolder.number_of_stars.text = item.stargazers_count.toString()
        Picasso.get().load(item.owner.avatar_url).into(viewHolder.repos_image)

        viewHolder.itemView.setOnClickListener {
            Toast.makeText(context, item.created_at, Toast.LENGTH_SHORT).show()
        }

    }

    override fun getLayout(): Int = R.layout.item_repos


}