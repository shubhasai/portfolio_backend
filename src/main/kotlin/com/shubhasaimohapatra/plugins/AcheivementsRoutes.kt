package com.shubhasaimohapatra.plugins

import com.mongodb.MongoException
import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.shubhasaimohapatra.models.AcheivementDetails
import com.shubhasaimohapatra.models.ProjectDetails
import com.shubhasaimohapatra.models.WorkExperience
import io.ktor.server.application.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun Application.getAllAch(): List<AcheivementDetails> {
    val connectionString = DatabaseUrl.url
    val mongoClient = MongoClient.create(connectionString)
    val database = mongoClient.getDatabase("portfolio")
    val collection = database.getCollection<AcheivementDetails>("ach")
    return try {
        withContext(Dispatchers.IO) {
            val list = mutableListOf<AcheivementDetails>()
            collection.find().collect { list.add(it) }
            list
        }
    } catch (e: Exception) {
        System.err.println("Error retrieving Experiences: $e")
        emptyList<AcheivementDetails>()  // Return an empty list in case of an error
    } finally {
        mongoClient.close()
    }
}
suspend fun Application.addAch(achDetails: AcheivementDetails) {
    val connectionString = DatabaseUrl.url
    val mongoClient = MongoClient.create(connectionString)
    val database = mongoClient.getDatabase("portfolio")
    val collection = database.getCollection<AcheivementDetails>("ach")

    try {
        collection.insertOne(achDetails)
    } catch (e: MongoException) {
        System.err.println("Unable to insert due to an error: $e")
        throw e  // Rethrow the exception to handle it in the route
    } finally {
        mongoClient.close()
    }
}
suspend fun Application.deleteAch(_achId: String) {
    val connectionString = DatabaseUrl.url
    val mongoClient = MongoClient.create(connectionString)
    val database = mongoClient.getDatabase("portfolio")
    val collection = database.getCollection<AcheivementDetails>("exps")
    val query = Filters.eq(AcheivementDetails::_id.name, _achId)
    println("Received From User Work Id $_achId")
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