package com.swalisoft.payer.todo.data

import com.swalisoft.payer.todo.domain.ToDoModel
import com.swalisoft.payer.todo.domain.ToDoRepository
import com.swalisoft.payer.todo.domain.Status

class ToDoRepositoryImp(
    private val remote: TaskHttpDataSource,
    private val tokenProvider: suspend () -> String
) : ToDoRepository {
    override suspend fun getTasks(): Result<List<ToDoModel>> {
        return try {
            val token = tokenProvider()
            val dtos = remote.getTasks(token)
            val tasks = dtos.map { it.toDomain() }
            Result.success(tasks)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createTask(task: ToDoModel): Result<ToDoModel> {
        return try {
            val token = tokenProvider()
            val dto = task.toCreateDto()
            val created = remote.createTask(token, dto)
            Result.success(created.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateTask(id: String, task: ToDoModel): Result<ToDoModel> {
        return try {
            val token = tokenProvider()
            val dto = task.toDto()
            val updated = remote.updateTask(token, id, dto)
            Result.success(updated.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteTask(id: String): Result<Unit> {
        return try {
            val token = tokenProvider()
            remote.deleteTask(token, id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

fun TaskDto.toDomain() = ToDoModel(
    id = id,
    titulo = title,
    descripcion = description ?: "",
    status = when (status) {
        "COMPLETED" -> Status.COMPLETED
        "ARCHIVE" -> Status.ARCHIVE
        else -> Status.PENDING
    },
    fechaCreacion = creationDate,
    fechaFinalizacion = dueDate ?: ""
)

fun ToDoModel.toDto() = TaskDto(
    id = id,
    title = titulo,
    description = descripcion,
    creationDate = fechaCreacion,
    dueDate = fechaFinalizacion,
    status = status.name
)

fun ToDoModel.toCreateDto() = TaskCreateDto(
    title = titulo,
    description = descripcion,
    dueDate = fechaFinalizacion,
)