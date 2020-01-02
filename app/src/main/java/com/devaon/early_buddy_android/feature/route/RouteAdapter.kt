package com.devaon.early_buddy_android.feature.route

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
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
                GG.create(
                    holder.itemViewType, when (holder.itemViewType) {
                        1 -> routeList[position].lane.subwayCode
                        else -> routeList[position].lane.type
                    }
                )?.let {
                    with(holder) {
                        ridingNumber.text = it.ridingNumber
                        ridingLine.setImageResource(it.ridingLine)
                        ridingImg.setImageResource(it.ridingImg)
                        topPoint.setImageResource(it.point)
                        bottomPoint.setImageResource(it.point)
                        quitImg.setImageResource(it.quitImg)
                    }
                    when (val background = holder.ridingNumber.getBackground()) {
                        is GradientDrawable -> background.setColor(
                            ContextCompat.getColor(
                                context,
                                it.ridingLine
                            )
                        )
                    }
                }
                when (holder.itemViewType) {
                    //지하철
                    1 -> {
                        holder.direction.text = String.format("%s 방면", routeList[position].way)
                        holder.startingText.text =
                            String.format("%s역", routeList[position].startName)
                        holder.endText.text = String.format("%s역", routeList[position].endName)

                    }
                    //버스
                    2 -> {
                        holder.ridingNumber.text =
                            String.format("%s", routeList[position].lane.busNo)
                        holder.startingText.text =
                            String.format("%s", routeList[position].startName)
                        holder.endText.text = String.format("%s", routeList[position].endName)
                        holder.direction.text = "방향을 주의하고 탑승하세요"
                    }

                }

                holder.bind(routeList[position])
                holder.dropDown.setOnClickListener {
                    if (routeList[position].clicked) {
                        holder.detailList.visibility = View.GONE
                        holder.dropDownUp.setImageResource(R.drawable.ic_dropbox_down)
                        routeList[position].clicked = false
                    } else {
                        when (holder.itemViewType) {
                            1 -> {
                                routeDetailAdapter =
                                    RouteDetailAdapter(
                                        routeList[position].passStopList.stations,
                                        holder.itemViewType,
                                        routeList[position].lane.subwayCode
                                    )
                            }
                            2 -> {
                                routeDetailAdapter =
                                    RouteDetailAdapter(
                                        routeList[position].passStopList.stations,
                                        holder.itemViewType,
                                        routeList[position].lane.type
                                    )
                            }
                        }

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
                when (position) {
                    //첫번째
                    0 -> {
                        holder.walkStartPoint.text =
                            String.format("%s", routeList[position].startName)
                        when (routeList[position + 1].trafficType) {
                            1 -> holder.walkEndPoint.text =
                                String.format("%s역까지 걷기", routeList[position + 1].startName)
                            2 -> holder.walkEndPoint.text =
                                String.format("%s까지 걷기", routeList[position + 1].startName)
                        }
                    }
                    //마지막
                    routeList.size - 1 -> {
                        when (getPreviousTrafficType(position)) {
                            1 -> {
                                holder.walkStartPoint.text =
                                    String.format("%d번 출구로 나오기", routeList[position - 1].endExitNo)
                            }
                            2 -> {
                                holder.walkStartPoint.text =
                                    String.format("%s 하차", routeList[position - 1].endName)
                            }
                        }
                    }
                    //중간
                    else -> {
                        when (getPreviousTrafficType(position)) {
                            1 -> {
                                holder.walkStartPoint.text =
                                    String.format("%d번 출구로 나오기", routeList[position - 1].endExitNo)
                            }
                            2 -> {
                                holder.walkStartPoint.text =
                                    String.format("%s 하차", routeList[position - 1].endName)
                            }
                        }

                        holder.walkEndPoint.text =
                            String.format("%s까지 걷기", routeList[position + 1].startName)
                    }
                }
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return routeList[position].trafficType
    }

    fun getPreviousTrafficType(position: Int): Int {
        return routeList[position - 1].trafficType
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
    val startingText: TextView =
        itemView.findViewById(R.id.item_pass_riding_tv_starting_point)
    val viewMap: TextView = itemView.findViewById(R.id.item_pass_riding_tv_view_map)
    val ridingImg: ImageView = itemView.findViewById(R.id.item_pass_riding_iv_riding_img)
    private val travelTime: TextView = itemView.findViewById(R.id.item_pass_riding_tv_travel_time)
    val ridingLine: ImageView = itemView.findViewById(R.id.item_pass_riding_iv_riding_line)
    private val stationCount: TextView =
        itemView.findViewById(R.id.item_pass_riding_tv_stop_station_count)
    val quitImg: ImageView = itemView.findViewById(R.id.item_pass_riding_tv_quit)
    val endText: TextView = itemView.findViewById(R.id.item_pass_riding_tv_end_point)
    val topPoint: ImageView = itemView.findViewById(R.id.item_pass_riding_iv_top_circle)
    val bottomPoint: ImageView = itemView.findViewById(R.id.item_pass_riding_iv_bottom_circle)
    val direction: TextView = itemView.findViewById(R.id.item_pass_riding_tv_direction)

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