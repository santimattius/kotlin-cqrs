package com.santimattius.configurations.container

import com.santimattius.module.healthcheck.HealthCheckController
import com.santimattius.module.notification.application.NotificationSender
import com.santimattius.module.notification.domain.Notifier
import com.santimattius.module.notification.infrastructure.NotificationPostController
import com.santimattius.module.notification.infrastructure.SlackNotifier
import com.santimattius.module.product.application.ProductCatalog
import com.santimattius.module.product.application.ProductSearcher
import com.santimattius.module.product.application.ProductUpdater
import com.santimattius.module.product.application.create.CreateProductCommandHandler
import com.santimattius.module.product.application.create.ProductCreator
import com.santimattius.module.product.domain.ProductRepository
import com.santimattius.module.product.domain.ProductSearchService
import com.santimattius.module.product.infrastructure.controllers.ProductGetController
import com.santimattius.module.product.infrastructure.controllers.ProductPostController
import com.santimattius.module.product.infrastructure.controllers.ProductPutController
import com.santimattius.module.product.infrastructure.repositories.InMemoryProductRepository
import org.koin.dsl.module

private val application = module {
    //Product UseCases
    factory { ProductCreator(repository = get<ProductRepository>()) }
    factory { ProductSearcher(repository = get<ProductRepository>()) }
    factory { ProductCatalog(repository = get<ProductRepository>()) }
    factory { ProductUpdater(repository = get<ProductRepository>()) }

    //Notification UseCases
    factory { NotificationSender(notifier = get<Notifier>()) }
    factory { CreateProductCommandHandler(get()) }
}

private val domain = module {
    factory { ProductSearchService(repository = get<ProductRepository>()) }
}

private val infrastructure = module {
    //Product
    single<ProductRepository> { InMemoryProductRepository() }
    factory {
        ProductGetController(
            productSearcher = get<ProductSearcher>(),
            productCatalog = get<ProductCatalog>()
        )
    }
    factory { ProductPostController(get()) }
    factory { ProductPutController(productUpdater = get<ProductUpdater>()) }

    //Notification
    factory<Notifier> {
        SlackNotifier(hookUrl = "slack/notifier", setting = mapOf("token" to "slackToken"))
    }
    factory { NotificationPostController(notificationSender = get<NotificationSender>()) }
    factory { HealthCheckController() }
}

val dependencies = listOf(application, domain, infrastructure)