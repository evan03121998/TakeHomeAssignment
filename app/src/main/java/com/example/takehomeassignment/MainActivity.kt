package com.example.takehomeassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private var list = ArrayList<User>()
    private lateinit var support: Support
    //private val list = ArrayList<PostResponse>()

    private lateinit var rvPost : RecyclerView
    private lateinit var tvResponseCode : TextView
    private lateinit var adapter: PostAdapter
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private fun initComponents(){
        rvPost = findViewById(R.id.rvPost)
        tvResponseCode = findViewById(R.id.tvResponseCode)
        swipeRefresh = findViewById(R.id.swipeRefresh)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponents()

        rvPost.setHasFixedSize(true)
        rvPost.layoutManager = LinearLayoutManager(this)

        getData()

        swipeRefresh.setOnRefreshListener {
            getData()
        }

    }

    fun getData(){
        RetrofitClient.instance.getUsers().enqueue(object : Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if(swipeRefresh.isRefreshing){
                    swipeRefresh.isRefreshing = false
                }
                tvResponseCode.text = response.code().toString()
                val page = response.body()?.page
                val per_page = response.body()?.per_page
                val total = response.body()?.total
                val total_pages = response.body()?.total_pages

                val supportResponse = response.body()?.support
                if(supportResponse != null){
                    support = supportResponse
                }

                tvResponseCode.text = "${support.url}\n${support.text}"

                val listResponse = response.body()?.data
                if (listResponse != null) {
                    list = listResponse
                }
                adapter = PostAdapter(list){index -> deleteItem(index)}
                rvPost.adapter = adapter

            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                tvResponseCode.text = t.message
            }
        })
    }

    fun deleteItem(index: Int){
        list.removeAt(index)
        adapter.setItems(list)
    }
}