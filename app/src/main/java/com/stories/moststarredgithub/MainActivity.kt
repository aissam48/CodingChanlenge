package com.stories.moststarredgithub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AbsListView
import android.widget.Toast
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
    lateinit var section: Section
    var isdownloaded = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        groupAdapter = GroupAdapter<GroupieViewHolder>()
        section = Section()

        groupAdapter.add(section)
        recycler.adapter = groupAdapter

        val layout = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)
        recycler.layoutManager = layout

        allRepos(page.toString())


        recycler.addOnScrollListener(object :RecyclerView.OnScrollListener(){

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalItems = layout.itemCount
                val visibleItems = layout.childCount
                val itemsScrolled = layout.findFirstVisibleItemPosition()
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager

                if(isdownloaded &&  layoutManager.findLastCompletelyVisibleItemPosition() == mutableListOfItems.size-1){
                    allRepos(page.toString())
                    Toast.makeText(applicationContext,"last $page",Toast.LENGTH_LONG).show()
                    isdownloaded = false
                }

            }
        })

    }

    fun allRepos(pageN:String){

        retrofit.fRetrofit().create(GetData::class.java).getDataFromGithub("search/repositories?q=created:>2017-10-22&sort=stars&order=desc&page=$pageN").enqueue(object : Callback<ModulData>{
            override fun onResponse(call: Call<ModulData>?, response: Response<ModulData>?) {
                if (response!!.isSuccessful){
                    for (item in response.body().items){
                        val itemGroupAdapter = GroupieAdapter(item)
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

    fun trendingRepos(page : String){



    }

}