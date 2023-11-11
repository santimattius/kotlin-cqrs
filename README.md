[![codecov](https://codecov.io/gh/santimattius/kotlin-hexagonal-architecture/branch/master/graph/badge.svg?token=BCIWKUK8RN)](https://codecov.io/gh/santimattius/kotlin-hexagonal-architecture) ![Quality Checks](https://github.com/santimattius/kotlin-hexagonal-architecture/actions/workflows/action.yml/badge.svg)

# kotlin-hexagonal-architecture


Example project applying hexagonal architecture in kotlin.

<p align="center">
  <img width="500" src="https://github.com/santimattius/kotlin-hexagonal-architecture/blob/master/images/arch_hexa.png?raw=true" alt="Layers"/>
</p>

## Layers

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
