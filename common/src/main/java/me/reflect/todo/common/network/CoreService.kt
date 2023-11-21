package me.reflect.todo.common.network

import me.reflect.todo.common.network.model.core.NetworkProject
import me.reflect.todo.common.network.model.core.TasksResponse
import me.reflect.todo.common.network.model.core.NetworkTask
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface CoreService {
    @GET("/core/task/my")
    suspend fun getMyTasks(): TasksResponse

    @GET("/core/task/{id}")
    suspend fun getTaskById(id: String): NetworkTask

    @GET("/core/task/project/{id}")
    suspend fun getProjectTasksById(): TasksResponse

    @POST("/core/task")
    suspend fun addTask(@Body task: NetworkTask): NetworkTask

    @PUT("/core/task/{id}")
    suspend fun updateTaskById(id: String, @Body task: NetworkTask): NetworkTask

    @DELETE("/core/task/{id}")
    suspend fun deleteTaskById(id: String): Response<Any>

    @GET("/core/project/{id}")
    suspend fun getProjectById(id: String): NetworkProject

    @POST("/core/project")
    suspend fun addProject(@Body project: NetworkProject): NetworkProject

    @PUT("/core/project/{id}")
    suspend fun updateProjectById(id: String, @Body project: NetworkProject): NetworkProject

    @DELETE("/core/project/{id}")
    suspend fun deleteProjectById(id: String): Response<Any>
}