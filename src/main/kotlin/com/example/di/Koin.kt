package com.example.di

import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin


fun Application.configureDi(){
    install(Koin){
        modules(com.example.di.mainModule)
    }
}