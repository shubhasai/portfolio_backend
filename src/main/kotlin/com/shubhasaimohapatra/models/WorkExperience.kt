package com.shubhasaimohapatra.models
import kotlinx.serialization.Serializable

@Serializable
data class WorkExperience(
    val _id:String,
    val jobTitle:String,
    val companyName:String,
    val jobDescription:String,
    val startDate:String,
    val endDate:String,
    val skills:ArrayList<String> = ArrayList(),
    val companyLink:String
)
