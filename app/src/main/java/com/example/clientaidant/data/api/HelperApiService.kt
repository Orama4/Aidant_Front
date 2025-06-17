package com.example.clientaidant.data.api


import AssistedUser
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface HelperApiService {
    @GET("/api/helper/{userHelperId}/active-endusers")
    suspend fun getActiveEndUsers(
        @Path("userHelperId") helperUserId: Int
    ): Response<ActiveEndUsersResponse>
}


fun ActiveEndUser.toAssistedUser(): AssistedUser {
    return AssistedUser(
        id = this.id,
        name = this.username,
        location = this.addresse ?: "Unknown location",
        timestamp = this.lastPosition?.timestamp ?: this.connectedAt,
        status = when (status.lowercase()) {
            "on_the_move" -> UserStatus.ON_THE_MOVE
            "waiting" -> UserStatus.WAITING
            "in_assistance" -> UserStatus.IN_ASSISTANCE
            else -> UserStatus.WAITING
        },
        status1 = this.status,
        avatarUrl = null // Tu peux mettre une URL réelle si tu l’as
    )
}

data class ActiveEndUsersResponse(
    val success: Boolean,
    val count: Int,
    val users: List<ActiveEndUser>
)

data class ActiveEndUser(
    val id: Int,
    val userId: Int,
    val addresse: String?,
    val username: String,
    val lastPosition: LastPosition?,
    val status: String,
    val isOnline: Boolean,
    val connectedAt: String
)

data class LastPosition(
    val lat: Double,
    val lng: Double,
    val timestamp: String
)
