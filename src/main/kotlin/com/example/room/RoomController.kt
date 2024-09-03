package com.example.room

import com.example.data.MessageDataSource
import com.example.data.model.Message
import io.ktor.websocket.*
import java.util.concurrent.ConcurrentHashMap

class RoomController(
    private val messageDataSource: MessageDataSource
) {
    private val members = ConcurrentHashMap<String, Member>()

    fun onJoin(
        username:String,
        sessionId: String,
        socket: WebSocketSession
    ){
        if (members.containsKey(username)){
            throw MemberAlreadyExists()
        }
        members[username] = Member(
            userName = username,
            sessionId = sessionId,
            socket = socket
        )
    }

    suspend fun sendMessage(sendUsername:String, message: String){
        members.values.forEach {member ->
            val messageEntity = Message(
                text = message,
                userName = sendUsername,
                timeStamp = System.currentTimeMillis(),
            )
            messageDataSource.insertMessage(messageEntity)
        }
    }
}