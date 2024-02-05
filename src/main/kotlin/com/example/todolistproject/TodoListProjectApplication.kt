package com.example.todolistproject

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TodoListProjectApplication

fun main(args: Array<String>) {
	runApplication<TodoListProjectApplication>(*args)
}
