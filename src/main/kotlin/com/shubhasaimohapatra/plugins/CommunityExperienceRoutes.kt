package com.shubhasaimohapatra.plugins

import com.shubhasaimohapatra.models.CommunityExperience

import com.mongodb.MongoException
import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.shubhasaimohapatra.models.ProjectDetails
import com.shubhasaimohapatra.models.WorkExperience
import io.ktor.server.application.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun Application.getAllCommExp(): List<CommunityExperience> {
    val connectionString = DatabaseUrl.url
    val mongoClient = MongoClient.create(connectionString)
    val database = mongoClient.getDatabase("portfolio")
    val collection = database.getCollection<CommunityExperience>("commexps")
    return try {
        withContext(Dispatchers.IO) {
            val list = mutableListOf<CommunityExperience>()
            collection.find().collect { list.add(it) }
            list
        }
    } catch (e: Exception) {
        System.err.println("Error retrieving Experiences: $e")
        emptyList<CommunityExperience>()  // Return an empty list in case of an error
    } finally {
        mongoClient.close()
    }
}
suspend fun Application.addCommExp(expDetails: CommunityExperience) {
    val connectionString = DatabaseUrl.url
    val mongoClient = MongoClient.create(connectionString)
    val database = mongoClient.getDatabase("portfolio")
    val collection = database.getCollection<CommunityExperience>("commexps")

    try {
        collection.insertOne(expDetails)
    } catch (e: MongoException) {
        System.err.println("Unable to insert due to an error: $e")
        throw e  // Rethrow the exception to handle it in the route
    } finally {
        mongoClient.close()
    }
}
suspend fun Application.deleteCommExp(_expId: String) {
    val connectionString = DatabaseUrl.url
    val mongoClient = MongoClient.create(connectionString)
    val database = mongoClient.getDatabase("portfolio")
    val collection = database.getCollection<WorkExperience>("commexps")
    val query = Filters.eq(CommunityExperience::_id.name, _expId)
    println("Received From User Work Id $_expId")
    //println("Received From DB Project Id ${ProjectDetails::_id.name}")
    try {
        val result = collection.deleteOne(query)
        if (result.deletedCount.toInt() == 0) {
            println("No document was deleted.")
        } else {
            println("Document deleted successfully.")
        }
    } catch (e: MongoException) {
        System.err.println("Unable to delete due to an error: $e")
        throw e  // Rethrow the exception to handle it in the route
    } finally {
        mongoClient.close()
    }
}