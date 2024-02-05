package com.example.todolistproject.bot

import com.example.todolistproject.service.TodoService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

@Service
class TodoBot(
    @Value("\${bot.token}") token: String,
    private val todoService: TodoService
): TelegramLongPollingBot(token) {

    @Value("\${bot.name}")
    private var botName: String = ""

    override fun getBotUsername(): String = botName

    override fun onUpdateReceived(update: Update) {
        if (update.hasMessage()) {
            val message = update.message
            val chatId = message.chatId

            if (message.hasText()) {
                val arguments = message.text.split(" ", limit=2)
                when (arguments[0]) {
                    "/start" -> {
                        getStartMessage(chatId)
                    }
                    "/add" -> {
                        if (arguments.size < 2) getArgumentMessage(chatId)
                        else getAddMessage(chatId, arguments)
                    }
                    "/edit" -> {
                        if (arguments.size < 2) getArgumentMessage(chatId)
                        else getEditMessage(chatId, arguments)
                    }
                    "/all" -> {
                        getAllTodos(chatId)
                    }
                    "/done" -> {
                        getDoneMessage(chatId)
                    }
                    "/help" -> {
                        getHelpMessage(chatId)
                    }
                    "/delete" -> {
                        if (arguments.size < 2) getArgumentMessage(chatId)
                        else getDeleteMessage(chatId, arguments)
                    }
                }
            } else {
                getIllegalMessage(chatId)
            }
        }
    }

    private fun getDoneMessage(chatId: Long) {
        val getMessage = todoService.getDone()
        val sendMessage = SendMessage(chatId.toString(), "Выполненные задачи: \n\n$getMessage")
        sendMessage.enableMarkdown(true)
        execute(sendMessage)
    }

    private fun getDeleteMessage(chatId: Long, arguments: List<String>) {
        val deleteMessage = todoService.deleteTodo(arguments)
        execute(SendMessage(chatId.toString(), deleteMessage))
    }

    private fun getHelpMessage(chatId: Long) {
        val helpMessage = SendMessage(chatId.toString(), "*Список доступных команд TodoTestTelegramBot:*\n\n" +
                "*/start* - приветственное сообщение\n" +
                "*/add* `{name}`|`{description}` - добавление новой задачи.\n" +
                "Новая задача имеет статус _TODO_\n" +
                "*/edit* `{name}`|`{new status}` - изменение статуса у уже существующей задачи.\nВарианты статусов:" +
                "\n  _TODO_ - предстоит сделать, \n  _DONE_ - готово, \n  _DELAYED_ - отложено на неопределенный срок\n" +
                "*/delete* `{name}` - удаление задачи\n" +
                "*/all* - список всех задач\n" +
                "*/done* - список выполненных задач"
        )
        helpMessage.enableMarkdown(true)
        execute(helpMessage)
    }

    private fun getAllTodos(chatId: Long) {
        val getMessage = todoService.getTodos()
        val sendMessage = SendMessage(chatId.toString(), "*Все ваши задачи:* \n\n$getMessage")
        sendMessage.enableMarkdown(true)
        execute(sendMessage)
    }

    private fun getEditMessage(chatId: Long, args: List<String>) {
        val editMessage = todoService.updateTodo(args)
        execute(SendMessage(chatId.toString(), editMessage))
    }

    private fun getIllegalMessage(chatId: Long) {
        val illegalMessage = SendMessage(chatId.toString(), "*Я не понимаю, о чем вы говорите.* /help")
        illegalMessage.enableMarkdown(true)
        execute(illegalMessage)
    }

    private fun getAddMessage(chatId: Long, args: List<String>) {
        val addMessage = todoService.createTodo(args)
        execute(SendMessage(chatId.toString(), addMessage))
    }

    private fun getArgumentMessage(chatId: Long) {
        val argumentMessage = SendMessage(chatId.toString(), "*Недостаточно информации!* /help")
        argumentMessage.enableMarkdown(true)
        execute(argumentMessage)
    }

    private fun getStartMessage(chartId: Long) {
        val startMessage = SendMessage(chartId.toString(), "*Добро пожаловать!\n\nВведите команду* /help, " +
                "*чтобы узнать возможности бота*")
        startMessage.enableMarkdown(true)
        execute(startMessage)
    }

}