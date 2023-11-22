package me.reflect.todo.common.network

import me.reflect.todo.common.network.model.fcm.FcmRegisterModel
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface FCMService {
    @POST("/fcm/register")
    suspend fun registerFcmToken(@Body registerModel: FcmRegisterModel): Response<ResponseBody>
}