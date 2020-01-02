package com.devaon.early_buddy_android.feature.place.search.route

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devaon.early_buddy_android.R
import com.devaon.early_buddy_android.data.route.Path


class PlaceSearchRouteAdapter(
    private val context: Context
) : RecyclerView.Adapter<PlaceSearchRouteAdapter.PlaceSearchRouteViewHolder>() {

    private var routeList: ArrayList<Path> = ArrayList()

    fun replaceAll(list : ArrayList<Path>){
        routeList.clear()
        routeList.addAll(list)
    }

    override fun getItemCount(): Int {
        return routeList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceSearchRouteViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_list_place_search_route, parent, false)
        return PlaceSearchRouteViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaceSearchRouteViewHolder, position: Int) {
        holder.bind(routeList[position])

        when(routeList[position].pathType){
            1 -> { holder.method.text = "지하철" }
            2 -> { holder.method.text = "버스" }
            3 -> { holder.method.text = "지하철 + 버스"}
        }

        var transText = arrayListOf<String>()

        for(i in 1.. routeList[position].subPath.size-1) {
            if(i % 2 != 0) { // 홀수만 처리
                when (routeList[position].subPath[i].trafficType) {
                    1 -> { // 지하철
                        when(routeList[position].subPath[i].lane.subwayCode){
                            1, 2, 3, 4, 5, 6, 7, 8, 9 -> transText.add(routeList[position].subPath[i].lane.subwayCode.toString() + "호선")
                            100 -> transText.add("분당선")
                            101 -> transText.add("공항철도")
                            104 -> transText.add("경의중앙선")
                            107 -> transText.add("에버라인")
                            108 -> transText.add("경춘선")
                            102 -> transText.add("자기부상철도")
                            109 -> transText.add("신분당선")
                            110 -> transText.add("의정부경전철")
                            111 -> transText.add("수인선")
                            112 -> transText.add("경강선")
                            113 -> transText.add("우이신설선")
                            114 -> transText.add("서해선")
                        }
                    }
                    2 -> { // 버스
                        transText.add(routeList[position].subPath[i].lane.busNo)
                    }
                }
            }

        }

        val walkParam1 = holder.walk1.getLayoutParams() as LinearLayout.LayoutParams
        walkParam1.weight = routeList[position].subPath[0].sectionTime.toFloat()
        holder.walk1.setLayoutParams(walkParam1)

        val methodParam1 = holder.method1.getLayoutParams() as LinearLayout.LayoutParams
        methodParam1.weight = routeList[position].subPath[1].sectionTime.toFloat()
        holder.method1.setLayoutParams(methodParam1)
        holder.method1Tx.text = transText[0]

        val walkParam2 = holder.walk2.getLayoutParams() as LinearLayout.LayoutParams
        walkParam2.weight =  routeList[position].subPath[2].sectionTime.toFloat()
        holder.walk1.setLayoutParams(walkParam2)

        if(routeList[position].subPath.size > 3) {
            val methodParam2 = holder.method2.getLayoutParams() as LinearLayout.LayoutParams
            methodParam2.weight = routeList[position].subPath[3].sectionTime.toFloat()
            holder.method2.setLayoutParams(methodParam2)
            holder.method2Tx.text = transText[1]
        }else{
            holder.method2.visibility = GONE
            holder.walk3.visibility = GONE
            holder.method3.visibility = GONE
            holder.walk4.visibility = GONE
        }

        if(routeList[position].subPath.size > 4){
            val walkParam3 = holder.walk3.getLayoutParams() as LinearLayout.LayoutParams
            walkParam3.weight = routeList[position].subPath[4].sectionTime.toFloat()
            holder.walk3.setLayoutParams(walkParam3)
        }else{
            holder.walk3.visibility = GONE
            holder.method3.visibility = GONE
            holder.walk4.visibility = GONE
        }

        if(routeList[position].subPath.size > 5) {
            val methodParam3 = holder.method3.getLayoutParams() as LinearLayout.LayoutParams
            methodParam3.weight = routeList[position].subPath[5].sectionTime.toFloat()
            holder.method3.setLayoutParams(methodParam3)
            holder.method3Tx.text = transText[2]
        }else{
            holder.method3.visibility = GONE
            holder.walk4.visibility = GONE
        }

        if(routeList[position].subPath.size > 6) {
            val walkParam4 = holder.walk4.getLayoutParams() as LinearLayout.LayoutParams
            walkParam4.weight = routeList[position].subPath[6].sectionTime.toFloat()
            holder.walk4.setLayoutParams(walkParam4)
        }else{
            holder.walk4.visibility = GONE
        }
    }

    inner class PlaceSearchRouteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val time: TextView = view.findViewById(R.id.li_place_search_route_tv_time)
        val method: TextView = view.findViewById(R.id.li_place_search_route_tv_method)
        val transfer: TextView = view.findViewById(R.id.li_place_search_route_tv_transfer)
        val walk: TextView = view.findViewById(R.id.li_place_search_route_tv_walk)
        val money: TextView = view.findViewById(R.id.li_place_search_route_tv_money)
        val best : TextView = view.findViewById(R.id.li_place_search_route_tv_best)

        val walk1 : RelativeLayout = view.findViewById(R.id.act_schedule_route_rl_walk_1)
        val walk2 : RelativeLayout = view.findViewById(R.id.act_schedule_route_rl_walk_2)
        val walk3 : RelativeLayout = view.findViewById(R.id.act_schedule_route_rl_walk_3)
        val walk4 : RelativeLayout = view.findViewById(R.id.act_schedule_route_rl_walk_4)

        val method1: RelativeLayout = view.findViewById(R.id.act_schedule__route_rl_method_1)
        val method2:RelativeLayout = view.findViewById(R.id.act_schedule_route_rl_method_2)
        val method3: RelativeLayout = view.findViewById(R.id.act_schedule_route_rl_method_3)

        var method1Img: ImageView = view.findViewById(R.id.act_schedule_route_iv_method_1)
        var method2Img: ImageView = view.findViewById(R.id.act_schedule_route_iv_method_2)
        var method3Img: ImageView = view.findViewById(R.id.act_schedule_route_iv_method_3)

        var method1Tx: TextView = view.findViewById(R.id.act_schedule_route_tv_method_1)
        var method2Tx: TextView = view.findViewById(R.id.act_schedule_route_tv_method_2)
        var method3Tx: TextView = view.findViewById(R.id.act_schedule_route_tv_method_3)

        fun bind(data: Path) {
            val hour = data.totalTime / 60
            val min = data.totalTime % 60

            val method1Color = "#35b645" //첫번째 경로 색깔
            val method2Color = "#e8146d" //두번째 경로 색깔
            val method3Color = "#219de2" //세번째 경로 색깔

            if(min != 0){
                time.text = String.format("%d시간 %d분", hour, min)
            }else{
                time.text = String.format("%d시간", hour)
            }
            transfer.text = String.format("환승 %d회", data.transitCount)
            walk.text = String.format("도보 %d분", data.totalWalkTime)
            money.text = String.format("%d원", data.totalPay)

            method1Img.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(method1Color)))
            method1Tx.setTextColor(Color.parseColor(method1Color))
            method2Img.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(method2Color)))
            method2Tx.setTextColor(Color.parseColor(method2Color))
            method3Img.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(method3Color)))
            method3Tx.setTextColor(Color.parseColor(method3Color))


        }
    }

}