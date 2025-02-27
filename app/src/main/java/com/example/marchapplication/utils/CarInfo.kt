package com.example.marchapplication.utils

import com.example.marchapplication.R

data class CarInfo(
    val carName: String,
    val imageResId: Int,
    val textEN: String,
    val textJP: String
)

val carInfoList = listOf(
    CarInfo(
        carName = "CX-90",
        imageResId = R.drawable.a,
        textEN = "The CX-90 was revealed on 31 January 2023, for the North American market," +
                " while a release in the Australian and Middle Eastern markets occurred later during 2023." +
                "[7][10] The CX-90 is available with a choice of six, seven, or eight-passenger seat configurations." +
                " The CX-70, the two-row variant with five seats, was revealed on 30 January 2024 in North America." +
                " The CX-70 differs from the CX-90 by its front and rear bumper designs, black exterior accents replacing chrome," +
                " unique wheel designs, and differing interior color scheme options.[11]" +
                "With the use of the rear-wheel-drive-biased large vehicle platform, " +
                "the CX-90 and CX-70 are placed into the brand's internal Large Product Group category." +
                " According to Mazda, the CX-90 addresses many of the weaknesses of the CX-9 such as its small interior space.[12]" +
                "The CX-90 and CX-70 are also equipped with the Kinematic Posture Control technology, " +
                "a feature originally developed for the 2022 MX-5 Miata and also present in the similar CX-60." +
                " The software suppresses the vehicle's body lift during cornering to improve stability and traction," +
                " helping occupants maintain a natural posture.[5]"+
                "The CX-9 was discontinued in North America, replaced by the CX-90 and CX-70." +
                "[8] Like its predecessor, the CX-90 will not be sold in Jap",

        textJP = "これは日本語の文章です。日々の生活に関して、健康を維持することは非常に重要です。" +
                "適度な運動やバランスが取れた食事が、体調を整えるために役立ちます。" +
                "また、良質な睡眠を確保することで、心身の調和が保たれます。"
    ),
    CarInfo(
        carName = "CX-80",
        imageResId = R.drawable.b,
        textEN = "English text for CX-80",
        textJP = "日本語テキスト CX-80"
    ),
    CarInfo(
        carName = "CX-8",
        imageResId = R.drawable.c,
        textEN = "English text for CX-8",
        textJP = "日本語テキスト CX-8"
    ),
    CarInfo(
        carName = "CX-70",
        imageResId = R.drawable.d,
        textEN = "English text for CX-70",
        textJP = "日本語テキスト CX-70"
    ),
    CarInfo(
        carName = "CX-60",
        imageResId = R.drawable.e,
        textEN = "English text for CX-60",
        textJP = "日本語テキスト CX-60"
    ),
    CarInfo(
        carName = "CX-50",
        imageResId = R.drawable.f,
        textEN = "English text for CX-50",
        textJP = "日本語テキスト CX-50"
    ),
    CarInfo(
        carName = "CX-5",
        imageResId = R.drawable.g,
        textEN = "English text for CX-5",
        textJP = "日本語テキスト CX-5"
    ),
    CarInfo(
        carName = "CX-30",
        imageResId = R.drawable.h,
        textEN = "English text for CX-30",
        textJP = "日本語テキスト CX-30"
    ),
    CarInfo(
        carName = "CX-3",
        imageResId = R.drawable.i,
        textEN = "English text for CX-3",
        textJP = "日本語テキスト CX-3"
    ),
    CarInfo(
        carName = "BT-50",
        imageResId = R.drawable.k,
        textEN = "English text for BT-50",
        textJP = "日本語テキスト BT-50"
    ),
    CarInfo(
        carName = "MX-5",
        imageResId = R.drawable.l,
        textEN = "English text for MX-5",
        textJP = "日本語テキスト MX-5"
    ),
    CarInfo(
        carName = "MX-30",
        imageResId = R.drawable.m,
        textEN = "English text for MX-30",
        textJP = "日本語テキスト MX-30"
    ),
    // Add more CarInfo objects for other car types
)