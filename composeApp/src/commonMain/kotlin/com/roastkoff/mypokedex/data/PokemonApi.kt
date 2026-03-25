package com.roastkoff.mypokedex.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

interface PokemonApi {
    suspend fun fetchList(limit: Int, offset: Int): PokemonListResponse
}

class KtorPokemonApi(private val client: HttpClient) : PokemonApi {
    private companion object {
        const val BASE_URL = "https://pokeapi.co/api/v2/pokemon"
    }

    override suspend fun fetchList(limit: Int, offset: Int): PokemonListResponse {
        return client.get(BASE_URL) {
            url {
                parameters.append("limit", limit.toString())
                parameters.append("offset", offset.toString())
            }
        }.body()
    }
}