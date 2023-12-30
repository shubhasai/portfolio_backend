package com.shubhasaimohapatra.plugins

import com.mongodb.MongoException
import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.shubhasaimohapatra.models.AcheivementDetails
import com.shubhasaimohapatra.models.ProjectDetails
import com.shubhasaimohapatra.models.Skill
import com.shubhasaimohapatra.models.WorkExperience
import io.ktor.server.application.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun Application.getAllSkills(): List<Skill> {
    val connectionString = DatabaseUrl.url
    val mongoClient = MongoClient.create(connectionString)
    val database = mongoClient.getDatabase("portfolio")
    val collection = database.getCollection<Skill>("skills")
    return try {
        withContext(Dispatchers.IO) {
            val list = mutableListOf<Skill>()
            collection.find().collect { list.add(it) }
            list
        }
    } catch (e: Exception) {
        System.err.println("Error retrieving Experiences: $e")
        emptyList<Skill>()  // Return an empty list in case of an error
    } finally {
        mongoClient.close()
    }
}
suspend fun Application.addSkill(skillDetails: Skill) {
    val connectionString = DatabaseUrl.url
    val mongoClient = MongoClient.create(connectionString)
    val database = mongoClient.getDatabase("portfolio")
    val collection = database.getCollection<Skill>("skills")

    try {
        collection.insertOne(skillDetails)
    } catch (e: MongoException) {
        System.err.println("Unable to insert due to an error: $e")
        throw e  // Rethrow the exception to handle it in the route
    } finally {
        mongoClient.close()
    }
}
suspend fun Application.deleteSkill(_skillId: String) {
    val connectionString = DatabaseUrl.url
    val mongoClient = MongoClient.create(connectionString)
    val database = mongoClient.getDatabase("portfolio")
    val collection = database.getCollection<Skill>("skills")
    val query = Filters.eq(Skill::_id.name, _skillId)
    println("Received From User Work Id $_skillId")
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