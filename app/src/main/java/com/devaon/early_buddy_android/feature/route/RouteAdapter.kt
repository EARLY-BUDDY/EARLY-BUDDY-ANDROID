package com.devaon.early_buddy_android.feature.route

import android.content.Context
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
import com.devaon.early_buddy_android.data.route.SubPath

class RouteAdapter(
    var context: Context,
    private val clickListener: RouteViewHolder.ItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var routeDetailAdapter: RouteDetailAdapter
    var routeList: ArrayList<SubPath> = ArrayList()
    private var passthroughList: ArrayList<PassThroughStation> = ArrayList()

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
        when (holder) {
            is RouteViewHolder -> {
                when (holder.itemViewType) {
                    //지하철
                    1 -> {
                        holder.ridingImg.setImageResource(R.drawable.img_subway)
                        holder.ridingNumber.text =
                            String.format("%d호선", routeList[position].lane[0].subwayCode)
                    }
                    //버스
                    2 -> {
                        holder.ridingImg.setImageResource(R.drawable.img_bus)
                        holder.ridingNumber.text =
                            String.format("%s", routeList[position].lane[0].busNo)
                    }

                }

                holder.bind(routeList[position])
                holder.dropDown.setOnClickListener {
                    if (routeList[position].clicked) {
                        holder.detailList.visibility = View.GONE
                        holder.dropDownUp.setImageResource(R.drawable.ic_dropbox_down)
                        routeList[position].clicked = false
                    } else {
                        routeDetailAdapter =
                            RouteDetailAdapter(routeList[position].passStopList.stations)
                        holder.detailList.visibility = View.VISIBLE
                        holder.detailList.adapter = routeDetailAdapter
                        holder.detailList.layoutManager = LinearLayoutManager(context)
                        holder.dropDownUp.setImageResource(R.drawable.ic_dropbox_up)
                        routeList[position].clicked = true
                    }
                }
            }
            is WalkViewHolder -> {
                holder.bindWalk(routeList[position])
                when(position){
                    //첫번째
                    0 -> {
                        holder.walkStartPoint.text = String.format("%s",routeList[position].startName)
                        holder.walkEndPoint.text = String.format("%s 까지 걷기",routeList[position+1].startName)
                    }
                    //마지막
                    routeList.size-1 -> {
                        holder.walkStartPoint.visibility=View.GONE

                    }
                    //중간
                    else -> {
                        holder.walkStartPoint.text = String.format("%d번 출구로 나오기",routeList[position+-1].endExitNo)
                        holder.walkEndPoint.text = String.format("%s 까지 걷기",routeList[position+1].startName)
                    }
                }
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return routeList[position].trafficType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            3 -> {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_pass_through_route_walk, parent, false)
                WalkViewHolder(view)
            }
            else -> {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_pass_through_route_riding, parent, false)
                RouteViewHolder(view, clickListener)
            }
        }
    }
}


class RouteViewHolder(itemView: View, private val clickListener: ItemClickListener) :
    RecyclerView.ViewHolder(itemView) {
    val dropDown: ConstraintLayout = itemView.findViewById(R.id.item_pass_riding_cl_drop_down_up)
    val detailList: RecyclerView =
        itemView.findViewById(R.id.item_pass_riding_rv_riding_info_detail)
    val dropDownUp: ImageView = itemView.findViewById(R.id.item_pass_riding_iv_drop_down_up_icon)
    val ridingNumber: TextView = itemView.findViewById(R.id.item_pass_riding_tv_riding_number)
    private val startingText: TextView =
        itemView.findViewById(R.id.item_pass_riding_tv_starting_point)
    val viewMap: TextView = itemView.findViewById(R.id.item_pass_riding_tv_view_map)
    val ridingImg: ImageView = itemView.findViewById(R.id.item_pass_riding_iv_riding_img)
    private val travelTime: TextView = itemView.findViewById(R.id.item_pass_riding_tv_travel_time)
    val ridingLine: ImageView = itemView.findViewById(R.id.item_pass_riding_iv_riding_line)
    private val stationCount: TextView =
        itemView.findViewById(R.id.item_pass_riding_tv_stop_station_count)
    val quitImg: TextView = itemView.findViewById(R.id.item_pass_riding_tv_quit)
    private val endText: TextView = itemView.findViewById(R.id.item_pass_riding_tv_end_point)

    init {
        dropDown.setOnClickListener {
            clickListener.dropDownClick(adapterPosition)
        }
    }

    interface ItemClickListener {
        fun dropDownClick(position: Int)
    }

    fun bind(data: SubPath) {
        stationCount.text = String.format("%d개 정류장", data.stationCount)
        travelTime.text = String.format("약 %d분", data.sectionTime)
        startingText.text = String.format("%s", data.startName)
        endText.text = String.format("%s", data.endName)
    }
}

class WalkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var walkDistance: TextView = itemView.findViewById(R.id.act_route_tv_walk_distance)
    var walkTime: TextView = itemView.findViewById(R.id.act_route_tv_walk_time)
    var walkStartPoint: TextView = itemView.findViewById(R.id.act_route_tv_walk_start_point)
    var walkEndPoint: TextView = itemView.findViewById(R.id.act_route_tv_walk_end_point)

    fun bindWalk(data: SubPath) {
        walkTime.text = String.format("약 %d분", data.sectionTime)
        walkDistance.text = String.format("도보 %dm", data.distance)
    }
}