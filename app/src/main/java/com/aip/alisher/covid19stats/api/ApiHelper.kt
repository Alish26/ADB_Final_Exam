package com.aip.alisher.covid19stats.api

import com.aip.alisher.covid19stats.common.models.CountryCovidInfo
import com.aip.alisher.covid19stats.common.models.CountryInfo
import retrofit2.Response

/**
 * Created by halilozel1903 on 11.12.2021
 */

interface ApiHelper {
    suspend fun getCountryList(): Response<List<CountryInfo>>
    suspend fun getCountryInfo(slug: String): Response<CountryCovidInfo>
}