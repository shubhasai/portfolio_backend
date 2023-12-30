package com.shubhasaimohapatra.models
import kotlinx.serialization.Serializable

@Serializable
data class Skill(
    val _id:String,
    val skill:String,
    val rating:Float
)
