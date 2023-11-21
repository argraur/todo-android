package me.reflect.todo.common.network.model.core

import kotlinx.serialization.Serializable

@Serializable
data class NetworkProjectProperties(
    var title: String,
    var description: String,
    var memberLimit: Long,
    var logo: String, /* base64-encoded */
    var ownerId: Long
)