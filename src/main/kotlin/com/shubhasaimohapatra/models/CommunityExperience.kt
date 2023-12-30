package com.shubhasaimohapatra.models

import kotlinx.serialization.Serializable

@Serializable
data class CommunityExperience(
    val _id:String,
    val positionTitle:String,
    val communityName:String,
    val positionDescription:String,
    val startDate:String,
    val endDate:String,
    val skills:ArrayList<String> = ArrayList(),
    val communityLink:String
)
