package com.example.pocvirginialottery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_retailer.view.*

class RetailerListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var list: MutableList<Result>
    lateinit var onClickListener: View.OnClickListener

    fun setDataList(list: MutableList<Result>) {
        this.list = list
    }

    fun setlistener(onClickListener: View.OnClickListener) {
        this.onClickListener = onClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_retailer, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tag = position
        val PHOTO_REFERENCE =
            if (list[position].photos != null) list[position].photos[0].photo_reference else ""
        val MAX_HEIGHT = "100"
        val MAX_WIDTH = "100"
        val KEY = Constants.API_KEY
        val url =
            "https://maps.googleapis.com/maps/api/place/photo?photoreference=$PHOTO_REFERENCE&sensor=false&maxheight=$MAX_HEIGHT&maxwidth=$MAX_WIDTH&key=$KEY"

        Glide.with(holder.itemView)
            .load(url)
            .centerCrop()
            .placeholder(R.drawable.logo)
            .into(holder.itemView.image)

        holder.itemView.name.text = list[position].name
        holder.itemView.address.text = list[position].formatted_address
        holder.itemView.setOnClickListener(onClickListener)

        if(list[position].opening_hours!=null){
            holder.itemView.openclose.text= if(list[position].opening_hours.open_now)"Opened" else "Closed"
        }else{
            holder.itemView.openclose.text="Open-Close No Info "
        }
        holder.itemView.rating.text="Rating ${list[position].rating}"
    }
}
