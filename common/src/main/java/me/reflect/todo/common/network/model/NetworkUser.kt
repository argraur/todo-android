package me.reflect.todo.common.network.model
import kotlinx.serialization.Serializable

@Serializable
data class NetworkUser(
    var userId: Long,
    var email: String,
    var firstName: String,
    var lastName: String,
    var avatar: String /* base64-encoded */
)