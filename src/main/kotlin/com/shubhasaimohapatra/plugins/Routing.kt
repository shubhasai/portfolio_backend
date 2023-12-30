package com.shubhasaimohapatra.plugins

import com.shubhasaimohapatra.models.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import netscape.javascript.JSObject

fun Application.configureRouting() {
    routing {
        //Projects
        get("/projects") {
            try {
                val projects = application.getAllProjects()
                call.respond(projects)
            }catch (e:Exception){
                call.respondText("Unable to fetch due to an error: $e", status = HttpStatusCode.InternalServerError)

            }

        }
        post("/addproject") {
            val projectDetails = call.receive<ProjectDetails>()
            try {
               application.addProject(projectDetails)
                call.respondText("Successfully Added")
            } catch (e: Exception) {
                call.respondText("Unable to insert due to an error: $e", status = HttpStatusCode.InternalServerError)
            }
        }
        post("/deleteproject") {
            @Serializable
            data class projectdata(
                val _id:String
            )
            val _data = call.receive<projectdata>()
            try {
                application.deleteProject(_data._id)
                call.respondText("Successfully Deleted")
            } catch (e: Exception) {
                call.respondText("Unable to Delete due to an error: $e", status = HttpStatusCode.InternalServerError)
            }
        }

        //WorkExperience
        get("/workexps") {
            try {
                val exp = application.getAllExp()
                call.respond(exp)
            }catch (e:Exception){
                call.respondText("Unable to fetch due to an error: $e", status = HttpStatusCode.InternalServerError)

            }

        }
        post("/addworkexp") {
            val projectDetails = call.receive<WorkExperience>()
            try {
                application.addWork(projectDetails)
                call.respondText("Successfully Added")
            } catch (e: Exception) {
                call.respondText("Unable to insert due to an error: $e", status = HttpStatusCode.InternalServerError)
            }
        }
        post("/deleteworkexp") {
            @Serializable
            data class workexpdata(
                val _id:String
            )
            val _data = call.receive<workexpdata>()
            try {
                application.deleteWork(_data._id)
                call.respondText("Successfully Deleted")
            } catch (e: Exception) {
                call.respondText("Unable to Delete due to an error: $e", status = HttpStatusCode.InternalServerError)
            }
        }

        //Community Experience
        get("/commexps") {
            try {
                val commexp = application.getAllCommExp()
                call.respond(commexp)
            }catch (e:Exception){
                call.respondText("Unable to fetch due to an error: $e", status = HttpStatusCode.InternalServerError)

            }

        }
        post("/addcommexp") {
            val projectDetails = call.receive<CommunityExperience>()
            try {
                application.addCommExp(projectDetails)
                call.respondText("Successfully Added")
            } catch (e: Exception) {
                call.respondText("Unable to insert due to an error: $e", status = HttpStatusCode.InternalServerError)
            }
        }
        post("/deletecommexp") {
            @Serializable
            data class commexpdata(
                val _id:String
            )
            val _data = call.receive<commexpdata>()
            try {
                application.deleteCommExp(_data._id)
                call.respondText("Successfully Deleted")
            } catch (e: Exception) {
                call.respondText("Unable to Delete due to an error: $e", status = HttpStatusCode.InternalServerError)
            }
        }

        //Skills
        get("/skills") {
            try {
                val skill = application.getAllSkills()
                call.respond(skill)
            }catch (e:Exception){
                call.respondText("Unable to fetch due to an error: $e", status = HttpStatusCode.InternalServerError)

            }

        }
        post("/addskill") {
            val skillDetails = call.receive<Skill>()
            try {
                application.addSkill(skillDetails)
                call.respondText("Successfully Added")
            } catch (e: Exception) {
                call.respondText("Unable to insert due to an error: $e", status = HttpStatusCode.InternalServerError)
            }
        }
        post("/deleteskill") {
            @Serializable
            data class skillpdata(
                val _id:String
            )
            val _data = call.receive<skillpdata>()
            try {
                application.deleteSkill(_data._id)
                call.respondText("Successfully Deleted")
            } catch (e: Exception) {
                call.respondText("Unable to Delete due to an error: $e", status = HttpStatusCode.InternalServerError)
            }
        }

        //Acheivements
        get("/achs") {
            try {
                val commexp = application.getAllAch()
                call.respond(commexp)
            }catch (e:Exception){
                call.respondText("Unable to fetch due to an error: $e", status = HttpStatusCode.InternalServerError)

            }

        }
        post("/addach") {
            val acheivementDetails = call.receive<AcheivementDetails>()
            try {
                application.addAch(acheivementDetails)
                call.respondText("Successfully Added")
            } catch (e: Exception) {
                call.respondText("Unable to insert due to an error: $e", status = HttpStatusCode.InternalServerError)
            }
        }
        post("/deleteach") {
            @Serializable
            data class achdata(
                val _id:String
            )
            val _data = call.receive<achdata>()
            try {
                application.deleteAch(_data._id)
                call.respondText("Successfully Deleted")
            } catch (e: Exception) {
                call.respondText("Unable to Delete due to an error: $e", status = HttpStatusCode.InternalServerError)
            }
        }
    }
}
