package com.github.tywinlanni.telegramOpenAiBot

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatCompletionChunk
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.aallam.openai.client.OpenAIConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.runBlocking

class OpenAiHandler(
    openAIIAIConfig: OpenAIConfig,
    private val contextText: String,
    private val modelId: ModelId,
    private val randomMessageChance: Int,
) {
    private val openAI = OpenAI(openAIIAIConfig)
    @OptIn(BetaOpenAI::class)
    fun handle(message: String, isReply: Boolean, assistantMessage: String): String {
        var answer = ""
        if (message.contains("Владимир Вольфович") || isReply || (1..randomMessageChance).random() == 1) {

            val chatCompletionRequest = ChatCompletionRequest(
                model = modelId,
                messages = listOf(
                    ChatMessage(
                        role = ChatRole.System,
                        content = contextText
                    ),
                    ChatMessage(
                        role = ChatRole.Assistant,
                        content = assistantMessage,
                    ),
                    ChatMessage(
                        role = ChatRole.User,
                        content = message
                    ),
                )
            )
            val completions: Flow<ChatCompletionChunk> = openAI.chatCompletions(chatCompletionRequest)

            runBlocking {
                completions
                    .catch { e ->
                        println("Error: $e")
                    }
                    .collect { response ->
                        response.choices
                            .map { it.delta?.content ?: "" }
                            .forEach { answer += it }
                    }
            }

        }
        return answer
    }
}
