package com.closer.test.util.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Article(
    val id: Long,
    val title: String,

    @SerializedName("published-at")
    val publishedAt: Date,

    val hero: String? = null,
    val author: String,
    val summary: String,
    val body: String,
): Parcelable