package com.shubhasaimohapatra.plugins


import org.bson.Document
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.kotlin.client.coroutine.MongoClient
import kotlinx.coroutines.runBlocking


object MongoConnection {

    fun main():String {
        // Replace the placeholders with your credentials and hostname
        val connectionString = "mongodb+srv://mshubhasai2002:shubhasai2002@portfoliobackend.0t4upnx.mongodb.net/?retryWrites=true&w=majority"

        val serverApi = ServerApi.builder()
            .version(ServerApiVersion.V1)
            .build()

        val mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(ConnectionString(connectionString))
            .serverApi(serverApi)
            .build()

        // Create a new client and connect to the server
        MongoClient.create(mongoClientSettings).use { mongoClient ->
            val database = mongoClient.getDatabase("admin")
            runBlocking {
                database.runCommand(Document("ping", 1))
            }
            println("Pinged your deployment. You successfully connected to MongoDB!")
            return "success"
        }
    }

}
