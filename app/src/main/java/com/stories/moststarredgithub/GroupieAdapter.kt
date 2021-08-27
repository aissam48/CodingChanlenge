package com.stories.moststarredgithub

import com.squareup.picasso.Picasso
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_repos.*

class GroupieAdapter(val item: com.stories.moststarredgithub.Item):Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.repos_name.text = item.name
        viewHolder.repos_description.text = item.description
        viewHolder.repos_owner_name.text = item.owner.login

        Picasso.get().load(item.owner.avatar_url).into(viewHolder.repos_image)

    }

    override fun getLayout(): Int = R.layout.item_repos


}