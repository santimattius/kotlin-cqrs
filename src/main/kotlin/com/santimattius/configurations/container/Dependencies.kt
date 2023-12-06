package com.santimattius.configurations.container

import com.santimattius.module.healthcheck.HealthCheckController
import com.santimattius.module.notification.application.NotificationSender
import com.santimattius.module.notification.domain.Notifier
import com.santimattius.module.notification.infrastructure.NotificationPostController
import com.santimattius.module.notification.infrastructure.SlackNotifier
import com.santimattius.module.product.application.ProductCatalog
import com.santimattius.module.product.application.ProductUpdater
import com.santimattius.module.product.application.create.CreateProductCommandHandler
import com.santimattius.module.product.application.create.ProductCreator
import com.santimattius.module.product.application.search.ProductSearchQueryHandler
import com.santimattius.module.product.application.search.ProductSearcher
import com.santimattius.module.product.domain.ProductRepository
import com.santimattius.module.product.domain.ProductSearchService
import com.santimattius.module.product.infrastructure.controllers.ProductGetController
import com.santimattius.module.product.infrastructure.controllers.ProductPostController
import com.santimattius.module.product.infrastructure.controllers.ProductPutController
import com.santimattius.module.product.infrastructure.repositories.InMemoryProductRepository
import com.santimattius.module.shared.domain.command.CommandBus
import com.santimattius.module.shared.domain.query.QueryBus
import com.santimattius.module.shared.infrastructure.command.CommandHandlersInformation
import com.santimattius.module.shared.infrastructure.command.InMemoryCommandBus
import com.santimattius.module.shared.infrastructure.query.InMemoryQueryBus
import com.santimattius.module.shared.infrastructure.query.QueryHandlersInformation
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
    factory { ProductSearchQueryHandler(get()) }
}

private val domain = module {
    factory { ProductSearchService(repository = get<ProductRepository>()) }
}

private val infrastructure = module {
    //Product
    single<ProductRepository> { InMemoryProductRepository() }
    factory {
        ProductGetController(
            productCatalog = get<ProductCatalog>()
        )
    }
    factory { ProductPostController() }
    factory { ProductPutController(productUpdater = get<ProductUpdater>()) }

    //Notification
    factory<Notifier> {
        SlackNotifier(hookUrl = "slack/notifier", setting = mapOf("token" to "slackToken"))
    }
    factory { NotificationPostController(notificationSender = get<NotificationSender>()) }
    factory { HealthCheckController() }
}

private val cqrs = module(createdAtStart = true) {
    single { CommandHandlersInformation() }
    single { QueryHandlersInformation() }
    single<CommandBus> { InMemoryCommandBus(get()) }
    single<QueryBus> { InMemoryQueryBus(get()) }
}

val dependencies = listOf(cqrs, application, domain, infrastructure)