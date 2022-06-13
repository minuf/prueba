package com.example.testapp.core.infra.adapter.retrofit.data

data class MarvelResponse<T>(val data: Data<T>)
data class Data<T>(val results: T)