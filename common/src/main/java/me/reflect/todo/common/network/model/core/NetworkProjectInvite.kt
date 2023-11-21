package me.reflect.todo.common.network.model.core

import kotlinx.serialization.Serializable

@Serializable
data class NetworkProjectInvite(
    var id: Long,
    var projectId: Long,
    var userId: Long,
    var invitedUserId: Long,
    var status: String
)