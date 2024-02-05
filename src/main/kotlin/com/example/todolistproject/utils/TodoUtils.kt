package com.example.todolistproject.utils

import com.example.todolistproject.entities.Todo

class TodoUtils {

    companion object {
        val statusFormat = Regex("""TODO|DONE|DELAYED""")

        fun getStringFromTodo(todo: Todo): String {
            val name = todo.name
            val description = todo.description
            val status = todo.status

            return "*Название:* $name,  *Описание:* $description,  *Статус:* $status\n"
        }
    }
}