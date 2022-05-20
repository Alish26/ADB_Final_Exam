package com.aip.alisher.covid19stats.api

import com.aip.alisher.covid19stats.common.models.CountryCovidInfo
import com.aip.alisher.covid19stats.common.models.CountryInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CoronaService {
    @GET("countries/")
    suspend fun getCountryList(): Response<List<CountryInfo>>

    @GET("country/{slug}")
    suspend fun getCountryInfo(
        @Path("slug") slug: String
    ): Response<CountryCovidInfo>
}