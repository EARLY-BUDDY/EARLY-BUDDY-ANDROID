package com.devaon.early_buddy_android.feature.route

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devaon.early_buddy_android.R

class RouteDetailAdapter(var routedetail:ArrayList<String>):RecyclerView.Adapter<RouteDetailViewHolder>(){
    override fun getItemCount(): Int {
        return routedetail.size
    }

    override fun onBindViewHolder(holder: RouteDetailViewHolder, position: Int) {
        holder.stopStation.text=routedetail[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteDetailViewHolder {
        val view:View=LayoutInflater.from(parent.context).inflate(R.layout.item_pass_thorugh_route_detail,parent,false)
        return RouteDetailViewHolder(view)
    }
}


class RouteDetailViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    var stopStation = itemView.findViewById<TextView>(R.id.item_tv_stop_station_name)

}