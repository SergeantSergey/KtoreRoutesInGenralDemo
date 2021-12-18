package com.example

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
    }

    routing {
        trace { application.log.trace(it.buildText()) }
        route("/weather") {

            get("/usa") {
                call.respondText {
                    "The weather in USA: snow"
                }
            }

            route("/europe", HttpMethod.Get) {
                header("systemtoken", "weathersystem") {
                    param("name") {
                        handle {
                            val name = call.parameters["name"]
                            call.respondText { "Dear $name, The weather in Europe: sunny" }
                        }
                    }
                    handle {
                        call.respondText { "The weather in Europe: sunny" }
                    }
                }
            }
        }
    }
}

