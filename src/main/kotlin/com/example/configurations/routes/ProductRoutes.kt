package com.example.configurations.routes

import com.example.configurations.plugins.inject
import com.example.module.product.infrastructure.Product
import com.example.module.product.infrastructure.controllers.ProductGetController
import com.example.module.product.infrastructure.controllers.ProductPostController
import com.example.module.product.infrastructure.controllers.ProductPutController
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put


fun Routing.productV1() {
    getProduct()
    postProduct()
    putProduct()
}

fun Route.getProduct() {
    val controller: ProductGetController by inject()

    get("/v1/product/{productId}") {

        val productId = call.parameters["productId"]
        if (productId.isNullOrBlank()) {
            call.respond(HttpStatusCode.BadRequest, "ProductId required")
        } else {
            val (code, response) = controller.get(id = productId)
            call.respond(code, response ?: "{}")
        }
    }

    get("/v1/product/all") {
        val (code, response) = controller.get()
        call.respond(code, response)
    }
}

fun Route.postProduct() {

    val controller: ProductPostController by inject()

    post("/v1/product") {
        val product = call.receive<Product>()
        val (code, response) = controller.post(product)
        call.respond(code, response ?: "{}")
    }
}

fun Route.putProduct() {

    val controller: ProductPutController by inject()

    put("/v1/product") {
        val product = call.receive<Product>()
        val (code, response) = controller.put(product)
        call.respond(code, response ?: "{}")
    }
}
