package com.devaon.early_buddy_android.feature.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.devaon.early_buddy_android.R

class HomeRouteDetailAdapter(var routeDetail: ArrayList<String>, val viewType: Int, val code: Int) :
    RecyclerView.Adapter<HomeRouteDetailViewHolder>() {
    override fun getItemCount(): Int {
        return routeDetail.size
    }

    override fun onBindViewHolder(holder: HomeRouteDetailViewHolder, position: Int) {
        when (position) {
            0 -> {
                holder.stopView.visibility = View.GONE
            }
            (routeDetail.size - 1) -> {
                holder.stopView.visibility = View.GONE
            }
            else -> {
                //역 뒤에 붙이고 말고 결정
                when (viewType) {
                    1 -> {
                        holder.stopStation.text = String.format("%s역", routeDetail[position])
                        when (code) {
                            1 -> holder.stopPoint.setImageResource(R.drawable.img_path_point_one)
                            2 -> holder.stopPoint.setImageResource(R.drawable.img_path_point_two)
                            3 -> holder.stopPoint.setImageResource(R.drawable.img_path_point_three)
                            4 -> holder.stopPoint.setImageResource(R.drawable.img_path_point_four)
                            5 -> holder.stopPoint.setImageResource(R.drawable.img_path_point_five)
                            6 -> holder.stopPoint.setImageResource(R.drawable.img_path_point_six)
                            7 -> holder.stopPoint.setImageResource(R.drawable.img_path_point_seven)
                            8 -> holder.stopPoint.setImageResource(R.drawable.img_path_point_eight)
                            9 -> holder.stopPoint.setImageResource(R.drawable.img_path_point_nine)
                            100 -> holder.stopPoint.setImageResource(R.drawable.img_path_point_bundang)
                            101 -> holder.stopPoint.setImageResource(R.drawable.img_path_point_airport)
                            104 -> holder.stopPoint.setImageResource(R.drawable.img_path_point_kyunguijungang)
                            107 -> holder.stopPoint.setImageResource(R.drawable.img_path_point_everline)
                            108 -> holder.stopPoint.setImageResource(R.drawable.img_path_point_kyungchun)
                            102 -> holder.stopPoint.setImageResource(R.drawable.img_path_point_jaki)
                            109 -> holder.stopPoint.setImageResource(R.drawable.img_path_point_shinbundang)
                            110 -> holder.stopPoint.setImageResource(R.drawable.img_path_point_uijeongbu)
                            113 -> holder.stopPoint.setImageResource(R.drawable.img_path_point_ui)
                        }
                    }
                    2 -> {
                        holder.stopStation.text = routeDetail[position]
                        when (code) {
                            1, 2, 11 -> holder.stopPoint.setImageResource(R.drawable.img_path_point_ganline)
                            10, 12 -> holder.stopPoint.setImageResource(R.drawable.img_path_point_jiline)
                            4, 14, 15 -> holder.stopPoint.setImageResource(R.drawable.img_path_point_gwangyuk)
                            5 -> holder.stopPoint.setImageResource(R.drawable.img_path_point_airport)
                            else -> holder.stopPoint.setImageResource(R.drawable.img_path_point_others)
                        }

                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRouteDetailViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pass_thorugh_route_detail, parent, false)
        return HomeRouteDetailViewHolder(view)
    }
}


class HomeRouteDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val stopView: ConstraintLayout = itemView.findViewById(R.id.item_cl_stop_station_view)
    val stopStation: TextView = itemView.findViewById(R.id.item_tv_stop_station_name)
    val stopPoint: ImageView = itemView.findViewById(R.id.item_tv_stop_station_point)

}