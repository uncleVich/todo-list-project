package com.example.todolistproject.config

import com.example.todolistproject.bot.TodoBot
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

@Configuration
class TodoBotConfig {

    @Bean
    fun telegramBotsApi(bot: TodoBot): TelegramBotsApi =
        TelegramBotsApi(DefaultBotSession::class.java).apply { registerBot(bot) }
}