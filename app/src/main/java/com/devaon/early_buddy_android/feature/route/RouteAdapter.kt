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
                when (holder.itemViewType) {
                    //지하철
                    1 -> {
                        holder.ridingImg.setImageResource(R.drawable.img_subway)
                        when (routeList[position].lane[0].subwayCode) {
                            1 -> {
                                with(holder) {
                                    ridingNumber.text = "1호선"
                                    ridingLine.setImageResource(R.color.seoul_line_one)
                                    ridingImg.setImageResource(R.drawable.img_subway_one)
                                    topPoint.setImageResource(R.drawable.img_path_point_one)
                                    bottomPoint.setImageResource(R.drawable.img_path_point_one)
                                    quitImg.setImageResource(R.drawable.img_stop_one)
                                }
                                when (val background = holder.ridingNumber.getBackground()) {
                                    is GradientDrawable -> background.setColor(
                                        ContextCompat.getColor(
                                            context,
                                            R.color.seoul_line_one
                                        )
                                    )
                                }
                            }
                            2 -> {
                                with(holder) {
                                    ridingNumber.text = "2호선"
                                    ridingLine.setImageResource(R.color.seoul_line_two)
                                    ridingImg.setImageResource(R.drawable.img_subway_two)
                                    topPoint.setImageResource(R.drawable.img_path_point_two)
                                    bottomPoint.setImageResource(R.drawable.img_path_point_two)
                                    quitImg.setImageResource(R.drawable.img_stop_two)
                                }
                                when (val background = holder.ridingNumber.getBackground()) {
                                    is GradientDrawable -> background.setColor(
                                        ContextCompat.getColor(
                                            context,
                                            R.color.seoul_line_two
                                        )
                                    )
                                }
                            }
                            3 -> {
                                with(holder) {
                                    ridingNumber.text = "3호선"
                                    ridingLine.setImageResource(R.color.seoul_line_three)
                                    ridingImg.setImageResource(R.drawable.img_subway_three)
                                    topPoint.setImageResource(R.drawable.img_path_point_three)
                                    bottomPoint.setImageResource(R.drawable.img_path_point_three)
                                    quitImg.setImageResource(R.drawable.img_stop_three)
                                }
                                when (val background = holder.ridingNumber.getBackground()) {
                                    is GradientDrawable -> background.setColor(
                                        ContextCompat.getColor(
                                            context,
                                            R.color.seoul_line_three
                                        )
                                    )
                                }
                            }
                            4 -> {
                                with(holder) {
                                    ridingNumber.text = "4호선"
                                    ridingLine.setImageResource(R.color.seoul_line_four)
                                    ridingImg.setImageResource(R.drawable.img_subway_four)
                                    topPoint.setImageResource(R.drawable.img_path_point_four)
                                    bottomPoint.setImageResource(R.drawable.img_path_point_four)
                                    quitImg.setImageResource(R.drawable.img_stop_four)
                                }
                                when (val background = holder.ridingNumber.getBackground()) {
                                    is GradientDrawable -> background.setColor(
                                        ContextCompat.getColor(
                                            context,
                                            R.color.seoul_line_four
                                        )
                                    )
                                }
                            }
                            5 -> {
                                with(holder) {
                                    ridingNumber.text = "5호선"
                                    ridingLine.setImageResource(R.color.seoul_line_five)
                                    ridingImg.setImageResource(R.drawable.img_subway_five)
                                    topPoint.setImageResource(R.drawable.img_path_point_five)
                                    bottomPoint.setImageResource(R.drawable.img_path_point_five)
                                    quitImg.setImageResource(R.drawable.img_stop_five)
                                }
                                when (val background = holder.ridingNumber.getBackground()) {
                                    is GradientDrawable -> background.setColor(
                                        ContextCompat.getColor(
                                            context,
                                            R.color.seoul_line_five
                                        )
                                    )
                                }
                            }
                            6 -> {
                                with(holder) {
                                    ridingNumber.text = "6호선"
                                    ridingLine.setImageResource(R.color.seoul_line_six)
                                    ridingImg.setImageResource(R.drawable.img_subway_six)
                                    topPoint.setImageResource(R.drawable.img_path_point_six)
                                    bottomPoint.setImageResource(R.drawable.img_path_point_six)
                                    quitImg.setImageResource(R.drawable.img_stop_six)
                                }
                                when (val background = holder.ridingNumber.getBackground()) {
                                    is GradientDrawable -> background.setColor(
                                        ContextCompat.getColor(
                                            context,
                                            R.color.seoul_line_six
                                        )
                                    )
                                }
                            }
                            7 -> {
                                with(holder) {
                                    ridingNumber.text = "7호선"
                                    ridingLine.setImageResource(R.color.seoul_line_seven)
                                    ridingImg.setImageResource(R.drawable.img_subway_seven)
                                    topPoint.setImageResource(R.drawable.img_path_point_seven)
                                    bottomPoint.setImageResource(R.drawable.img_path_point_seven)
                                    quitImg.setImageResource(R.drawable.img_stop_seven)
                                }
                                when (val background = holder.ridingNumber.getBackground()) {
                                    is GradientDrawable -> background.setColor(
                                        ContextCompat.getColor(
                                            context,
                                            R.color.seoul_line_seven
                                        )
                                    )
                                }
                            }
                            8 -> {
                                with(holder) {
                                    ridingNumber.text = "8호선"
                                    ridingLine.setImageResource(R.color.seoul_line_eight)
                                    ridingImg.setImageResource(R.drawable.img_subway_eight)
                                    topPoint.setImageResource(R.drawable.img_path_point_eight)
                                    bottomPoint.setImageResource(R.drawable.img_path_point_eight)
                                    quitImg.setImageResource(R.drawable.img_stop_eight)
                                }
                                when (val background = holder.ridingNumber.getBackground()) {
                                    is GradientDrawable -> background.setColor(
                                        ContextCompat.getColor(
                                            context,
                                            R.color.seoul_line_eight
                                        )
                                    )
                                }
                            }
                            9 -> {
                                with(holder) {
                                    ridingNumber.text = "9호선"
                                    ridingLine.setImageResource(R.color.seoul_line_nine)
                                    ridingImg.setImageResource(R.drawable.img_subway_nine)
                                    topPoint.setImageResource(R.drawable.img_path_point_nine)
                                    bottomPoint.setImageResource(R.drawable.img_path_point_nine)
                                    quitImg.setImageResource(R.drawable.img_stop_nine)
                                }
                                when (val background = holder.ridingNumber.getBackground()) {
                                    is GradientDrawable -> background.setColor(
                                        ContextCompat.getColor(
                                            context,
                                            R.color.seoul_line_nine
                                        )
                                    )
                                }
                            }
                            100 -> {
                                with(holder) {
                                    ridingNumber.text = "분당선"
                                    ridingLine.setImageResource(R.color.seoul_line_bunDang)
                                    ridingImg.setImageResource(R.drawable.img_subway_bundang)
                                    topPoint.setImageResource(R.drawable.img_path_point_bundang)
                                    bottomPoint.setImageResource(R.drawable.img_path_point_bundang)
                                    quitImg.setImageResource(R.drawable.img_stop_bundang)
                                }
                                when (val background = holder.ridingNumber.getBackground()) {
                                    is GradientDrawable -> background.setColor(
                                        ContextCompat.getColor(
                                            context,
                                            R.color.seoul_line_bunDang
                                        )
                                    )
                                }
                            }
                            101 -> {
                                with(holder) {
                                    ridingNumber.text = "공항철도"
                                    ridingLine.setImageResource(R.color.seoul_line_gongHang)
                                    ridingImg.setImageResource(R.drawable.img_subway_airport)
                                    topPoint.setImageResource(R.drawable.img_path_point_airport)
                                    bottomPoint.setImageResource(R.drawable.img_path_point_airport)
                                    quitImg.setImageResource(R.drawable.img_stop_airport)
                                }
                                holder.ridingNumber.text = "공항철도"
                                when (val background = holder.ridingNumber.getBackground()) {
                                    is GradientDrawable -> background.setColor(
                                        ContextCompat.getColor(
                                            context,
                                            R.color.seoul_line_gongHang
                                        )
                                    )
                                }
                            }
                            104 -> {
                                with(holder) {
                                    ridingNumber.text = "경의중앙선"
                                    ridingLine.setImageResource(R.color.seoul_line_gyungJung)
                                    ridingImg.setImageResource(R.drawable.img_subway_kyunguijungang)
                                    topPoint.setImageResource(R.drawable.img_path_point_kyunguijungang)
                                    bottomPoint.setImageResource(R.drawable.img_path_point_kyunguijungang)
                                    quitImg.setImageResource(R.drawable.img_stop_kyunguijungang)
                                }
                                when (val background = holder.ridingNumber.getBackground()) {
                                    is GradientDrawable -> background.setColor(
                                        ContextCompat.getColor(
                                            context,
                                            R.color.seoul_line_gyungJung
                                        )
                                    )
                                }
                            }
                            107 -> {
                                with(holder) {
                                    ridingNumber.text = "에버라인"
                                    ridingLine.setImageResource(R.color.seoul_line_ever)
                                    ridingImg.setImageResource(R.drawable.img_subway_everline)
                                    topPoint.setImageResource(R.drawable.img_path_point_everline)
                                    bottomPoint.setImageResource(R.drawable.img_path_point_everline)
                                    quitImg.setImageResource(R.drawable.img_stop_everline)
                                }
                                when (val background = holder.ridingNumber.getBackground()) {
                                    is GradientDrawable -> background.setColor(
                                        ContextCompat.getColor(
                                            context,
                                            R.color.seoul_line_ever
                                        )
                                    )
                                }
                            }
                            108 -> {
                                with(holder) {
                                    ridingNumber.text = "경춘선"
                                    ridingLine.setImageResource(R.color.seoul_line_gyungChun)
                                    ridingImg.setImageResource(R.drawable.img_subway_kyungchun)
                                    topPoint.setImageResource(R.drawable.img_path_point_kyungchun)
                                    bottomPoint.setImageResource(R.drawable.img_path_point_kyungchun)
                                    quitImg.setImageResource(R.drawable.img_stop_kyungchun)
                                }
                                when (val background = holder.ridingNumber.getBackground()) {
                                    is GradientDrawable -> background.setColor(
                                        ContextCompat.getColor(
                                            context,
                                            R.color.seoul_line_gyungChun
                                        )
                                    )
                                }
                            }
                            102 -> {
                                with(holder) {
                                    ridingNumber.text = "자기부상철도"
                                    ridingLine.setImageResource(R.color.seoul_line_jaGiBuSang)
                                    ridingImg.setImageResource(R.drawable.img_subway_jaki)
                                    topPoint.setImageResource(R.drawable.img_path_point_jaki)
                                    bottomPoint.setImageResource(R.drawable.img_path_point_jaki)
                                    quitImg.setImageResource(R.drawable.img_stop_jaki)
                                }
                                when (val background = holder.ridingNumber.getBackground()) {
                                    is GradientDrawable -> background.setColor(
                                        ContextCompat.getColor(
                                            context,
                                            R.color.seoul_line_jaGiBuSang
                                        )
                                    )
                                }
                            }
                            109 -> {
                                with(holder) {
                                    ridingNumber.text = "신분당선"
                                    ridingLine.setImageResource(R.color.seoul_line_sinBunDang)
                                    ridingImg.setImageResource(R.drawable.img_subway_shinbundang)
                                    topPoint.setImageResource(R.drawable.img_path_point_shinbundang)
                                    bottomPoint.setImageResource(R.drawable.img_path_point_shinbundang)
                                    quitImg.setImageResource(R.drawable.img_stop_shinbundang)
                                }
                                when (val background = holder.ridingNumber.getBackground()) {
                                    is GradientDrawable -> background.setColor(
                                        ContextCompat.getColor(
                                            context,
                                            R.color.seoul_line_sinBunDang
                                        )
                                    )
                                }
                            }
                            110 -> {
                                with(holder) {
                                    ridingNumber.text = "의정부경전철"
                                    ridingLine.setImageResource(R.color.seoul_line_uiJeongBu)
                                    ridingImg.setImageResource(R.drawable.img_subway_uijeongbu)
                                    topPoint.setImageResource(R.drawable.img_path_point_uijeongbu)
                                    bottomPoint.setImageResource(R.drawable.img_path_point_uijeongbu)
                                    quitImg.setImageResource(R.drawable.img_stop_uijeongbu)
                                }
                                when (val background = holder.ridingNumber.getBackground()) {
                                    is GradientDrawable -> background.setColor(
                                        ContextCompat.getColor(
                                            context,
                                            R.color.seoul_line_uiJeongBu
                                        )
                                    )
                                }
                            }
                            113 -> {
                                with(holder) {
                                    ridingNumber.text = "우이신설선"
                                    ridingLine.setImageResource(R.color.seoul_line_ueeSinSeol)
                                    ridingImg.setImageResource(R.drawable.img_subway_ui)
                                    topPoint.setImageResource(R.drawable.img_path_point_ui)
                                    bottomPoint.setImageResource(R.drawable.img_path_point_ui)
                                    quitImg.setImageResource(R.drawable.img_stop_ui)
                                }
                                when (val background = holder.ridingNumber.getBackground()) {
                                    is GradientDrawable -> background.setColor(
                                        ContextCompat.getColor(
                                            context,
                                            R.color.seoul_line_ueeSinSeol
                                        )
                                    )
                                }
                            }
                        }
                        holder.direction.text = String.format("%s 방면", routeList[position].way)
                        holder.startingText.text =
                            String.format("%s역", routeList[position].startName)
                        holder.endText.text = String.format("%s역", routeList[position].endName)

                    }
                    //버스
                    2 -> {
                        when (routeList[position].lane[0].type) {
                            1,2,11 -> {     //간선,좌석,일반
                                with(holder) {
                                    ridingNumber.text =
                                        String.format("%s", routeList[position].lane[0].busNo)
                                    startingText.text =
                                        String.format("%s", routeList[position].startName)
                                    endText.text = String.format("%s", routeList[position].endName)
                                    ridingLine.setImageResource(R.color.seoul_bus_gan_line)
                                    ridingImg.setImageResource(R.drawable.img_bus_ganline)
                                    topPoint.setImageResource(R.drawable.img_path_point_ganline)
                                    bottomPoint.setImageResource(R.drawable.img_path_point_ganline)
                                    quitImg.setImageResource(R.drawable.img_stop_ganline)
                                }
                                when (val background = holder.ridingNumber.getBackground()) {
                                    is GradientDrawable -> background.setColor(
                                        ContextCompat.getColor(
                                            context,
                                            R.color.seoul_bus_gan_line
                                        )
                                    )
                                }
                            }
                            10,12 -> {     //외곽,지선
                                with(holder) {
                                    ridingNumber.text =
                                        String.format("%s", routeList[position].lane[0].busNo)
                                    startingText.text =
                                        String.format("%s", routeList[position].startName)
                                    endText.text = String.format("%s", routeList[position].endName)
                                    ridingLine.setImageResource(R.color.seoul_bus_ji_line)
                                    ridingImg.setImageResource(R.drawable.img_bus_jiline)
                                    topPoint.setImageResource(R.drawable.img_path_point_jiline)
                                    bottomPoint.setImageResource(R.drawable.img_path_point_jiline)
                                    quitImg.setImageResource(R.drawable.img_stop_jiline)
                                }
                                when (val background = holder.ridingNumber.getBackground()) {
                                    is GradientDrawable -> background.setColor(
                                        ContextCompat.getColor(
                                            context,
                                            R.color.seoul_bus_ji_line
                                        )
                                    )
                                }
                            }
                            4,14,15 -> {     //직행,광역,급행
                                with(holder) {
                                    ridingNumber.text =
                                        String.format("%s", routeList[position].lane[0].busNo)
                                    startingText.text =
                                        String.format("%s", routeList[position].startName)
                                    endText.text = String.format("%s", routeList[position].endName)
                                    ridingLine.setImageResource(R.color.seoul_bus_gwangyuk)
                                    ridingImg.setImageResource(R.drawable.img_bus_gwangyuk)
                                    topPoint.setImageResource(R.drawable.img_path_point_gwangyuk)
                                    bottomPoint.setImageResource(R.drawable.img_path_point_gwangyuk)
                                    quitImg.setImageResource(R.drawable.img_stop_gwangyuk)
                                }
                                when (val background = holder.ridingNumber.getBackground()) {
                                    is GradientDrawable -> background.setColor(
                                        ContextCompat.getColor(
                                            context,
                                            R.color.seoul_bus_gwangyuk
                                        )
                                    )
                                }
                            }
                            5 -> {     //공항
                                with(holder) {
                                    ridingNumber.text =
                                        String.format("%s", routeList[position].lane[0].busNo)
                                    startingText.text =
                                        String.format("%s", routeList[position].startName)
                                    endText.text = String.format("%s", routeList[position].endName)
                                    ridingLine.setImageResource(R.color.seoul_line_gongHang)
                                    ridingImg.setImageResource(R.drawable.img_bus_ap)
                                    topPoint.setImageResource(R.drawable.img_path_point_airport)
                                    bottomPoint.setImageResource(R.drawable.img_path_point_airport)
                                    quitImg.setImageResource(R.drawable.img_stop_airport)
                                }
                                when (val background = holder.ridingNumber.getBackground()) {
                                    is GradientDrawable -> background.setColor(
                                        ContextCompat.getColor(
                                            context,
                                            R.color.seoul_line_gongHang
                                        )
                                    )
                                }
                            }
                            13 -> {     //순환
                                with(holder) {
                                    ridingNumber.text =
                                        String.format("%s", routeList[position].lane[0].busNo)
                                    startingText.text =
                                        String.format("%s", routeList[position].startName)
                                    endText.text = String.format("%s", routeList[position].endName)
                                    ridingLine.setImageResource(R.color.inCheon_line_two)
                                    ridingImg.setImageResource(R.drawable.img_bus_circula)
                                    topPoint.setImageResource(R.drawable.img_path_point_incheontwo)
                                    bottomPoint.setImageResource(R.drawable.img_path_point_incheontwo)
                                    quitImg.setImageResource(R.drawable.img_stop_incheontwo)
                                }
                                when (val background = holder.ridingNumber.getBackground()) {
                                    is GradientDrawable -> background.setColor(
                                        ContextCompat.getColor(
                                            context,
                                            R.color.inCheon_line_two
                                        )
                                    )
                                }
                            }
                            else -> {       //나머지
                                with(holder) {
                                    ridingNumber.text =
                                        String.format("%s", routeList[position].lane[0].busNo)
                                    startingText.text =
                                        String.format("%s", routeList[position].startName)
                                    endText.text = String.format("%s", routeList[position].endName)
                                    ridingLine.setImageResource(R.color.seoul_bus_remainder)
                                    ridingImg.setImageResource(R.drawable.img_bus_others)
                                    topPoint.setImageResource(R.drawable.img_path_point_others)
                                    bottomPoint.setImageResource(R.drawable.img_path_point_others)
                                    quitImg.setImageResource(R.drawable.img_stop_ohters)
                                }
                                when (val background = holder.ridingNumber.getBackground()) {
                                    is GradientDrawable -> background.setColor(
                                        ContextCompat.getColor(
                                            context,
                                            R.color.seoul_bus_remainder
                                        )
                                    )
                                }
                            }
                        }
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
                                        routeList[position].lane[0].subwayCode
                                    )
                            }
                            2 -> {
                                routeDetailAdapter =
                                    RouteDetailAdapter(
                                        routeList[position].passStopList.stations,
                                        holder.itemViewType,
                                        routeList[position].lane[0].type
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