package com.example.configurations.container

import com.example.module.healthcheck.HealthCheckController
import com.example.module.notification.application.NotificationSender
import com.example.module.notification.domain.Notifier
import com.example.module.notification.infrastructure.NotificationPostController
import com.example.module.notification.infrastructure.SlackNotifier
import com.example.module.product.application.ProductCatalog
import com.example.module.product.application.ProductCreator
import com.example.module.product.application.ProductSearcher
import com.example.module.product.application.ProductUpdater
import com.example.module.product.domain.ProductRepository
import com.example.module.product.domain.ProductSearchService
import com.example.module.product.infrastructure.controllers.ProductGetController
import com.example.module.product.infrastructure.controllers.ProductPostController
import com.example.module.product.infrastructure.controllers.ProductPutController
import com.example.module.product.infrastructure.repositories.InMemoryProductRepository
import org.koin.dsl.module

private val application = module {
    //Product UseCases
    factory { ProductCreator(repository = get<ProductRepository>()) }
    factory { ProductSearcher(repository = get<ProductRepository>()) }
    factory { ProductCatalog(repository = get<ProductRepository>()) }
    factory { ProductUpdater(repository = get<ProductRepository>()) }

    //Notification UseCases
    factory { NotificationSender(notifier = get<Notifier>()) }
}

private val domain = module {
    factory { ProductSearchService(repository = get<ProductRepository>()) }
}

private val infrastructure = module {
    //Product
    single<ProductRepository> { InMemoryProductRepository() }
//    single<ProductRepository> { MySqlProductRepository() }
    factory {
        ProductGetController(
            productSearcher = get<ProductSearcher>(),
            productCatalog = get<ProductCatalog>()
        )
    }
    factory { ProductPostController(productCreator = get<ProductCreator>()) }
    factory { ProductPutController(productUpdater = get<ProductUpdater>()) }

    //Notification
    factory<Notifier> {
        SlackNotifier(hookUrl = "slack/notifier", setting = mapOf("token" to "slackToken"))
    }
    //factory<Notifier> { EmailNotifier(user = "user", password = "password") }
    factory { NotificationPostController(notificationSender = get<NotificationSender>()) }
    factory { HealthCheckController() }
}

val dependencies = listOf(application, domain, infrastructure)