package me.reflect.todo.common.network

import me.reflect.todo.common.network.model.core.NetworkProject
import me.reflect.todo.common.network.model.core.TasksResponse
import me.reflect.todo.common.network.model.core.NetworkTask
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CoreService {
    @GET("/core/task/my")
    suspend fun getMyTasks(): List<NetworkTask>

    @GET("/core/task/id/{id}")
    suspend fun getTaskById(id: String): NetworkTask

    @GET("/core/task/project/{id}")
    suspend fun getProjectTasksById(@Path("id") id: String): List<NetworkTask>

    @POST("/core/task")
    suspend fun addTask(@Body task: NetworkTask): NetworkTask

    @PUT("/core/task/id/{id}")
    suspend fun updateTaskById(@Path("id") id: String, @Body task: NetworkTask): NetworkTask

    @DELETE("/core/task/id/{id}")
    suspend fun deleteTaskById(@Path("id") id: Long): Response<ResponseBody>

    @GET("/core/project/{id}")
    suspend fun getProjectById(@Path("id") id: String): NetworkProject

    @POST("/core/project")
    suspend fun addProject(@Body project: NetworkProject): NetworkProject

    @PUT("/core/project/{id}")
    suspend fun updateProjectById(@Path("id") id: String, @Body project: NetworkProject): NetworkProject

    @DELETE("/core/project/{id}")
    suspend fun deleteProjectById(@Path("id") id: String): Response<ResponseBody>
}