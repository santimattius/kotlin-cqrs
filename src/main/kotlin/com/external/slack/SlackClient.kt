package com.external.slack

class SlackClient(
    private val hookUrl: String,
    private val setting: Map<String, String>
) {

    fun send(message: Message) {
        println(
            """
            Channel: ${message.channel}
            Message: ${message.content}  
            HookUrl: $hookUrl  
            Message: $setting
            """
        )
    }
}