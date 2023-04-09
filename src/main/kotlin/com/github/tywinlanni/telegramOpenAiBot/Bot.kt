package com.github.tywinlanni.telegramOpenAiBot

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.entities.ChatId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.dispatcher.message


class MyBot(private val token: String, private val openAI: OpenAiHandler, private val whiteChatListIds: List<Long>): CoroutineScope {
    override val coroutineContext = Dispatchers.Default

    private val bot = bot {
        myCommands()
    }

    fun startPolling() {
        bot.startPolling()
    }

    private fun Bot.Builder.myCommands() {
        token = this@MyBot.token

        dispatch {
            command("help") {
                bot.sendMessage(
                    chatId = ChatId.fromId(update.message?.chat?.id ?: return@command),
                    text = "Я бот, который отвечает на сообщения, начинающиеся с \"Владимир Вольфович\"." +
                            " Также я отвечаю на ответы на мои сообщения." +
                            " Иногда я отвечаю на сообщения, которые не начинаются с \"Владимир Вольфович\"."
                )
            }
            message {
                val chatId = update.message?.chat?.id ?: return@message

                if (whiteChatListIds.none { it == chatId }) {
                    return@message
                }

                val text = update.message?.text ?: return@message
                val messageId = update.message?.messageId
                val isRelay = update.message?.replyToMessage?.from?.username == "vladimir_volfovich_bot"
                openAI.handle(text, isRelay, update.message?.replyToMessage?.text ?: "")
                    .takeIf { answer -> answer.isNotEmpty() }
                    ?.let { answer: String ->
                        bot.sendMessage(
                            chatId = ChatId.fromId(chatId),
                            text = answer,
                            replyToMessageId = messageId,
                        )
                    }
            }
        }
    }
}
