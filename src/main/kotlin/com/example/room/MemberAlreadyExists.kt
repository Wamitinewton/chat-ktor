package com.example.room

class MemberAlreadyExists: Exception(
    "There is already a user with that username in the room."
)