package com.shubhasaimohapatra.models
import kotlinx.serialization.Serializable

@Serializable
data class BlogDetails(
    val _id:String,
    val title:String,
    val tags:ArrayList<String> = ArrayList(),
    val thumbnailImage:String,
    val description:String,
    val externalUrl:String
)
