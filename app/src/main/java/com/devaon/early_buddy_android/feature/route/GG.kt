package com.devaon.early_buddy_android.feature.route

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.devaon.early_buddy_android.R

data class GG internal constructor(
    val ridingNumber: String,
    @ColorRes val ridingLine: Int,
    @DrawableRes val ridingImg: Int,
    @DrawableRes val point: Int,
    @DrawableRes val quitImg: Int
) {
    companion object {
        fun create(trafficType: Int, type: Int): GG {
            return ggMap[trafficType to type]?:GG(
                "나머지",
                R.color.seoul_bus_remainder,
                R.drawable.img_bus_others,
                R.drawable.img_path_point_others,
                R.drawable.img_stop_ohters
            )
        }
    }
}

private const val SUBWAY_TYPE = 1
private const val BUS_TYPE = 2

private val ggMap = mapOf(
    SUBWAY_TYPE to 1 to GG(
        "1호선",
        R.color.seoul_line_one,
        R.drawable.img_subway_one,
        R.drawable.img_path_point_one,
        R.drawable.img_stop_one
    ), SUBWAY_TYPE to 2 to GG(
        "2호선",
        R.color.seoul_line_two,
        R.drawable.img_subway_two,
        R.drawable.img_path_point_two,
        R.drawable.img_stop_two
    ),SUBWAY_TYPE to 3 to GG(
        "3호선",
        R.color.seoul_line_three,
        R.drawable.img_subway_three,
        R.drawable.img_path_point_three,
        R.drawable.img_stop_three
    ),SUBWAY_TYPE to 4 to GG(
        "4호선",
        R.color.seoul_line_four,
        R.drawable.img_subway_four,
        R.drawable.img_path_point_four,
        R.drawable.img_stop_four
    ),SUBWAY_TYPE to 5 to GG(
        "5호선",
        R.color.seoul_line_five,
        R.drawable.img_subway_five,
        R.drawable.img_path_point_five,
        R.drawable.img_stop_five
    ),SUBWAY_TYPE to 6 to GG(
        "6호선",
        R.color.seoul_line_six,
        R.drawable.img_subway_six,
        R.drawable.img_path_point_six,
        R.drawable.img_stop_six
    ),SUBWAY_TYPE to 7 to GG(
        "7호선",
        R.color.seoul_line_seven,
        R.drawable.img_subway_seven,
        R.drawable.img_path_point_seven,
        R.drawable.img_stop_seven
    ),SUBWAY_TYPE to 8 to GG(
        "8호선",
        R.color.seoul_line_eight,
        R.drawable.img_subway_eight,
        R.drawable.img_path_point_eight,
        R.drawable.img_stop_eight
    ),SUBWAY_TYPE to 9 to GG(
        "9호선",
        R.color.seoul_line_nine,
        R.drawable.img_subway_nine,
        R.drawable.img_path_point_nine,
        R.drawable.img_stop_nine
    ),SUBWAY_TYPE to 100 to GG(
        "분당선",
        R.color.seoul_line_bunDang,
        R.drawable.img_subway_bundang,
        R.drawable.img_path_point_bundang,
        R.drawable.img_stop_bundang
    ),SUBWAY_TYPE to 101 to GG(
        "공항철도",
        R.color.seoul_line_gongHang,
        R.drawable.img_subway_airport,
        R.drawable.img_path_point_airport,
        R.drawable.img_stop_airport
    ),SUBWAY_TYPE to 104 to GG(
        "경의중앙선",
        R.color.seoul_line_gyungJung,
        R.drawable.img_subway_kyunguijungang,
        R.drawable.img_path_point_kyunguijungang,
        R.drawable.img_stop_kyunguijungang
    ),SUBWAY_TYPE to 107 to GG(
        "에버라인",
        R.color.seoul_line_ever,
        R.drawable.img_subway_everline,
        R.drawable.img_path_point_everline,
        R.drawable.img_stop_everline
    ),SUBWAY_TYPE to 108 to GG(
        "경춘선",
        R.color.seoul_line_gyungChun,
        R.drawable.img_subway_kyungchun,
        R.drawable.img_path_point_kyungchun,
        R.drawable.img_stop_kyungchun
    ),SUBWAY_TYPE to 102 to GG(
        "자기부상철도",
        R.color.seoul_line_jaGiBuSang,
        R.drawable.img_subway_jaki,
        R.drawable.img_path_point_jaki,
        R.drawable.img_stop_jaki
    ),SUBWAY_TYPE to 109 to GG(
        "신분당선",
        R.color.seoul_line_sinBunDang,
        R.drawable.img_subway_shinbundang,
        R.drawable.img_path_point_shinbundang,
        R.drawable.img_stop_shinbundang
    ),SUBWAY_TYPE to 110 to GG(
        "의정부경전철",
        R.color.seoul_line_uiJeongBu,
        R.drawable.img_subway_uijeongbu,
        R.drawable.img_path_point_uijeongbu,
        R.drawable.img_stop_uijeongbu
    ),SUBWAY_TYPE to 113 to GG(
        "우이신설선",
        R.color.seoul_line_ueeSinSeol,
        R.drawable.img_subway_ui,
        R.drawable.img_path_point_ui,
        R.drawable.img_stop_ui
    ), BUS_TYPE to 1 to GG(
        "간선",
        R.color.seoul_bus_gan_line,
        R.drawable.img_bus_ganline,
        R.drawable.img_path_point_ganline,
        R.drawable.img_stop_ganline
    ), BUS_TYPE to 2 to GG(
        "좌석",
        R.color.seoul_bus_gan_line,
        R.drawable.img_bus_ganline,
        R.drawable.img_path_point_ganline,
        R.drawable.img_stop_ganline
    ), BUS_TYPE to 11 to GG(
        "일반",
        R.color.seoul_bus_gan_line,
        R.drawable.img_bus_ganline,
        R.drawable.img_path_point_ganline,
        R.drawable.img_stop_ganline
    ), BUS_TYPE to 10 to GG(
        "외곽",
        R.color.seoul_bus_ji_line,
        R.drawable.img_bus_jiline,
        R.drawable.img_path_point_jiline,
        R.drawable.img_stop_jiline
    ), BUS_TYPE to 12 to GG(
        "지선",
        R.color.seoul_bus_ji_line,
        R.drawable.img_bus_jiline,
        R.drawable.img_path_point_jiline,
        R.drawable.img_stop_jiline
    ), BUS_TYPE to 4 to GG(
        "직행",
        R.color.seoul_bus_gwangyuk,
        R.drawable.img_bus_gwangyuk,
        R.drawable.img_path_point_gwangyuk,
        R.drawable.img_stop_gwangyuk
    ), BUS_TYPE to 14 to GG(
        "광역",
        R.color.seoul_bus_gwangyuk,
        R.drawable.img_bus_gwangyuk,
        R.drawable.img_path_point_gwangyuk,
        R.drawable.img_stop_gwangyuk
    ), BUS_TYPE to 15 to GG(
        "급행",
        R.color.seoul_bus_gwangyuk,
        R.drawable.img_bus_gwangyuk,
        R.drawable.img_path_point_gwangyuk,
        R.drawable.img_stop_gwangyuk
    ), BUS_TYPE to 5 to GG(
        "공항",
        R.color.seoul_line_gongHang,
        R.drawable.img_bus_ap,
        R.drawable.img_path_point_airport,
        R.drawable.img_stop_airport
    ), BUS_TYPE to 13 to GG(
        "순환",
        R.color.inCheon_line_two,
        R.drawable.img_bus_circula,
        R.drawable.img_path_point_incheontwo,
        R.drawable.img_stop_incheontwo
    )
)

