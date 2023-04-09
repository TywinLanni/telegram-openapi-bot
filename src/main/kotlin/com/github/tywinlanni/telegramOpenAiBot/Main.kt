package com.github.tywinlanni.telegramOpenAiBot

import com.aallam.openai.api.http.Timeout
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAIConfig
import kotlin.time.Duration.Companion.seconds

fun main() {
    val openApiKey = System.getenv("OPENAI_KEY")
    val telegramBotToken = System.getenv("TELEGRAM_BOT_TOKEN")
    val gptModelId = System.getenv("GPT_MODEL_ID")
    val context = System.getenv("CONTEXT")
    val randomMessageChance = System.getenv("RANDOM_MESSAGE_CHANCE")
        ?.toInt()
        ?.takeIf { it >= 1 }
        ?: 10
    val whiteChannelList = System.getenv("WHITE_CHANNEL_LIST")
        ?.split(",")
        ?.map { it.toLong() }
        ?: listOf()

    val config = OpenAIConfig(
        token = openApiKey,
        timeout = Timeout(socket = 60.seconds),
    )

    val openAiHandler = OpenAiHandler(config, context, ModelId(gptModelId), randomMessageChance)

    MyBot(telegramBotToken, openAiHandler, whiteChannelList)
        .startPolling()
}
