package com.example.clientaidant.repositories

import android.util.Log
import com.example.clientaidant.data.api.AuthApiService
import com.example.clientaidant.data.api.ChangePasswordRequest
import com.example.clientaidant.data.api.DeleteAccountRequest
import com.example.clientaidant.data.api.LoginRequest
import com.example.clientaidant.data.api.LoginResponse
import com.example.clientaidant.data.api.RegisterRequest
import com.example.clientaidant.data.api.ResetPasswordRequest
import com.example.clientaidant.data.api.SendOTPRequest
import com.example.clientaidant.data.api.UpdateProfileRequest
import com.example.clientaidant.data.api.UserProfileResponse
import com.example.clientaidant.data.api.VerifyOTPRequest


class AuthRepository(private val authApiService: AuthApiService) {
    suspend fun sendOTP(request: SendOTPRequest): Result<Unit> {
        Log.d("request send otp", "request send otp: $request")

        return try {
            val response = authApiService.sendOTP(request)
            Log.d("response send otp", "response send otp: $response")

            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("API error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun verifyOTP(request: VerifyOTPRequest): Result<Unit> {
        return try {
            Log.d("requets verify otp code is ","requets verify otp code is :$request")

            val response = authApiService.verifyOTP(request)
            Log.d("response verify otp code is ","response verify otp code is :$response")

            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("API error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun register(request: RegisterRequest): Result<Unit> {
        Log.d("repo1", "repo1: $request")

        return try {
            val response = authApiService.register(request)
            Log.d("response", "response: $response")

            if (response.isSuccessful) {
                // Return success without trying to access the body
                Result.success(Unit)
            } else {
                Result.failure(Exception("API error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(request: LoginRequest): Result<LoginResponse> {
        return try {
            Log.d("in repo", "in repo : $request")

            val response = authApiService.login(request)
            Log.d("in repo result ", "in repo resulet  : $response")

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("API error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }

    }
    suspend fun getProfile(token: String): Result<UserProfileResponse> {
        return try {
            Log.d("entering to repo to fetch userprofile ","fetchin with token: $token")
            val response = authApiService.getProfile("Bearer $token")
            Log.d("see response ","heyyyyyyyy $response")

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("API error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun updateProfile(token: String, request: UpdateProfileRequest): Result<Unit> {
        return try {
            val response = authApiService.updateProfile("Bearer $token", request)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("API error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun changePassword(token: String, request: ChangePasswordRequest): Result<Unit> {
        return try {
            val response = authApiService.changePassword("Bearer $token", request)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("API error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun sendForgotPasswordOTP(request: SendOTPRequest): Result<Unit> {
        return try {
            val response = authApiService.sendForgotPasswordOTP(request)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("API error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun verifyForgotPasswordOTP(request: VerifyOTPRequest): Result<Unit> {
        return try {
            val response = authApiService.verifyForgotPasswordOTP(request)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("API error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun resetPassword(request: ResetPasswordRequest): Result<Unit> {
        return try {
            val response = authApiService.resetPassword(request)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("API error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun deleteAccount(token: String, request: DeleteAccountRequest): Result<Unit> {
        return try {
            val response = authApiService.deleteAccount("Bearer $token", request)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("API error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
