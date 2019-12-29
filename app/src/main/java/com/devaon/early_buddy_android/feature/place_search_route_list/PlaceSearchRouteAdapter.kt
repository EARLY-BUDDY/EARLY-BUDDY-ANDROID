package com.devaon.early_buddy_android.feature.place_search_route_list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devaon.early_buddy_android.R
import com.devaon.early_buddy_android.data.place.GetPlaceData
import com.devaon.early_buddy_android.data.route.Path
import com.devaon.early_buddy_android.data.route.SubPath
import com.devaon.early_buddy_android.feature.place_search_list.PlaceSearchAdapter

class PlaceSearchRouteAdapter(
    private val context: Context
) : RecyclerView.Adapter<PlaceSearchRouteAdapter.PlaceSearchRouteViewHolder>() {

    var routeList: ArrayList<Path> = ArrayList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlaceSearchRouteAdapter.PlaceSearchRouteViewHolder {
        val view: View = LayoutInflater
            .from(context)
            .inflate(R.layout.list_item_place_search, parent, false)
        return PlaceSearchRouteViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaceSearchRouteAdapter.PlaceSearchRouteViewHolder, position: Int) {
        holder.bind(routeList[position])
    }

    override fun getItemCount(): Int {
        return routeList.size
    }

    inner class PlaceSearchRouteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val time: TextView = view.findViewById(R.id.li_place_search_route_tv_time)
        val method : TextView = view.findViewById(R.id.li_place_search_route_tv_method)
        val transfer : TextView = view.findViewById(R.id.li_place_search_route_tv_transfer)
        val walk : TextView = view.findViewById(R.id.li_place_search_route_tv_walk)
        val money : TextView = view.findViewById(R.id.li_place_search_route_tv_money)

        fun bind(data: Path) {
            time.text = String.format("%d", data.totalTime)
            method.text = String.format("환승 %d회", data.transitCount)
            walk.text = String.format("도보 %d분", data.totalWalkTime)
            money.text = String.format("%d원", data.totalPay)
        }
    }

}