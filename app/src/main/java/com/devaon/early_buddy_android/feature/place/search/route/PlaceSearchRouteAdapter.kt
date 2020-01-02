package com.devaon.early_buddy_android.feature.place.search.route

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.devaon.early_buddy_android.R
import com.devaon.early_buddy_android.data.route.Path
import org.w3c.dom.Text

class PlaceSearchRouteAdapter(
    private val context: Context
) : RecyclerView.Adapter<PlaceSearchRouteAdapter.PlaceSearchRouteViewHolder>() {

    var routeList: ArrayList<Path> = ArrayList()

    override fun getItemCount(): Int {
        return routeList.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlaceSearchRouteViewHolder {
        val view: View = LayoutInflater
            .from(context)
            .inflate(R.layout.item_list_place_search_route, parent, false)
        return PlaceSearchRouteViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: PlaceSearchRouteViewHolder,
        position: Int
    ) {
        holder.bind(routeList[position])
        when(routeList[position].pathType){
            1 -> { holder.method.text = "지하철" }
            2 -> { holder.method.text = "버스" }
            3 -> { holder.method.text = "지하철 + 버스"}
        }


//        when(routeList[position].subPath[position].trafficType){
//            //지하철인경우
//            1->{
//                when(routeList[position].subPath[position].lane[0].subwayCode){
//                    1 -> {
//                        with(holder){
//
//                        }
//                    }
//                }
//            }
//            2->{
//
//            }
//        }

//        val totalTime = routeList[position].totalTime.toFloat()
//        val firstWalkTime = routeList[position].subPath[0].sectionTime.toFloat()
//        val method1Time = routeList[position].subPath[1].sectionTime.toFloat()
//        val secondWalkTime = routeList[position].subPath[2].sectionTime.toFloat()
//        var method2Time = 0f
//        var thirdWalkTime = 0f
//        var method3Time = 0f
//        if(routeList[position].subPath.size > 3) {
//            method2Time = routeList[position].subPath[3].sectionTime.toFloat()
//            thirdWalkTime = routeList[position].subPath[4].sectionTime.toFloat()
//        }
//        if(routeList[position].subPath.size > 5)
//            method3Time = routeList[position].subPath[5].sectionTime.toFloat()



//        val totalTime = routeList[0].totalTime.toFloat()
//        val firstWalkTime = routeList[0].subPath[0].sectionTime.toFloat()
//        val secondWalkTime = routeList[0].subPath[2].sectionTime.toFloat()
//        val thirdWalkTime = routeList[0].subPath[4].sectionTime.toFloat()
//        //val fourthWalkTime = routeList[0].subPath[6].sectionTime
//        val method1Time = routeList[0].subPath[1].sectionTime.toFloat()
//        val method2Time = routeList[0].subPath[3].sectionTime.toFloat()
//        val method3Time = routeList[0].subPath[5].sectionTime.toFloat()


        var methodTime = arrayListOf<Float>()
        var methodLen = arrayListOf<Float>()

        var totalPath = holder.totalPath
        var totalTime =routeList[0].totalTime.toFloat()
        var firstWalkTime = routeList[0].subPath[0].sectionTime.toFloat()
        var secondWalkTime = routeList[0].subPath[2].sectionTime.toFloat()
        var thirdWalkTime = routeList[0].subPath[4].sectionTime.toFloat()
        methodTime.add(routeList[0].subPath[1].sectionTime.toFloat())
        methodTime.add(0f)
        methodTime.add(0f)
        if(routeList[position].subPath.size > 3) {
            methodTime[1]= (routeList[0].subPath[3].sectionTime.toFloat())
            thirdWalkTime = routeList[position].subPath[4].sectionTime.toFloat()
        }
        if(routeList[position].subPath.size > 5)
            methodTime[2] = (routeList[0].subPath[5].sectionTime.toFloat())

        val defaultLength = 110f

        for(i in 0..methodTime.size -1){
            Log.e("method Time", i.toString() + " " + methodTime[i])
        }

        for(i in 0..methodTime.size-1){
            if(methodTime[i] != 0f)
                methodLen.add(defaultLength)
            else
                methodLen.add(0f)
        }

        for(i in 0..methodTime.size -1){
            if(methodTime[i] != 0f && methodTime[i] < defaultLength * totalTime / totalPath)
                totalPath = totalPath - defaultLength + (totalPath / totalTime * methodTime[i])
        }

        for(i in 0..methodTime.size -1){
            if(methodTime[i] >= defaultLength * totalTime / totalPath) {
                methodLen[i] = totalPath / totalTime * methodTime[i]
            }
        }


        for(i in 0..methodTime.size -1){
            Log.e("method Len", i.toString() + " " + methodLen[i])
        }

        //Log.e("adapter", "totalTime:$totalTime firstWalkTime:$firstWalkTime secondWalkTime:$secondWalkTime thirdWalkTime:$thirdWalkTime method1Time:$method1Time method2Time:$method2Time")

//        val method1Len = holder.totalPath / ((totalTime / method1Time).toFloat()) //totalPath에서 (totalTime / method1Time)만큼의 비율을 차지
//        var method2Len = 0f
//        if(method2Time != 0f)
//            method2Len = holder.totalPath / ((totalTime / method2Time).toFloat())
//        var method3Len = 0f
//        if(method3Time != 0f)
//            method3Len = holder.totalPath / ((totalTime / method3Time).toFloat())
        Log.e("holder.totalPath.width", (holder.totalPath).toString())
//        Log.e("adapter", "method1Len:$method1Len method2Len:$method2Len method3Len:$method3Len")

//        val method1Margin = holder.totalPath / (totalTime / firstWalkTime)
//        val method2Margin = (holder.totalPath / (totalTime / (firstWalkTime+method1Time+secondWalkTime).toFloat())).toInt()
//        val method3Margin = (holder.totalPath / (totalTime / (firstWalkTime+method1Time+secondWalkTime+method2Time+thirdWalkTime).toFloat())).toInt()
//        var method1Margin = 0f
//        if(firstWalkTime !=0f)
//            method1Margin = holder.totalPath / (totalTime / firstWalkTime).toFloat()
//        var method2Margin = method1Margin + method1Len
//        if(secondWalkTime != 0f)
//            method2Margin = method1Margin + method1Len + (holder.totalPath / (totalTime/secondWalkTime).toFloat())
//        var method3Margin = method2Margin + method2Len
//        if(thirdWalkTime != 0f)
//            method3Margin = method2Margin + method2Len + (holder.totalPath / (totalTime/thirdWalkTime).toFloat())
       // Log.e("adapter", "method1Margin:$method1Margin method2Margin:$method2Margin method3Margin:$method3Margin")

        var method1Margin = 0f
        if(firstWalkTime !=0f)
            method1Margin = holder.totalPath / (totalTime / firstWalkTime).toFloat()
        var method2Margin = method1Margin + methodLen[0]
        if(secondWalkTime != 0f)
            method2Margin = method1Margin + methodLen[0] + (holder.totalPath / (totalTime/secondWalkTime).toFloat())
        var method3Margin = method2Margin + methodLen[1]
        if(thirdWalkTime != 0f)
            method3Margin = method2Margin + methodLen[1] + (holder.totalPath / (totalTime/thirdWalkTime).toFloat())


//        val method1Params = holder.method1?.layoutParams  as? ConstraintLayout.LayoutParams
//        method1Params?.width = method1Len.toInt()
//        method1Params?.marginStart = method1Margin.toInt()
//        holder.method1?.layoutParams = method1Params
//
//        val method2Params = holder.method2?.layoutParams  as? ConstraintLayout.LayoutParams
//        method2Params?.width = method2Len.toInt()
//        method2Params?.marginStart = method2Margin.toInt()
//        holder.method2?.layoutParams = method2Params
//
//        val method3Params = holder.method3?.layoutParams  as? ConstraintLayout.LayoutParams
//        method3Params?.width = method3Len.toInt()
//        method3Params?.marginStart = method3Margin.toInt()
//        holder.method3?.layoutParams = method3Params

        val method1Params = holder.method1?.layoutParams  as? ConstraintLayout.LayoutParams
        method1Params?.width = methodLen[0].toInt()
        method1Params?.marginStart = method1Margin.toInt()
        holder.method1?.layoutParams = method1Params

            val method2Params = holder.method2?.layoutParams  as? ConstraintLayout.LayoutParams
            method2Params?.width = methodLen[1].toInt()
            method2Params?.marginStart = method2Margin.toInt()
            holder.method2?.layoutParams = method2Params


            val method3Params = holder.method3?.layoutParams  as? ConstraintLayout.LayoutParams
            method3Params?.width = methodLen[2].toInt()
            method3Params?.marginStart = method3Margin.toInt()
            holder.method3?.layoutParams = method3Params


    }

    inner class PlaceSearchRouteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val time: TextView = view.findViewById(R.id.li_place_search_route_tv_time)
        val method: TextView = view.findViewById(R.id.li_place_search_route_tv_method)
        val transfer: TextView = view.findViewById(R.id.li_place_search_route_tv_transfer)
        val walk: TextView = view.findViewById(R.id.li_place_search_route_tv_walk)
        val money: TextView = view.findViewById(R.id.li_place_search_route_tv_money)

        val totalPath = 996f
        val method1: ConstraintLayout? = view.findViewById(R.id.li_place_search_route_cl_method_1)
        val method2:ConstraintLayout? = view.findViewById(R.id.li_place_search_route_cl_method_2)
        val method3: ConstraintLayout? = view.findViewById(R.id.li_place_search_route_cl_method_3)

        var method1Img: ImageView = view.findViewById(R.id.li_place_search_route_iv_method_1)
        var method2Img: ImageView = view.findViewById(R.id.li_place_search_route_iv_method_2)
        var method3Img: ImageView = view.findViewById(R.id.li_place_search_route_iv_method_3)

        var method1Tx: TextView = view.findViewById(R.id.li_place_search_route_tv_method_1)
        var method2Tx: TextView = view.findViewById(R.id.li_place_search_route_tv_method_2)
        var method3Tx: TextView = view.findViewById(R.id.li_place_search_route_tv_method_3)

        fun bind(data: Path) {
            val hour = data.totalTime / 60
            val min = data.totalTime % 60
            val method1Color = "#e8146d" //첫번째 경로 색깔
            val method2Color = "#e8146d" //두번째 경로 색깔
            val method3Color = "#e8146d" //세번째 경로 색깔
            time.text = String.format("%d시간 %d분", hour, min)
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