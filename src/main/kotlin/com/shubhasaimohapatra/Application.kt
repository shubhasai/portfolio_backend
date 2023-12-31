package com.shubhasaimohapatra

import com.shubhasaimohapatra.models.ProjectDetails
import com.shubhasaimohapatra.plugins.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.cors.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.FileInputStream
import java.util.*

fun main() {
    val port = System.getenv("PORT")?.toInt() ?: 8080 // Default to 8080 if not set
    val host = System.getenv("HOST") ?: "127.0.0.1" // Default to "0.0.0.0"
    embeddedServer(Netty, port = port, host = host, module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val properties = Properties()

// Use getResourceAsStream to load the properties file
    val inputStream = this::class.java.classLoader.getResourceAsStream("local.properties")
    if (inputStream != null) {
        properties.load(inputStream)
        DatabaseUrl.url = properties.getProperty("DATABASEURL")
    } else {
        // Fallback to environment variable if local.properties is not found
        DatabaseUrl.url = System.getenv("DATABASEURL")
    }
    install(CORS) {
        anyHost()
    }
    configureSerialization()
    configureRouting()
}
