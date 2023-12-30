package com.shubhasaimohapatra.models
import kotlinx.serialization.Serializable

@Serializable
data class ProjectDetails(
    val _id:String,
    val projectTitle:String,
    val projectDescription:String,
    val thumbnailUrl:String,
    val projectTag:String,
    val skills:ArrayList<String> = ArrayList(),
    val projectLink:String,
    val videoLink:String,
    val screenshotsLink:ArrayList<String> = ArrayList()
)
