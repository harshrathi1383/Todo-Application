package com.example.todoapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TodoDao {
    @Insert()
    suspend fun insertTodoItem(todoItem: TodoItem)
    @Query("Select * from TodoItem where isCompleted == 0")
    fun getTodoItem(): LiveData<List<TodoItem>>
    @Query("Update TodoItem set isCompleted = 1 where id= :uid")
    fun completeTask(uid: Long)
    @Query("Delete from TodoItem where id = :uid")
    fun deleteTask(uid: Long)
}