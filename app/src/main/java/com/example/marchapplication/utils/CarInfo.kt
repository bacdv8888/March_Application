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
        textEN = "English text for CX-90",
        textJP = "日本語テキスト CX-90"
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