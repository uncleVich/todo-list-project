package com.example.todolistproject.repository

import com.example.todolistproject.entities.Todo
import com.example.todolistproject.enum.Status
import org.springframework.data.jpa.repository.JpaRepository

interface TodoRepository: JpaRepository<Todo, Long> {
    fun findByName(name: String): Todo?
    fun findAllByStatus(status: Status): List<Todo>
}