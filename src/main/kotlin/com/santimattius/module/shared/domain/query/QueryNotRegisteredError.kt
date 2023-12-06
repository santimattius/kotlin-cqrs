package com.santimattius.module.shared.domain.query

class QueryNotRegisteredError(query: Class<out Query?>) :
    Exception("The query $query hasn't a query handler associated")