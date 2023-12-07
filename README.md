[![codecov](https://codecov.io/gh/santimattius/kotlin-hexagonal-architecture/branch/master/graph/badge.svg?token=BCIWKUK8RN)](https://codecov.io/gh/santimattius/kotlin-hexagonal-architecture) ![Quality Checks](https://github.com/santimattius/kotlin-hexagonal-architecture/actions/workflows/action.yml/badge.svg)

# kotlin-cqrs

Example project applying hexagonal architecture and CQRS in kotlin.

<p align="center">
  <img width="500" src="https://github.com/santimattius/kotlin-cqrs/blob/master/images/cqrs_diagram.png?raw=true" alt="Layers"/>
</p>

## Concepts

## CQRS Design Pattern

CQRS stands for Command Query Responsibility Segregation. It's a pattern that I first heard described by Greg Young. At
its heart is the notion that you can use a different model to update information than the model you use to read
information. For some situations, this separation can be valuable, but beware that for most systems CQRS adds risky
complexity.

### What is a Command

In CQRS, a Command represents the intention to perform an operation on our system that ends up modifying its state.

#### When it is good for us to use it

It is often said that you should not do CQRS if what you are going to set up is a blog, but like everything in life, it
depends.
If you are going to build or manage a blog with a lot of traffic, CQRS fits very well within your application, since in
terms of performance it gives us the ability to perform asynchronous tasks for free (using an asynchronous
implementation of the CommandBus).
It is also very good for us to work with this pattern when we have large teams, since by having query intentions
separated we will save ourselves a lot of merge hell's :)

## What is a Query (in CQRS)

Normally, if someone tells us Query, SQL comes to mind. In the context of CQRS this is not the case.
In CQRS, a Query represents the intention to request data from our system without altering its status.

### When it is good for us to use it

If we have a system with a high volume of traffic, we will be able to take advantage of the advantages it brings us.
Not having side-effects is very good for us to be able to search them.

## Layers in this project

<p align="center">
  <img width="500" src="https://github.com/santimattius/kotlin-cqrs/blob/master/images/arch_hexa.png?raw=true" alt="Layers"/>
</p>

**Domain**

Concepts that are in our context (User, Product, Cart, etc), and business rules that are determined exclusively by us (
domain services),

**Application**

The application layer is where the use cases of our application live (register user, publish product, add product to
cart, etc).

**Infrastructure**

Code that changes based on external decisions. In this layer will live the implementations of the interfaces that we
will define a domain level. That is, we will rely on the SOLID DIP to be able to decouple from external dependencies.

## Application

### Check

```shell
./gradlew check
```

### Run applications

```shell
./gradlew run
```

## HealthCheck

```shell
curl --location --request GET 'http://0.0.0.0:8081/healthcheck'
```

## Examples

**POST**

```shell
curl --location --request POST 'http://0.0.0.0:8081/v1/product' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "product name",
    "price": 120
}'
```

**GET**

Get all products

```shell
curl --location --request GET 'http://0.0.0.0:8081/v1/product/all'
```

Get product by id

```shell
curl --location --request GET 'http://0.0.0.0:8081/v1/product/9e6fcea9-237e-4055-ab31-95f90aac2f80'
```

Update product

```shell
curl --location --request PUT 'http://0.0.0.0:8081/v1/product' \
--header 'Content-Type: application/json' \
--data-raw '{
    "id": "9e6fcea9-237e-4055-ab31-95f90aac2f80",
    "name": "new product name",
    "price": 120
}'
```

## Frameworks

- **Ktor for server**: [Documentation](https://ktor.io/)
- **Koin for dependency injection**: [Documentation](https://insert-koin.io/)
- **Kotlin Serializations**: [Documentation](https://github.com/Kotlin/kotlinx.serialization)

## Documentation
- CQRS by Martin Fowler: [post](https://martinfowler.com/bliki/CQRS.html)
- CQRS Design Pattern in Microservices Architectures: [post](https://medium.com/design-microservices-architecture-with-patterns/cqrs-design-pattern-in-microservices-architectures-5d41e359768c)
- CQRS: Command Query Responsibility Segregation: [course](https://pro.codely.com/library/cqrs-command-query-responsibility-segregation-29074/62554/path/)
