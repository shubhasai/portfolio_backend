package com.shubhasaimohapatra.plugins

import com.mongodb.MongoException
import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.shubhasaimohapatra.models.ProjectDetails
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.withContext

suspend fun Application.getAllProjects(): List<ProjectDetails> {
    val connectionString = DatabaseUrl.url
    val mongoClient = MongoClient.create(connectionString)
    val database = mongoClient.getDatabase("portfolio")
    val collection = database.getCollection<ProjectDetails>("projects")
    return try {
        withContext(Dispatchers.IO) {
            val list = mutableListOf<ProjectDetails>()
            collection.find().collect { list.add(it) }
            list
        }
    } catch (e: Exception) {
        System.err.println("Error retrieving projects: $e")
        emptyList<ProjectDetails>()  // Return an empty list in case of an error
    } finally {
        mongoClient.close()
    }
}
suspend fun Application.addProject(projectDetails: ProjectDetails) {
    val connectionString = DatabaseUrl.url
    val mongoClient = MongoClient.create(connectionString)
    val database = mongoClient.getDatabase("portfolio")
    val collection = database.getCollection<ProjectDetails>("projects")

    try {
        collection.insertOne(projectDetails)
    } catch (e: MongoException) {
        System.err.println("Unable to insert due to an error: $e")
        throw e  // Rethrow the exception to handle it in the route
    } finally {
        mongoClient.close()
    }
}
suspend fun Application.deleteProject(_projectId: String) {
    val connectionString = DatabaseUrl.url
    val mongoClient = MongoClient.create(connectionString)
    val database = mongoClient.getDatabase("portfolio")
    val collection = database.getCollection<ProjectDetails>("projects")
    val query = Filters.eq(ProjectDetails::_id.name, _projectId)
    println("Received From User Project Id $_projectId")
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
