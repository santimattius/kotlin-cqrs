package com.example

import com.example.configurations.plugins.configureKoin
import com.example.configurations.plugins.configureRouting
import com.example.configurations.plugins.configureSerialization
import io.ktor.client.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.encodeToJsonElement
import org.koin.dsl.ModuleDeclaration
import org.koin.dsl.module

suspend fun ApplicationTestBuilder.execute(call: suspend (client: HttpClient) -> Unit) {
    call(client)
}

fun ApplicationTestBuilder.testConfigure(moduleDeclaration: ModuleDeclaration = {}) {
    application {
        configure(moduleDeclaration)
    }
}

private fun Application.configure(moduleDeclaration: ModuleDeclaration = {}) {
    configureSerialization()
    configureKoin(listOf(module(moduleDeclaration = moduleDeclaration)))
    configureRouting()
}

fun integrationTest(block: suspend ApplicationTestBuilder.() -> Unit) = testApplication(block)


suspend inline fun <reified T> HttpResponse.data(): T {
    val jsonString: String = this.bodyAsText()
    val json = Json {
        ignoreUnknownKeys = true
    }
    return json.decodeFromString(jsonString)
}

inline fun <reified T> TestApplicationResponse.data(): T? {
    val jsonString = this.content
    val json = Json {
        ignoreUnknownKeys = true
    }
    return jsonString?.let { json.decodeFromString(it) }
}

inline fun <reified T> parse(value: T): JsonElement {
    val json = Json {
        ignoreUnknownKeys = true
    }
    return json.encodeToJsonElement(value)
}

@Deprecated(
    message = "use integrationTest",
    level = DeprecationLevel.WARNING,
    replaceWith = ReplaceWith("integrationTest")
)
fun <R> legacyIntegrationTest(test: TestApplicationEngine.() -> R): R = withTestApplication(test)

fun TestApplicationEngine.testConfigure(moduleDeclaration: ModuleDeclaration = {}) {
    application.configure(moduleDeclaration)
}

fun TestApplicationEngine.post(
    urlString: String,
    setup: TestApplicationRequest.() -> Unit = {}
): TestApplicationResponse {
    return with(handleRequest(HttpMethod.Post, urlString, setup)) { response }
}

fun TestApplicationEngine.put(
    urlString: String,
    setup: TestApplicationRequest.() -> Unit = {}
): TestApplicationResponse {
    return with(handleRequest(HttpMethod.Put, urlString, setup)) { response }
}

inline fun <reified T> TestApplicationRequest.body(content: T) {
    setBody(parse(content).toString())
}

fun TestApplicationRequest.header(contentType: ContentType) {
    addHeader(HttpHeaders.ContentType, contentType.toString())
}