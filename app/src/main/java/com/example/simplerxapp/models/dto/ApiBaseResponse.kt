package com.example.simplerxapp.models.dto

interface ApiBaseResponse<out T> {
    val _embedded: T
}