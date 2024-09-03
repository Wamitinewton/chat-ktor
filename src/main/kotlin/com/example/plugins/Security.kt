package com.example.plugins

import com.example.session.ChatSession
import io.ktor.server.application.*
import io.ktor.server.sessions.*
import io.ktor.util.*

fun Application.configureSecurity() {
    install(Sessions) {
        // each user interacting with this app will have
        // their chat session stored in a web cookie called "SESSION"
        cookie<ChatSession>("SESSION")
    }
// in case of anything in the below interceptor remember to check out on the ApplicationCallPipeline.Features
    intercept(ApplicationCallPipeline.Plugins) {
        // sessions are usually
        // processed in the pipeline
        if (call.sessions.get<ChatSession>() == null) {
            val userName = call.parameters["username"] ?: "Guest"
            call.sessions.set(ChatSession(userName, generateNonce()))
        }
    }
}
