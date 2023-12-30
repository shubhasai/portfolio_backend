package com.shubhasaimohapatra.models

import kotlinx.serialization.Serializable

@Serializable
data class AcheivementDetails(
    val _id:String,
    val name:String,
    val description:String,
    val date:String
)
