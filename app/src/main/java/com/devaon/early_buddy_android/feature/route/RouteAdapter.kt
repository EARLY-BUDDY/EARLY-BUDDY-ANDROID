package com.devaon.early_buddy_android.feature.route

import android.content.Context
import android.text.method.TextKeyListener.clear
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devaon.early_buddy_android.R
import com.devaon.early_buddy_android.data.route.PassThroughStation
import com.devaon.early_buddy_android.data.route.Route
import com.devaon.early_buddy_android.data.route.SubPath
import java.util.Collections.addAll

class RouteAdapter(
    var context: Context,
    private val clickListener: RouteViewHolder.ItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var routeDetailAdapter: RouteDetailAdapter
    private var routeList: ArrayList<SubPath> =ArrayList()
    private var passthroughList: ArrayList<PassThroughStation> =ArrayList()

    //경로데이터 넣기
    fun setRouteItem(newRouteList: ArrayList<SubPath>) {
        with(routeList) {
            clear()
            addAll(newRouteList)
        }
        notifyDataSetChanged()
    }

    //클릭 여부 확인
    fun getClicked(position: Int): Boolean {
        return routeList[position].clicked
    }

    override fun getItemCount(): Int {
        return routeList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RouteViewHolder) {
            holder.bind(routeList[position])
            holder.dropDown.setOnClickListener {
                if (routeList[position].clicked) {
                    holder.detailList.visibility = View.GONE
                    holder.dropDownUp.setImageResource(R.drawable.ic_dropbox_down)
                    routeList[position].clicked = false
                } else {
                    routeDetailAdapter = RouteDetailAdapter(routeList[position].passStopList.stations)
                    holder.detailList.visibility = View.VISIBLE
                    holder.detailList.adapter = routeDetailAdapter
                    holder.detailList.layoutManager = LinearLayoutManager(context)
                    holder.dropDownUp.setImageResource(R.drawable.ic_dropbox_up)
                    routeList[position].clicked = true
                }
            }
        } else {

        }

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_pass_through_route_first, parent, false)
                FirstViewHolder(view)
            }
            else -> {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_pass_through_route, parent, false)
                RouteViewHolder(view, clickListener)
            }
        }
    }
}


class RouteViewHolder(itemView: View, private val clickListener: ItemClickListener) :
    RecyclerView.ViewHolder(itemView) {
    var dropDown: ConstraintLayout = itemView.findViewById(R.id.item_pass_cl_drop_down_up)
    var detailList: RecyclerView = itemView.findViewById(R.id.item_pass_rv_riding_info_detail)
    var dropDownUp: ImageView = itemView.findViewById(R.id.item_pass_iv_drop_down_up_icon)
    var ridingNumber: TextView = itemView.findViewById(R.id.item_pass_tv_riding_number)
    var startingText: TextView = itemView.findViewById(R.id.item_pass_tv_starting_point)
    var viewMap: TextView = itemView.findViewById(R.id.item_pass_tv_view_map)
    var ridingImg: ImageView = itemView.findViewById(R.id.item_pass_iv_riding_img)
    var travelTime: TextView = itemView.findViewById(R.id.itemm_pass_tv_travel_time)
    var ridingLine: ImageView = itemView.findViewById(R.id.item_pass_iv_riding_line)
    var stationCount: TextView = itemView.findViewById(R.id.item_pass_tv_stop_station_count)
    var quitImg: TextView = itemView.findViewById(R.id.item_pass_tv_quit)
    var endText: TextView = itemView.findViewById(R.id.item_pass_tv_end_point)

    init {
        dropDown.setOnClickListener {
            clickListener.dropDownClick(adapterPosition)
        }
    }

    interface ItemClickListener {
        fun dropDownClick(position: Int)
    }

    fun bind(data:SubPath){
        stationCount.text= String.format("%d개 정류장",data.stationCount)
        travelTime.text= String.format("약 %d분",data.sectionTime)
        startingText.text=String.format("%s",data.startName)
        endText.text=String.format("%s",data.endName)
//        ridingNumber.text=String.format("%s",data.lane[0].laneName)
    }
}

class FirstViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

}