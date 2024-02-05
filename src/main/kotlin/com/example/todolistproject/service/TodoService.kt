package com.example.todolistproject.service

import com.example.todolistproject.entities.Todo
import com.example.todolistproject.enum.Status
import com.example.todolistproject.repository.TodoRepository
import com.example.todolistproject.utils.TodoUtils.Companion.getStringFromTodo
import com.example.todolistproject.utils.TodoUtils.Companion.statusFormat
import org.springframework.stereotype.Service

@Service
class TodoService(private val todoRepository: TodoRepository) {

    fun getTodos(): String {
        val todos = todoRepository.findAll()
        val sb = StringBuilder()
        for (todo: Todo in todos) {
            sb.append(getStringFromTodo(todo))
        }
        return sb.toString()
    }

    fun getDone(): String {
        val todos = todoRepository.findAllByStatus(Status.DONE)
        val sb = StringBuilder()
        for (todo: Todo in todos) {
            sb.append(getStringFromTodo(todo))
        }
        return sb.toString()
    }

    fun createTodo(arguments: List<String>): String {
        val data = arguments[1].split("|")
        val name = data.first()
        val description = data.last()
        if (name.length > 50) {
            return "Слишком длинное название! Не стоит так подробно описывать свои задачи."
        }
        val existingTask = todoRepository.findByName(name)
        if (existingTask != null) {
            return "Такая задача уже существует!"
        }
        val todo = Todo(name = name, description = description, status = Status.TODO)
        todoRepository.save(todo)

        return "Задача \"${todo.name}\" успешно добавлена!"
    }

    fun updateTodo(arguments: List<String>): String {
        val data = arguments[1].split("|")
        val name = data.first()
        val status = data.last()
        val existingTodo = todoRepository.findByName(name)
            ?: return "Такой задачи не существует!"
        if (!statusFormat.matches(status)) {
            return "Не известный статус! Все статусы задач, перечислены здесь: /help"
        }
        val statusValue = Status.valueOf(status)
        existingTodo.status = statusValue
        todoRepository.save(existingTodo)

        return "Статус задачи успешно изменен!"
    }

    fun deleteTodo(arguments: List<String>): String {
        val name = arguments.subList(1, arguments.size).joinToString(" ")
        val existingTodo = todoRepository.findByName(name)
            ?: return "Не получится удалить, такой задачи не существует!"
        todoRepository.delete(existingTodo)
        return "Задача успешно удалена!"
    }

}