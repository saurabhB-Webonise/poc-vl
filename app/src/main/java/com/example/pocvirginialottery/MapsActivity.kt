package com.example.pocvirginialottery

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
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


    lateinit var latlngList:MutableList<LatLng>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreen()
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        recyclerViewSetUp()
        callPlacesApi()
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
       // mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    private fun goto(loction:LatLng,title:String){
        mMap.addMarker(MarkerOptions().position(loction).title(title))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(loction))
    }

    private fun zoomTo(pos:Int){
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlngList[pos]))
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                latlngList[pos], 12.0f
            )
        )
    }

    private fun recyclerViewSetUp() {
        adapter = RetailerListAdapter()
        var list= mutableListOf<Result>()
        adapter.setDataList(list)

        adapter.setlistner(listner)

        val layoutManger = LinearLayoutManager(this)
        layoutManger.orientation=LinearLayoutManager.HORIZONTAL
        retaileRecyclerView.layoutManager = layoutManger
        retaileRecyclerView.adapter = adapter
    }


    val listner= View.OnClickListener {
        zoomTo(it.tag as Int)
    }


    private fun callPlacesApi(){
        val client = ApiClient.getClient()
        val api = client?.create(Api::class.java)
        api?.getPlaces("virginia+lottery+retailers", Constants.API_KEY)
            ?.enqueue(object : Callback<PlacesModel> {
                override fun onFailure(call: Call<PlacesModel>, t: Throwable) {

                }
                override fun onResponse(call: Call<PlacesModel>, response: Response<PlacesModel>) {
                    adapter.setDataList(response.body()!!.results as MutableList<Result>)
                    adapter.notifyDataSetChanged()
                    latlngList= mutableListOf<LatLng>()
                    latlngList.clear()
                    for(x in response.body()!!.results){
                        val loc=LatLng(x.geometry.location.lat,x.geometry.location.lng)
                        latlngList.add(loc)
                        goto(loc,"${x.formatted_address}")
                    }
                }
            })
    }

}