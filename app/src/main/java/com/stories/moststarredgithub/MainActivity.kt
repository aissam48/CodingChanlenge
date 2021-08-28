package com.stories.moststarredgithub

import android.content.pm.ActivityInfo
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stories.moststarredgithub.PackageModulData.ModulData
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var groupAdapter : GroupAdapter<GroupieViewHolder>
    private var page = 1
    private val retrofit = mRetrofit()
    private val mutableListOfItems = mutableListOf<Item>()
    private val mutableListOfTrendingRepos = mutableListOf<Item>()
    lateinit var section: Section
    private var isdownloaded = false
    private var isdownloadTrending = false
    private var inAll_or_inTrending = "InAll"
    private var pageTrending = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        window.statusBarColor = ContextCompat.getColor(this, R.color.grey)

        groupAdapter = GroupAdapter<GroupieViewHolder>()
        section = Section()

        groupAdapter.add(section)
        recycler.adapter = groupAdapter


        val layout = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)
        recycler.layoutManager = layout

        img_all_repos.setImageResource(R.drawable.ic_allblue)

        allRepos(page.toString())


        img_all_repos.setOnClickListener {
            if(inAll_or_inTrending == "InTrending"){

                progress.visibility = View.VISIBLE
                page = 1
                allRepos(page.toString())
                section.clear()
                inAll_or_inTrending = "InAll"

                img_all_repos.setImageResource(R.drawable.ic_allblue)
                img_star.setImageResource(R.drawable.ic_star)
            }
        }

        img_star.setOnClickListener {
            if(inAll_or_inTrending == "InAll"){

                progress.visibility = View.VISIBLE
                pageTrending = 1
                trendingRepos(pageTrending.toString())
                section.clear()
                inAll_or_inTrending = "InTrending"

                img_all_repos.setImageResource(R.drawable.ic_all)
                img_star.setImageResource(R.drawable.ic_starblue)
            }
        }


        recycler.addOnScrollListener(object :RecyclerView.OnScrollListener(){

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager

                if(isdownloaded &&  layoutManager.findLastCompletelyVisibleItemPosition() == mutableListOfItems.size-1 && inAll_or_inTrending == "InAll"){
                    allRepos(page.toString())
                    isdownloaded = false
                    progress.visibility = View.VISIBLE
                }

                if(isdownloadTrending && layoutManager.findLastCompletelyVisibleItemPosition() == mutableListOfTrendingRepos.size-1 && inAll_or_inTrending == "InTrending"){
                    trendingRepos(pageTrending.toString())
                    isdownloadTrending = false
                    progress.visibility = View.VISIBLE
                }


            }
        })
    }

    fun allRepos(pageN:String){

        retrofit.fRetrofit().create(GetData::class.java).getDataFromGithub("search/repositories?q=created:>2017-10-22&sort=stars&order=desc&page=$pageN").enqueue(object : Callback<ModulData>{
            override fun onResponse(call: Call<ModulData>?, response: Response<ModulData>?) {

                progress.visibility = View.GONE
                if (response!!.isSuccessful){
                    for (item in response.body().items){
                        val itemGroupAdapter = GroupieAdapter(item,applicationContext)
                        mutableListOfItems.add(itemGroupAdapter)
                        section.add(itemGroupAdapter)
                    }

                    groupAdapter.notifyDataSetChanged()
                    page++
                    isdownloaded = true

                }
            }

            override fun onFailure(call: Call<ModulData>?, t: Throwable?) {

            }

        })
    }

    fun trendingRepos(pageT : String){

        retrofit.fRetrofit().create(GetData::class.java).getDataFromGithub("search/repositories?q=created:>2017-10-22&sort=stars&order=desc&page=$pageT").enqueue(object : Callback<ModulData> {
            override fun onResponse(call: Call<ModulData>?, response: Response<ModulData>?) {

                progress.visibility = View.GONE
                if (response!!.isSuccessful) {

                    val allItems  = mutableListOf<com.stories.moststarredgithub.PackageModulData.Item>()

                    for (i in response.body().items){
                        allItems.add(i)
                    }

                    val lastItems = allItems.filter {
                        val hisTimeLong = it.created_at.replace("-","").replace("-","").replace("T","").replace(":","").replace(":","").replace("Z","").toLong()
                        hisTimeLong in 20171022000000..20171122000000
                    }

                    for (i in lastItems){
                        mutableListOfTrendingRepos.add(GroupieAdapter(i,applicationContext))
                        section.add(GroupieAdapter(i,applicationContext))
                    }

                    groupAdapter.notifyDataSetChanged()
                    pageTrending++
                    isdownloadTrending = true

                }
            }

            override fun onFailure(call: Call<ModulData>?, t: Throwable?) {

            }

        })


    }

}