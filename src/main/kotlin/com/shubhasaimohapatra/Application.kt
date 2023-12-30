package com.shubhasaimohapatra

import com.shubhasaimohapatra.models.ProjectDetails
import com.shubhasaimohapatra.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.FileInputStream
import java.util.*

fun main() {
    val port = System.getenv("PORT")?.toInt() ?: 8080 // Default to 8080 if PORT is not set
    embeddedServer(Netty, port = port, host = "127.0.0.1", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val properties = Properties()
    val propertiesPath = this::class.java.classLoader.getResource("local.properties")?.path
    propertiesPath?.let { path ->
        FileInputStream(path).use { properties.load(it) }
    }
    val url = properties.getProperty("databaseurl")
    if(url!=null){
        DatabaseUrl.url = url
    }
    else{
        DatabaseUrl.url = System.getenv("databaseurl")
    }
    configureSerialization()
    configureRouting()
}
