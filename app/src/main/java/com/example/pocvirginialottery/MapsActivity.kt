package com.example.pocvirginialottery

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pocvirginialottery.retroclient.Api
import com.example.pocvirginialottery.retroclient.ApiClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_retailer_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MapsActivity : BaseActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    lateinit var adapter: RetailerListAdapter
    lateinit var latlngList: MutableList<LatLng>
    var builder: AlertDialog.Builder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreen()
        setContentView(R.layout.activity_maps)
        builder = AlertDialog.Builder(this)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        recyclerViewSetUp()
        callPlacesApi()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }

    private fun addMarkers(loction: LatLng, title: String) {
        mMap.addMarker(MarkerOptions().position(loction).title(title))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(loction))
    }

    // Zooming for particular lat long
    private fun zoomTo(pos: Int) {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlngList[pos]))
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                latlngList[pos], 12.0f
            )
        )
    }

    private fun recyclerViewSetUp() {
        adapter = RetailerListAdapter()
        var list = mutableListOf<Result>()
        adapter.setDataList(list)
        adapter.setlistener(listner)
        val layoutManger = LinearLayoutManager(this)
        layoutManger.orientation = LinearLayoutManager.HORIZONTAL
        retaileRecyclerView.layoutManager = layoutManger
        retaileRecyclerView.adapter = adapter
    }

    // Click listener of horizontal list
    val listner = View.OnClickListener {
        zoomTo(it.tag as Int)
    }

    //APi calling
    private fun callPlacesApi() {
        val client = ApiClient.getClient()
        val api = client?.create(Api::class.java)

        var intentdata = intent.extras?.get("ZIPCODE")
        var zipcode: String = ""
        var query = "virginia+lottery+retailers"
        if (intentdata != null) {
            zipcode = intentdata as String
            query = "$query+$zipcode"
        }
        Log.i("zipcode", query)

        api?.getPlaces(query, Constants.API_KEY)
            ?.enqueue(object : Callback<PlacesModel> {
                override fun onFailure(call: Call<PlacesModel>, t: Throwable) {

                }

                override fun onResponse(call: Call<PlacesModel>, response: Response<PlacesModel>) {

                    Log.i("respo","${response.body()!!.results.size}")
                    if (response.body()!!.results.isNullOrEmpty()) {
                        // dialog for going
                        dialog()
                    }else{
                        adapter.setDataList(response.body()!!.results as MutableList<Result>)
                        adapter.notifyDataSetChanged()
                        latlngList = mutableListOf<LatLng>()
                        latlngList.clear()
                        for (x in response.body()!!.results) {
                            val loc = LatLng(x.geometry.location.lat, x.geometry.location.lng)
                            latlngList.add(loc)
                            addMarkers(loc, "${x.formatted_address}")
                        }
                    }

                }
            })
    }


    private fun dialog() {
        builder!!.setMessage(" No Retailer record found")
            .setCancelable(false)
            .setPositiveButton("ok") { dialog, id ->
                finish()
            }
        val alert = builder!!.create()
        alert.setTitle("Alert")
        alert.show()
    }
}
