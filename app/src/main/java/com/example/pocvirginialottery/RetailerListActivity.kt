package com.example.pocvirginialottery

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pocvirginialottery.retroclient.Api
import com.example.pocvirginialottery.retroclient.ApiClient
import kotlinx.android.synthetic.main.activity_retailer_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetailerListActivity : BaseActivity() {

    lateinit var adapter: RetailerListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreen()
        setContentView(R.layout.activity_retailer_list)
        init()
    }

    private fun init() {
        backButton.setOnClickListener {
            finish()
        }
        var list = mutableListOf<Result>()
        recyclerViewSetUp()
        adapter.setDataList(list)
        callPlacesApi()
    }

    private fun callPlacesApi() {
        val client = ApiClient.getClient()
        val api = client?.create(Api::class.java)
        api?.getPlaces("virginia+lottery+retailers", Constants.API_KEY)
            ?.enqueue(object : Callback<PlacesModel> {
                override fun onFailure(call: Call<PlacesModel>, t: Throwable) {

                }

                override fun onResponse(call: Call<PlacesModel>, response: Response<PlacesModel>) {
                    adapter.setDataList(response.body()!!.results as MutableList<Result>)
                    adapter.notifyDataSetChanged()
                }
            })
    }

    private fun recyclerViewSetUp() {
        adapter = RetailerListAdapter()
        val layoutManger = LinearLayoutManager(this)
        retaileRecyclerView.layoutManager = layoutManger
        retaileRecyclerView.adapter = adapter
    }
}
