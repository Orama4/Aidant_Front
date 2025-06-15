package com.example.clientaidant.repositories


import android.util.Log
import com.example.clientaidant.data.api.ActiveEndUsersResponse
import com.example.clientaidant.data.api.HelperApiService

class HelperRepository(private val api: HelperApiService) {

    suspend fun getActiveEndUsers(userHelperId: Int): Result<ActiveEndUsersResponse> {
        return try {
            val response = api.getActiveEndUsers(userHelperId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("API Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Log.e("HelperRepository", "Error fetching active endusers", e)
            Result.failure(e)
        }
    }
}
