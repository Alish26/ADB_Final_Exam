package com.aip.alisher.covid19stats.api

import com.aip.alisher.covid19stats.common.models.CountryCovidInfo
import com.aip.alisher.covid19stats.common.models.CountryInfo
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by halilozel1903 on 11.12.2021
 */
class CoronaHelperImpl @Inject constructor(private val service: CoronaService) : ApiHelper {
    override suspend fun getCountryList(): Response<List<CountryInfo>> =
        service.getCountryList()

    override suspend fun getCountryInfo(slug: String): Response<CountryCovidInfo> =
        service.getCountryInfo(slug)
}